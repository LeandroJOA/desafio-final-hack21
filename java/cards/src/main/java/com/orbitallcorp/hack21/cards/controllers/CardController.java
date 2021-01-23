package com.orbitallcorp.hack21.cards.controllers;

import com.orbitallcorp.hack21.cards.domains.Card;
import com.orbitallcorp.hack21.cards.repositories.CardRepository;
import com.orbitallcorp.hack21.cards.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/cards")
    public ResponseEntity<Card> save(@RequestBody Card card) {

        Card savedCard = cardService.save((card));

        return new ResponseEntity(savedCard, HttpStatus.CREATED);
    }

    @GetMapping("/cards")
    public ResponseEntity<List<Card>> findAll() {

        List<Card> cards = cardService.findAll();

        return ResponseEntity.ok(cards);
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<Card> findById(@PathVariable String id) {

        Long userId = Long.parseLong(id);
        Card card = cardService.findById(userId);

        return ResponseEntity.ok(card);
    }

    @DeleteMapping("/cards/{id}")
    public void deleteById(@PathVariable String id) {

        Long userId = Long.parseLong(id);

        cardService.deleteById(userId);
    }

    @PutMapping(("/cards/{id}"))
    public ResponseEntity<Card> updateById(@PathVariable String id, @RequestBody Card newCard) {

        Long userId = Long.parseLong(id);
        Card card = cardService.findById(userId);

        card.setCardNumber(newCard.getCardNumber());
        card.setEmbossName(newCard.getEmbossName());
        card.setCustomerName(newCard.getCustomerName());
        card.setDocumentNumber(newCard.getDocumentNumber());
        card.setMotherName(newCard.getMotherName());
        card.setAddress(newCard.getAddress());
        card.setCity(newCard.getCity());

        Card savedCard = cardService.save((card));

        return new ResponseEntity(savedCard, HttpStatus.OK);
    }

    // Consumo desta API:
    // localhost:8080/cards/paginationAndSorting?pageNo=[NumeroPagina]&pageSize=[TamanhoPagina]&sortBy=[CampoOrdenação]
    @GetMapping("/cards/paginationAndSorting")
    public ResponseEntity<List<Card>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "cardNumber") String sortBy)
    {
        List<Card> list = cardService.getAllEmployees(pageNo, pageSize, sortBy);

        return new ResponseEntity<List<Card>>(list, HttpStatus.OK);
    }
}

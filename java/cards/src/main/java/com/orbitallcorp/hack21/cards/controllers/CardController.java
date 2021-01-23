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

    // Metodo para salvar novo registro
    // Rota: localhost:8080/cards
    @PostMapping("/cards")
    public ResponseEntity<Card> save(@RequestBody Card card) {

        // Chama o metodo "Save" do Service, e envia como parametro o "body" recebido da requisição,
        // armazenando o resultado final em "savedCard"
        Card savedCard = cardService.save((card));

        // Retorna como resposta um feedback do que foi salvo, junto ao status HTTP 201
        return new ResponseEntity(savedCard, HttpStatus.CREATED);
    }

    // Metodo para buscar todos os registros salvos
    // Rota: localhost:8080/cards
    @GetMapping("/cards")
    public ResponseEntity<List<Card>> findAll() {

        // Chama o metodo "FindAll" do Service, armazenando o retorno na lista "cards"
        List<Card> cards = cardService.findAll();

        // Retorna, junto ao status HTTP 200, todos os registros encontrados
        return new ResponseEntity(cards, HttpStatus.OK);
    }

    // Metodo para buscar um registro específico, através de seu ID
    // Rota: localhost:8080/cards/[idDesejado]
    @GetMapping("/cards/{id}")
    public ResponseEntity<Card> findById(@PathVariable String id) {

        // Converte o parametro enviado pela URL de String para Long
        Long userId = Long.parseLong(id);
        // Chama o metodo "FindById" do Service, armazenando o registro encontrado em "card"
        Card card = cardService.findById(userId);

        // Retorna, junto ao status HTTP 200, o registro encontrado
        return new ResponseEntity(card, HttpStatus.OK);
    }

    // Metodo para deletar um registro específico, através de seu ID
    // Rota: localhost:8080/cards/[idDesejado]
    @DeleteMapping("/cards/{id}")
    public void deleteById(@PathVariable String id) {

        // Converte o parametro enviado pela URL de String para Long
        Long userId = Long.parseLong(id);

        // Chama o metodo "deleteById" do Service, o deletando do banco de dados
        cardService.deleteById(userId);

        //Por padrão, retorna o status HTTP 204
    }

    // Metodo para atuailizar um registro específico, através de seu ID
    // Rota: localhost:8080/cards/[idDesejado]
    @PutMapping(("/cards/{id}"))
    public ResponseEntity<Card> updateById(@PathVariable String id, @RequestBody Card newCard) {

        // Converte o parametro enviado pela URL de String para Long
        Long userId = Long.parseLong(id);
        // Chama o metodo "FindById" do Service, armazenando o registro encontrado em "card"
        Card card = cardService.findById(userId);

        // Substitui os atributos do registro encontrado pelos atributos presente no "body" recebido da requisição
        card.setCardNumber(newCard.getCardNumber());
        card.setEmbossName(newCard.getEmbossName());
        card.setCustomerName(newCard.getCustomerName());
        card.setDocumentNumber(newCard.getDocumentNumber());
        card.setMotherName(newCard.getMotherName());
        card.setAddress(newCard.getAddress());
        card.setCity(newCard.getCity());

        // Chama o metodo "Save" do Service, que por padrão atualiza os dados de um registro
        // quando este possui um "id" já cadastrado
        // Por fim, armazena o resultado final em "savedCard"
        Card savedCard = cardService.save((card));

        // Retorna como resposta um feedback do que foi salvo, junto ao status HTTP 200
        return new ResponseEntity(savedCard, HttpStatus.OK);
    }

    // Metodo para buscar todos os registros de forma paginada e ordenada
    // Consumo desta API:
    // localhost:8080/cards/paginationAndSorting?pageNo=[NumeroPagina]&pageSize=[TamanhoPagina]&sortBy=[CampoOrdenação]
    // Caso não informado, pageNo por padrão possuirá o valor "0", pageSize o valor "10" e sortBy "customerName"
    @GetMapping("/cards/paginationAndSorting")
    public ResponseEntity<List<Card>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "customerName") String sortBy)
    {
        // Chama o metodo "findAllSorted" do Service, buscando todos os registros,
        // passando como parametro a pagina requisitada, quantos registros irão existir por pagina e
        // por qual campo será feito a ordenação
        List<Card> cards = cardService.findAllSorted(pageNo, pageSize, sortBy);

        // Retorna todos os registros já ordenados e paginados, junto ao status HTTP 200
        return new ResponseEntity<List<Card>>(cards, HttpStatus.OK);
    }
}

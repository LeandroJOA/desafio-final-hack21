package com.orbitallcorp.hack21.cards.services;

import com.orbitallcorp.hack21.cards.domains.Card;
import com.orbitallcorp.hack21.cards.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card save(Card card) {
        return cardRepository.save((card));
    }

    public List<Card> findAll() {
        List<Card> cards = new ArrayList<>();

        cardRepository.findAll().forEach(cards :: add);

        return cards;
    }

    public Card findById(Long userId) {

        return cardRepository.findById(userId).get();
    }

    public void deleteById(Long userId) {

        cardRepository.deleteById(userId);
    }

    public List<Card> getAllEmployees(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Card> pagedResult = cardRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Card>();
        }
    }

}

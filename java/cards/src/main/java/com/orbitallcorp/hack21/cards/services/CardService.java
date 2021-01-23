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
import java.util.NoSuchElementException;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    // Metodo para salvar um novo registro
    public Card save(Card card) {
        // Salva o registro recebido e o retorna
        return cardRepository.save((card));
    }

    // Metodo para buscar todos os registros
    public List<Card> findAll() {
        List<Card> cards = new ArrayList<>();

        // Busca todos os registros, onde cada registro encontrado é adicionado à lista "cards"
        cardRepository.findAll().forEach(cards :: add);

        // Retorna a lista "cards" com os registros encontrados
        return cards;
    }

    // Metodo para buscar um registro específico
    public Card findById(Long userId) {

        try {
            // Busca o registro utilizando seu "id" e retorna o elemento encontrado
            return cardRepository.findById(userId).get();
        }catch (NoSuchElementException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    // Metodo para deletar um registro específico
    public void deleteById(Long userId) {

        // Deleta o registro utilizando seu "id"
        cardRepository.deleteById(userId);
    }

    // Metodo para buscar todos os registro de forma ordenada e paginada
    public List<Card> findAllSorted(Integer pageNo, Integer pageSize, String sortBy)
    {
        // Realiza uma requisição de paginação, indicando a pagina requisitada,
        // quantos elementos poderão existir por pagina e por qual campo ocorrerá a ordenação
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        // Busca todos os elementos, de forma paginada e ordenada, e os armazena em "pagedResult"
        Page<Card> pagedResult = cardRepository.findAll(paging);

        // Caso tenha sido encontrado algum conteudo, requisita este conteudo e o retorna
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        // Caso contrario retorna uma lista vazia
        } else {
            return new ArrayList<Card>();
        }
    }

}

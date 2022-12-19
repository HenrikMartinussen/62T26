package info.martinussen.dit.developmentprocesses.solitaire.domain

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard

import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank.ACE
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank.KING
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit.CLUBS
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit.SPADES

class DeckOfCards {
    List cards

    DeckOfCards(){
        cards = [] // shorthand for new ArrayList()
        (SPADES..CLUBS).each{ suit ->
            (ACE..KING).each{ rank ->
                cards << new PlayingCard(rank, suit)
            }
        }
    }

    DeckOfCards shuffle(){
        Collections.shuffle(cards)
        return this
    }

    PlayingCard draw(){
        if (cards.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException('cannot draw a card from an empty deck...')
        }
        PlayingCard card = cards.head() as PlayingCard // head() returns first element of list
        cards = cards.tail()                           // tail() returns a copy of the list except for the first element
        return card
    }

    List getRemainingCards() {
        List returnValue = cards.clone()
        cards = []
        return returnValue
    }

    Boolean isEmpty(){
        return cards.isEmpty()
    }

    Boolean hasMoreCards() {
        return !cards.isEmpty()
    }

}

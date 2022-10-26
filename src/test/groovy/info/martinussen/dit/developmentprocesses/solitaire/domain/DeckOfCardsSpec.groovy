package info.martinussen.dit.developmentprocesses.solitaire.domain

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import spock.lang.Specification

class DeckOfCardsSpec extends Specification{

    def 'new deck of cards should hold 52 cards, and when the last card has been drawn, the deck should be empty'(){
        given:
        def deck = new DeckOfCards()

        when:
        52.times{
            assert deck.hasMoreCards()
            deck.draw()
        }

        then:
        deck.isEmpty()
    }

    def 'It should be possible to draw 52 different cards, from a deck of cards'() {
        given:
        def deck = new DeckOfCards()
        Set drawnCards = new TreeSet() // throws away duplicates

        when:
        while (deck.hasMoreCards()){
            drawnCards << deck.draw()
        }

        then:
        drawnCards.size() == 52
        drawnCards.suit.unique().sort()                             == [Suit.DIAMONDS, Suit.CLUBS, Suit.HEARTS, Suit.SPADES]
        drawnCards.suit.unique().collect{it.color}.unique().sort()  == [Color.RED, Color.BLACK]
        drawnCards.rank.unique().collect{it.symbol}.unique().sort() == ['10', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'J', 'K', 'Q']
    }

    def 'when 30 cards have been drawn from the deck, when you get remaining cards, there should be 22 left'(){
        given:
        def deck = new DeckOfCards()
        30.times{
            deck.draw()
        }
        assert !deck.isEmpty()
        assert deck.hasMoreCards()

        when:
        def remainingCards = deck.getRemainingCards()

        then:
        remainingCards.size() == 22
    }

    def 'when it is attempted to draw a card from an empty deck an IOOBE is expected to be thrown'(){ // IOOBE: IndexOutOfBoundsException
        given: 'a new full deck of cards'
        def deck = new DeckOfCards()

        and: 'empty the deck'
        52.times{
            deck.draw()
        }

        when: 'it is attempted to draw from an empty deck'
        deck.draw()

        then: 'expect Index out of bounds exception'
        thrown IndexOutOfBoundsException

    }

}

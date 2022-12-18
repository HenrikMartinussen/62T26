package info.martinussen.dit.developmentprocesses.solitaire.domain

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import spock.lang.Specification

class DeckOfCardsSpec extends Specification {

    def 'new deck of cards should hold 52 cards, and when the last card has been drawn, the deck should be empty'(){
        given: 'a new (not shuffled) Deck of Cards'
        def deck = new DeckOfCards()

        when: '52 cards are drawn from the Deck'
        52.times{
            assert deck.hasMoreCards()
            deck.draw()
        }

        then: 'deck is empty'
        deck.isEmpty()
    }

    def 'It should be possible to draw 52 different cards, from a deck of cards'() {
        given: 'a new (not shuffled) Deck of Cards'
        def deck = new DeckOfCards()

        and: 'a set (no duplicates collection) to draw cards into'
        Set drawnCards = new TreeSet() // throws away duplicates

        when: 'all cards are drawn'
        while (deck.hasMoreCards()){
            drawnCards << deck.draw()
        }

        then: 'set has 52 cards'
        drawnCards.size() == 52
        and: 'all suits are represented'
        drawnCards.suit.unique().sort()                             == [Suit.SPADES, Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS]
        and: 'both colors are represented'
        drawnCards.suit.unique().collect{it.color}.unique().sort()  == [Color.RED, Color.BLACK]
        and: 'all ranks are represented'
        drawnCards.rank.unique().collect{it.symbol}.unique().sort() == ['10', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'J', 'K', 'Q']
    }

    def 'given 28 cards have been drawn from the deck, when you get remaining cards, there should be 24 and the Deck should be empty'(){
        given: 'a (not shuffled) Deck of Cards'
        def deck = new DeckOfCards()
        and: '28 cards have been drawn'
        28.times{
            deck.draw()
        }
        assert !deck.isEmpty()
        assert deck.hasMoreCards()

        when: 'remaining cards are fetched'
        def remainingCards = deck.getRemainingCards()

        then: '28 cards should have been returned'
        remainingCards.size() == 24
        and: 'Deck should be empty'
        deck.isEmpty()
        !deck.hasMoreCards()
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

    def 'The sequence of the Cards in a shuffled Deck of Cards is different from the sequence of an un-shuffled Deck'() {
        given: 'a shuffled and an un-shuffled Deck of Cards'
        DeckOfCards shuffledDeck   = new DeckOfCards().shuffle()
        DeckOfCards unShuffledDeck = new DeckOfCards()
        def compareResults = []
        def acceptableNumberOfCoincidentalEquality = 9

        when: 'we browse through the two Decks and compare the Cards'
        52.times{
            compareResults << (unShuffledDeck.draw() == shuffledDeck.draw())
        }

        then: 'expect only a few identical Cards'
        compareResults.count(true) < acceptableNumberOfCoincidentalEquality
    }
}

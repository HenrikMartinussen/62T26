package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.DeckOfCards
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import org.codehaus.groovy.runtime.powerassert.PowerAssertionError
import spock.lang.Specification

class StockPileSpec extends Specification {

    def 'A newly created stock pile should report that it is empty'(){
        given:
        def stockPile = new StockPile()

        expect:
        stockPile.isEmpty()
        !stockPile.hasMoreCards()
    }

    def 'A stock pile with cards in it should report so when asked, and also should report that it is not empty, and it should be possible to peek the top card even though cards in the stock pile are face down'(){
        given:
        def stockPile = new StockPile()
        assert stockPile.isEmpty()
        assert !stockPile.hasMoreCards()

        def deck = new DeckOfCards()
        39.times{// throw away 39 cards
            deck.draw()
        }

        when:
        stockPile.putCards(deck.getRemainingCards().collect {it.faceDown()})

        then:
        stockPile.hasMoreCards()
        !stockPile.isEmpty()

        and: 'it should be possible to peek the topmost though cards are face down'
        def cardInformation = stockPile.peek()
        cardInformation.rank == Rank.KING
        cardInformation.suit == Suit.CLUBS

        and: 'when you take the topmost card it is turned face up'
        def card = stockPile.takeCard()
        card.faceUp

    }

    def 'An empty stock pile should accept a list of face down cards'() {
        given: 'a new empty stock pile'
        def stockPile = new StockPile()
        assert stockPile.isEmpty()
        assert !stockPile.hasMoreCards()

        and: 'a new deck of cards'
        def deck = new DeckOfCards()

        when:
        stockPile.putCards deck.remainingCards.collect{it.faceDown()}

        then:
        !stockPile.isEmpty()
        stockPile.hasMoreCards()
    }

    def 'A stock pile with cards in it should not accept to be topped up with additional cards'(){
        given: 'a new empty stock pile'
        def stockPile = new StockPile()
        assert stockPile.isEmpty()
        assert !stockPile.hasMoreCards()

        and: 'a new deck of cards'
        def deck = new DeckOfCards()

        and: 'prepare to cheat by putting some cards up the sleeve'
        def sleeve = []
        3.times{
            sleeve << deck.draw()
        }

        and: 'put cards remaining in the deck face down into the stock pile'
        stockPile.putCards(deck.getRemainingCards().collect {it.faceDown()})

        when: 'you ask if it is acceptable to salt the stock pile with cards from up your sleeve'
        def response = stockPile.isAcceptable(sleeve.collect{it.attributes})

        then: 'you expect to get a NO'
        response == false
    }

    def 'A stock pile with cards in it should throw an error when it is attempted to top it up with additional cards'(){
        given: 'a new empty stock pile'
        def stockPile = new StockPile()
        assert stockPile.isEmpty()
        assert !stockPile.hasMoreCards()

        and: 'a new deck of cards'
        def deck = new DeckOfCards()

        and: 'prepare to cheat by putting some cards up the sleeve'
        def sleeve = []
        3.times{
            sleeve << deck.draw().faceDown()
        }

        and: 'put cards remaining in the deck face down into the stock pile ()'
        stockPile.putCards(deck.getRemainingCards().collect {it.faceDown()})

        when: 'you attempt to salt the stock pile with cards from up your sleeve'
        stockPile.putCards(sleeve)

        then: 'you get busted'
        thrown PowerAssertionError
    }


}

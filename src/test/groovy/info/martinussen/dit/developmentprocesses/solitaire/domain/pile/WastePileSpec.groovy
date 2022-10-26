package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.DeckOfCards
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import org.codehaus.groovy.runtime.powerassert.PowerAssertionError
import spock.lang.Specification

class WastePileSpec extends Specification {

    def 'A newly created waste pile should report that it is empty and hence does not have more cards'(){
        given:
        def wastePile = new WastePile()

        expect:
        wastePile.isEmpty()
        !wastePile.hasMoreCards()
    }

    def 'An empty waste pile should return null when you attempt to peek the topmost card'(){
        given:
        def wastePile = new WastePile()
        assert wastePile.isEmpty()
        assert !wastePile.hasMoreCards()

        PlayingCardAttributes topMostCardInfo

        when:
        topMostCardInfo = wastePile.peek()

        then:
        topMostCardInfo == null
    }

    def 'A waste pile should accept cards with their face up'() {
        given:
        def wastePile = new WastePile()
        assert wastePile.isEmpty()
        assert !wastePile.hasMoreCards()

        def deck = new DeckOfCards() // not shuffled
        def faceUpCard

        when:
        while(deck.hasMoreCards()) {
            faceUpCard = deck.draw()
            assert faceUpCard.isFaceUp()
            wastePile.addCard(faceUpCard)
            assert wastePile.hasMoreCards()
            assert !wastePile.isEmpty()
        }

        then:
        deck.isEmpty()
        !deck.hasMoreCards()
        wastePile.hasMoreCards()
        !wastePile.isEmpty()

        and:
        def topMostCard = wastePile.takeCard()
        assert topMostCard.suit == Suit.SPADES
        assert topMostCard.rank == Rank.KING
    }

    def 'An empty waste pile should not accept cards with their face down'() {
        given:
        def wastePile = new WastePile()
        assert wastePile.isEmpty()
        assert !wastePile.hasMoreCards()

        when:
        wastePile.addCard(faceDownCard)

        then:
        faceDownCard.isFaceDown()
        thrown PowerAssertionError

        where:
        faceDownCard << new DeckOfCards().getRemainingCards().collect {card -> card.faceDown()}
    }
}

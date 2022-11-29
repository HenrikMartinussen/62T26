package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.DeckOfCards
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import spock.lang.Specification

class WastePileAndFoundationPileIntegrationSpec extends Specification {

    def 'It should be possible to move an Ace from the WastePile to a Foundation Pile'() {
        given: 'an empty waste pile'
        def wastePile = new WastePile()
        assert wastePile.isEmpty()
        and: 'a new deck of cards, shuffled'
        def deck = new DeckOfCards().shuffle()
        and: 'a stock pile gets all of the cards (face down)'
        def stockPile = new StockPile()
        stockPile.putCards deck.getRemainingCards().collect {it.faceDown()}
        assert stockPile.hasMoreCards()
        and: 'we move cards from the stockPile to the wastePile one at the time, until we get an Ace'
        def move = stockPile.createMoveObject()
        move.setTarget(wastePile)
        move.execute()
        assert move.isSuccess()
        PlayingCardAttributes attributes = wastePile.peek()

        while (attributes.rank != Rank.ACE) {
            move = stockPile.createMoveObject()
            move.setTarget(wastePile)
            move.execute()
            assert move.isSuccess()
            attributes = wastePile.peek()
        }
        assert attributes.rank == Rank.ACE
        def foundationPile = new FoundationPile(attributes.suit)
        assert foundationPile.isEmpty()

        when:
        def foundationPileMove = wastePile.createMoveObject()
        foundationPileMove.setTarget(foundationPile)
        foundationPileMove.execute()

        then:
        foundationPileMove.isSuccess()
        foundationPile.hasMoreCards()
        foundationPile.peek().rank == Rank.ACE

    }

}

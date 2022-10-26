package info.martinussen.dit.developmentprocesses.solitaire.domain

import info.martinussen.dit.developmentprocesses.solitaire.domain.pile.StockPile
import info.martinussen.dit.developmentprocesses.solitaire.domain.pile.WastePile
import spock.lang.Specification

class StockPileAndWastePileIntegrationSpec extends Specification {

    def 'It should be possible to move a card from the stock pile to the waste pile'(){
        given: 'an empty waste pile'
        def wastePile = new WastePile()
        assert wastePile.isEmpty()
        and: 'a deck of cards with the first 30 cards discarded'
        def deck = new DeckOfCards()
        30.times{
            deck.draw()
        }
        and: 'a stock pile gets the remaining cards (face down)'
        def stockPile = new StockPile()
        stockPile.putCards deck.getRemainingCards().collect {it.faceDown()}
        assert stockPile.hasMoreCards()

        when:
        def move = stockPile.createMoveObject()
        move.setTarget(wastePile)
        and:
        move.execute()

        then:
        move.isSuccess()
        wastePile.hasMoreCards()

        and:
        wastePile.takeCard()
        wastePile.isEmpty()

    }
}

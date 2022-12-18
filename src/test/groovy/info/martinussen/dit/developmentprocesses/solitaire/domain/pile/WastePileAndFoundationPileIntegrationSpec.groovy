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

        and: 'we create a foundation pile of the suit of the Ace we found'
        def foundationPile = new FoundationPile(attributes.suit)
        assert foundationPile.isEmpty()

        when: 'we move the Ace we found to the foundation pile we just created'
        def wastePileTofoundationPileMove = wastePile.createMoveObject()
        wastePileTofoundationPileMove.setTarget(foundationPile)
        wastePileTofoundationPileMove.execute()

        then: 'Ace is not on top of waste pile anymore, the move was a success, the foundation pile is not empty anymore and an Ace is on top of the foundation pile'
        wastePile.peek()?.rank != Rank.ACE               // wastePile will be empty if the Ace was the first drawn card from the stockpile. As a consequence we must use null safe dereference
        wastePileTofoundationPileMove.isSuccess()
        !foundationPile.isEmpty()
        foundationPile.hasMoreCards()
        foundationPile.peek().rank == Rank.ACE
    }

}

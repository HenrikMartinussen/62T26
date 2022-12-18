package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.BlockingRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.ColorRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.FaceRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.NoOpRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.RankRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.Rule

class GameStartedTableauPileState implements TableauPileState {

    TableauPile receivingObject

    Rule colorRule
    Rule faceUpRule
    Rule rankRule

    GameStartedTableauPileState(TableauPile receivingObject) {
        this.receivingObject = receivingObject

        colorRule  = new ColorRule(receivingObject.peek().suit.color).next() // next card should have opposite color than the topmost card on this pile
        rankRule   = new RankRule(receivingObject.peek().rank).previous()    // next card should have the next lower value than the topmost card on this pile
        faceUpRule = new FaceRule(true)                                      // while game is started, pile accepts only face up cards
    }

    @Override
    Boolean isAcceptable(PlayingCardAttributes attributes) {
        Boolean returnValue = false
        if (faceUpRule.isValid(attributes)) {
            if (colorRule.isValid(attributes)) {
                if (rankRule.isValid(attributes)) {
                    returnValue = true
                }
            }
        }
        return returnValue
    }

    @Override
    Boolean isGameStarted() {
        return true
    }

    @Override
    void addCard(PlayingCard card) {
        assert card

        if (receivingObject.cards.isEmpty()) {
            colorRule = new ColorRule(card.suit?.color)
        }

        assert isAcceptable(card.attributes)

        receivingObject.cards.push(card)

        if (receivingObject.peek().rank == Rank.ACE) {
            rankRule = new BlockingRule()
        }

        colorRule.next()
        rankRule.previous()

    }

    @Override
    PlayingCard takeCard() {
        def card = receivingObject.cards.pop()

        if (receivingObject.cards.isEmpty()) { // we emptied the pile
            colorRule = new NoOpRule()
            rankRule  = new RankRule(Rank.KING)
        } else { // still cards in the pile
            if (receivingObject.peek().faceDown) {
                receivingObject.cards.last().faceUp()
            }
            if (card.rank == Rank.ACE) { // we took an ace...
                rankRule = new RankRule(receivingObject.peek().rank.previous())
            }

        }
        colorRule.previous()
        return card
    }
}

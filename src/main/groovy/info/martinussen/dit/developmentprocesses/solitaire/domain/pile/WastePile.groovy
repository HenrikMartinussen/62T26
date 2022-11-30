package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.SingleCardMove
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.FaceUpRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.Rule

class WastePile extends Pile {

    Rule faceUpRule

    WastePile() {
        this.cards = []
        faceUpRule = new FaceUpRule(true) // the waste pile accepts only face up cards
    }

    void addCard(PlayingCard card) {
        assert card
        assert card.isFaceUp()
        cards.push(card)
    }

    @Override
    Boolean isAcceptable(PlayingCardAttributes cardAttributes) {
        def returnValue = false
        assert cardAttributes
        if (faceUpRule.isValid(cardAttributes)) {
            returnValue = true
        }
        return returnValue
    }

    SingleCardMove createMoveObject() {
        def moveObject = new SingleCardMove(this)
        return moveObject
    }
}

package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.FaceUpRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.Rule

class LayoutPhaseTableauPileState implements TableauPileState {

    TableauPile receivingObject
    Rule faceUpRule

    LayoutPhaseTableauPileState(TableauPile receivingObject) {
        this.receivingObject = receivingObject

        if (receivingObject.initialSize == 1) {
            faceUpRule = new FaceUpRule(true)
        } else {
            faceUpRule = new FaceUpRule(false)
        }

    }

    @Override
    Boolean isAcceptable(PlayingCardAttributes attributes) {
        Boolean returnValue = false
        if (faceUpRule.isValid(attributes)) {
            returnValue = true
        }
        return returnValue
    }

    @Override
    Boolean isGameStarted() {
        return false
    }

    @Override
    void addCard(PlayingCard card) {
        assert card

        assert !receivingObject.layoutComplete // No one comes late to MY party!
        assert isAcceptable(card.attributes)

        receivingObject.cards.push(card)

        if (receivingObject.cards.size() == receivingObject.initialSize - 1 && !receivingObject.layoutComplete) {
            faceUpRule = new FaceUpRule(true)
        }

        if (receivingObject.cards.size() == receivingObject.initialSize && !receivingObject.layoutComplete) {
            receivingObject.layoutComplete = true
        }
    }

    @Override
    PlayingCard takeCard() {
        throw new IllegalStateException('You cannot take cards from a tableau pile while in the layout phase')
    }
}

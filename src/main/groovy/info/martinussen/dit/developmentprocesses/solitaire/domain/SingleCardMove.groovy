package info.martinussen.dit.developmentprocesses.solitaire.domain

import info.martinussen.dit.developmentprocesses.solitaire.domain.pile.Pile
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes

class SingleCardMove implements Move {

    Pile source
    Pile target
    Boolean success

    SingleCardMove(Pile source) {
        this.source = source
    }

    @Override
    void setTarget(Pile pile) {
        this.target = pile
    }

    void execute() {
        assert source.hasMoreCards()
        assert success == null
        success = false
        def cardAttributes = new PlayingCardAttributes(source.peek().rank, source.peek().suit, false)

        if (target.isAcceptable(cardAttributes)) {
            def card = source.takeCard().faceUp()
            target.putCard(card)
            success = true
        }
    }

    Boolean isSuccess() {
        if (success == null) { throw new IllegalStateException()}
        return success
    }
}

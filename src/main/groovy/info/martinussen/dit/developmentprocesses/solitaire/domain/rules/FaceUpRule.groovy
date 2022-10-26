package info.martinussen.dit.developmentprocesses.solitaire.domain.rules

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes

class FaceUpRule implements Rule {

    Boolean faceUp

    FaceUpRule(Boolean faceUp) {
        this.faceUp = faceUp
    }

    @Override
    Boolean isValid(PlayingCardAttributes attributes) {
        return attributes?.isFaceUp() == faceUp
    }

    @Override
    Rule next() {
        faceUp = !faceUp
        return this
    }

    @Override
    Rule previous() {
        faceUp = !faceUp
        return this
    }
}

package info.martinussen.dit.developmentprocesses.solitaire.domain.rules

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes

class NoOpRule implements Rule {

    @Override
    Boolean isValid(PlayingCardAttributes attributes) {
        return true
    }

    @Override
    Rule next() {
        return this
    }

    @Override
    Rule previous() {
        return this
    }
}

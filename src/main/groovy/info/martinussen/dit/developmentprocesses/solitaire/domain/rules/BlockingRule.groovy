package info.martinussen.dit.developmentprocesses.solitaire.domain.rules

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes

class BlockingRule implements Rule {

    @Override
    Boolean isValid(PlayingCardAttributes attributes) {
        return false
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

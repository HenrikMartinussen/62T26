package info.martinussen.dit.developmentprocesses.solitaire.domain.rules

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit

class SuitRule implements Rule {

    Suit suit

    SuitRule(Suit suit) {
        this.suit = suit
    }

    @Override
    Boolean isValid(PlayingCardAttributes attributes) {
        return attributes?.suit == suit
    }

    @Override
    Rule next() {
        suit++
        return this
    }

    @Override
    Rule previous() {
        suit++
        return this
    }
}

package info.martinussen.dit.developmentprocesses.solitaire.domain.rules

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes

class ColorRule implements Rule {

    Color color

    ColorRule(Color color) {
        this.color = color
    }

    @Override
    Boolean isValid(PlayingCardAttributes attributes) {
        attributes?.suit?.color == color
    }

    @Override
    ColorRule next() {
        color++
        return this
    }

    @Override
    ColorRule previous() {
        color--
        return this
    }
}

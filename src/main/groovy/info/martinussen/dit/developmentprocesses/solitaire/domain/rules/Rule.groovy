package info.martinussen.dit.developmentprocesses.solitaire.domain.rules

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes

interface Rule {

    Boolean isValid(PlayingCardAttributes attributes)
    Rule next()
    Rule previous()

}
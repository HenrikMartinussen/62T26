package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes

interface TableauPileState {

    Boolean isAcceptable(PlayingCardAttributes attributes)
    Boolean isGameStarted()
    void addCard(PlayingCard card)
    PlayingCard takeCard()

}
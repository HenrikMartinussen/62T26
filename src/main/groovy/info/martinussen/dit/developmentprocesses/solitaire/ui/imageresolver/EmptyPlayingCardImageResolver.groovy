package info.martinussen.dit.developmentprocesses.solitaire.ui.imageresolver

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard

class EmptyPlayingCardImageResolver extends PlayingCardImageResolver {

    @Override
    Integer calculateXIndex(PlayingCard playingCard) { // playingCard attributes is not used to find the empty image
        return 0
    }

    @Override
    Integer calculateYIndex(PlayingCard playingCard) { // playingCard attributes is not used to find the empty image
        return 4
    }
}

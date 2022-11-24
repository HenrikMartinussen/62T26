package info.martinussen.dit.developmentprocesses.solitaire.ui.imageresolver

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard

class FaceDownPlayingCardImageResolver extends PlayingCardImageResolver {

    @Override
    Integer calculateXIndex(PlayingCard playingCard) { // playingCard  attributes is not used to find the face down image
        return 4
    }

    @Override
    Integer calculateYIndex(PlayingCard playingCard) { // playingCard attributes is not used to find the face down image
        return 4
    }
}

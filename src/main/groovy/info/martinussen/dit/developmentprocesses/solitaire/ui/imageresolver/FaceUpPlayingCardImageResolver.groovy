package info.martinussen.dit.developmentprocesses.solitaire.ui.imageresolver

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard

class FaceUpPlayingCardImageResolver extends PlayingCardImageResolver {

    @Override
    Integer calculateXIndex(PlayingCard playingCard) {
        playingCard.getRank().ordinal()
    }

    @Override
    Integer calculateYIndex(PlayingCard playingCard) {
        playingCard.getSuit().ordinal()
    }
}

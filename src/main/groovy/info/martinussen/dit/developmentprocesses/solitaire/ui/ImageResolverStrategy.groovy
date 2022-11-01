package info.martinussen.dit.developmentprocesses.solitaire.ui

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard

import java.awt.image.BufferedImage

interface ImageResolverStrategy {

    BufferedImage getImage(PlayingCard playingCard)

}
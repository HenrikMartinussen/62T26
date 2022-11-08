package info.martinussen.dit.developmentprocesses.solitaire.ui

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard

import java.awt.*
import java.awt.image.BufferedImage

class FaceDownImageResolverStrategy implements ImageResolverStrategy {

    PlayingCardImageResolver mummy

    FaceDownImageResolverStrategy(PlayingCardImageResolver playingCardImageResolver) {
        mummy = playingCardImageResolver
    }

    @Override
    BufferedImage getImage(PlayingCard playingCard) {
        BufferedImage returnValue = null

        def sourceX = mummy.OFFSET + (4 * (mummy.SOURCE_WIDTH + mummy.MARGIN))
        def sourceY = mummy.OFFSET + (4 * (mummy.SOURCE_HEIGHT + mummy.MARGIN))
        returnValue = new BufferedImage(mummy.DESTINATION_WIDTH, mummy.DESTINATION_HEIGHT, BufferedImage.TYPE_INT_RGB)
        Graphics g = returnValue.createGraphics()
        g.drawImage(mummy.deckImage, 0, 0, mummy.DESTINATION_WIDTH, mummy.DESTINATION_HEIGHT, sourceX, sourceY, sourceX + mummy.SOURCE_WIDTH, sourceY + mummy.SOURCE_HEIGHT, null)

        return returnValue
    }
}
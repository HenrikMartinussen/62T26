package info.martinussen.dit.developmentprocesses.solitaire.ui.imageresolver

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard

import javax.imageio.ImageIO
import java.awt.Graphics
import java.awt.image.BufferedImage

abstract class PlayingCardImageResolver {

    static final String  LOCATION           = "/5109px-English_pattern_playing_cards_deck_PLUS.svg.png"
    static final Integer SOURCE_WIDTH       = 360;
    static final Integer SOURCE_HEIGHT      = 540;
    static final Integer DESTINATION_WIDTH  = 100;
    static final Integer DESTINATION_HEIGHT = 147;
    static final Integer X_OFFSET           = 38;
    static final Integer Y_OFFSET           = 30;
    static final Integer MARGIN             = 30;

    static PlayingCardImageResolver emptyPlayingCardImageResolver    = new EmptyPlayingCardImageResolver()
    static PlayingCardImageResolver faceDownPlayingCardImageResolver = new FaceDownPlayingCardImageResolver()
    static PlayingCardImageResolver faceUpPlayingCardImageResolver   = new FaceUpPlayingCardImageResolver()

    static def deckImage

    protected PlayingCardImageResolver() {
        if (deckImage == null) {
            def stream = getClass().getResourceAsStream(LOCATION)
            deckImage = ImageIO.read(stream)
        }
    }

    static PlayingCardImageResolver getInstance(PlayingCard playingCard) {
        def returnValue
        switch (playingCard) {
            case {playingCard == null}:
                returnValue = emptyPlayingCardImageResolver
                break
            case {playingCard.isFaceDown()}:
                returnValue = faceDownPlayingCardImageResolver
                break
            case {playingCard.isFaceUp()}:
                returnValue = faceUpPlayingCardImageResolver
                break
            default:
                returnValue = emptyPlayingCardImageResolver
                break
        }
        return returnValue

    }

    BufferedImage resolveImage(PlayingCard playingCard) {
        BufferedImage image = null

        def sourceX = calculateXValue(playingCard)
        def sourceY = calculateYValue(playingCard)
        image = new BufferedImage(DESTINATION_WIDTH, DESTINATION_HEIGHT, BufferedImage.TYPE_INT_RGB)
        Graphics g = image.createGraphics()
        g.drawImage(deckImage, 0, 0, DESTINATION_WIDTH, DESTINATION_HEIGHT, sourceX, sourceY, sourceX + SOURCE_WIDTH, sourceY + SOURCE_HEIGHT, null)

        return image
    }

    Integer calculateXValue (PlayingCard playingCard) {
        X_OFFSET + (calculateXIndex(playingCard) * (SOURCE_WIDTH + MARGIN))
    }

    Integer calculateYValue (PlayingCard playingCard) {
        Y_OFFSET + (calculateYIndex(playingCard) * (SOURCE_HEIGHT + MARGIN))
    }

    abstract Integer calculateXIndex(PlayingCard playingCard)

    abstract Integer calculateYIndex(PlayingCard playingCard)

}
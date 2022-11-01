package info.martinussen.dit.developmentprocesses.solitaire.ui

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class PlayingCardImageResolver {

    static final String LOCATION = "/5109px-English_pattern_playing_cards_deck_PLUS.svg.png"
    static final Integer SOURCE_WIDTH = 360;
    static final Integer SOURCE_HEIGHT = 540;
    static final Integer DESTINATION_WIDTH = 100;
    static final Integer DESTINATION_HEIGHT = 147;
    static final Integer OFFSET = 30;
    static final Integer MARGIN = 30;

    def deckImage
    private currentImageResolverStrategy
    private emptyImageResolverStrategy
    private faceDownImageResolverStrategy
    private faceUpImageResolverStrategy

    PlayingCardImageResolver() {
        def stream = getClass().getResourceAsStream(LOCATION)
        deckImage = ImageIO.read(stream)

        emptyImageResolverStrategy    = new EmptyImageResolverStrategy(this)
        faceDownImageResolverStrategy = new FaceDownImageResolverStrategy(this)
        faceUpImageResolverStrategy   = new FaceUpImageResolverStrategy(this)

        currentImageResolverStrategy = emptyImageResolverStrategy
    }

    BufferedImage resolveImage(PlayingCard playingCard) {
        currentImageResolverStrategy = selectImageResolverStrategy(playingCard)
        return currentImageResolverStrategy.getImage(playingCard)
    }

    private ImageResolverStrategy selectImageResolverStrategy(PlayingCard playingCard) {
        def returnValue = emptyImageResolverStrategy
        switch (playingCard) {
            case {playingCard == null}:
                returnValue = emptyImageResolverStrategy
                break
            case {playingCard.isFaceDown()}:
                returnValue = faceDownImageResolverStrategy
                break
            case {playingCard.isFaceUp()}:
                returnValue = faceUpImageResolverStrategy
                break
            default:
                returnValue = emptyImageResolverStrategy
                break
        }
        return returnValue
    }

}

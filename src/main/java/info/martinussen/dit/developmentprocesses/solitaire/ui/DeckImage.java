package info.martinussen.dit.developmentprocesses.solitaire.ui;

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class DeckImage {


    private final int SOURCE_WIDTH = 360;
    private final int SOURCE_HEIGHT = 540;
    private final int DESTINATION_WIDTH = 100;
    private final int DESTINATION_HEIGHT = 147;
    private final int offset = 30;
    private final int margin = 30;
    private static final String LOCATION = "/cards.png";
    private BufferedImage deckImage;

    DeckImage() {
        InputStream stream = getClass().getResourceAsStream(LOCATION);
        try {
            deckImage = ImageIO.read(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    BufferedImage getImage(PlayingCard playingCard) {
        int sourceX = offset + (playingCard.getRank().ordinal() * (SOURCE_WIDTH + margin));
        int sourceY = offset + (playingCard.getSuit().ordinal() * (SOURCE_HEIGHT + margin));
        BufferedImage image = new BufferedImage(DESTINATION_WIDTH, DESTINATION_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.drawImage(deckImage, 0, 0, DESTINATION_WIDTH, DESTINATION_HEIGHT, sourceX, sourceY, sourceX + SOURCE_WIDTH, sourceY + SOURCE_HEIGHT, null);
        return image;
    }
}

// https://stackoverflow.com/questions/14699441/getclass-getresourceasstream-in-maven-project
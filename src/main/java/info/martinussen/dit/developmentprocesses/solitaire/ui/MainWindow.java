package info.martinussen.dit.developmentprocesses.solitaire.ui;

import info.martinussen.dit.developmentprocesses.solitaire.domain.DeckOfCards;
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MainWindow {

    DeckOfCards deckOfCards;

    private JFrame frame;

    /**
     * Launch the application
     * @param args
     */
    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainWindow window = new MainWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application
     */
    public MainWindow() {
        deckOfCards = new DeckOfCards().shuffle();
        initialize();
    }

    /**
     * Initialize the contents of the frame
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(new Rectangle(100, 100, 450, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null); // allows for absolute placement of content in the pane

        JLabel cardLabel1 = new JLabel("Image goes here");
        cardLabel1.setBounds(10, 11, 100, 147);
        frame.getContentPane().add(cardLabel1);


        JLabel cardLabel2 = new JLabel("Image goes here");
        cardLabel2.setBounds(120, 11, 100, 147);
        frame.getContentPane().add(cardLabel2);

        JButton btnNewButton = new JButton("Deal");
        btnNewButton.setBounds(new Rectangle(10,169,210, 34));
        frame.getContentPane().add(btnNewButton);

        DeckImage deckImage = new DeckImage();

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayingCard playingCard1 = deckOfCards.draw();
                BufferedImage cardImage1 = deckImage.getImage(playingCard1);
                cardLabel1.setIcon(new ImageIcon(cardImage1));

                PlayingCard playingCard2 = deckOfCards.draw();
                BufferedImage cardImage2 = deckImage.getImage(playingCard2);
                cardLabel2.setIcon(new ImageIcon(cardImage2));

            }
        });
    }
}

package cardgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MainWindow {

    Random random = new Random();

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
                int suit1 = random.nextInt(4);
                int ordinal1 = random.nextInt(13);
                BufferedImage cardImage1 = deckImage.getImage(suit1, ordinal1);
                cardLabel1.setIcon(new ImageIcon(cardImage1));

                int suit2 = random.nextInt(4);
                int ordinal2 = random.nextInt(13);

                BufferedImage cardImage2 = deckImage.getImage(suit2, ordinal2);
                cardLabel2.setIcon(new ImageIcon(cardImage2));

            }
        });
    }
}

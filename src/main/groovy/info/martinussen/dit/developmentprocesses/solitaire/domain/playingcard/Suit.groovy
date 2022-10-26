package info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard

enum Suit {
    DIAMONDS('Diamonds','\u2662', Color.RED),
    CLUBS   ('Clubs',   '\u2663', Color.BLACK),
    HEARTS  ('Hearts',  '\u2661', Color.RED),
    SPADES  ('Spades',  '\u2660', Color.BLACK)

    public final String name
    public final String suitIndicator
    public final Color color

    private Suit(String name, String suitIndicator, Color color){
        this.name = name
        this.suitIndicator = suitIndicator
        this.color = color
    }
}


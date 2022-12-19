package info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard

interface PlayingCardState extends Comparable {

    Rank getRank()
    Integer getValue()
    Suit getSuit()
    Color getColor()
    Boolean isFaceDown()
    Boolean isFaceUp()
    PlayingCardAttributes getAttributes()
    void faceUp()
    void faceDown()

    Boolean hasSameColor(PlayingCard card)
    Boolean hasSameSuit(PlayingCard card)
    Boolean hasDifferentSuit(PlayingCard card)


    String toShortString()
}
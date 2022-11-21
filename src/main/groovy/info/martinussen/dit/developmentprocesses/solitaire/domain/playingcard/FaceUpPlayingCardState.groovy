package info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard

class FaceUpPlayingCardState implements PlayingCardState {

    PlayingCard receivingObject

    FaceUpPlayingCardState(PlayingCard receivingObject){
        this.receivingObject = receivingObject
    }

    @Override
    Boolean isFaceDown() {
        return false
    }

    @Override
    Boolean isFaceUp() {
        return true
    }

    @Override
    PlayingCardAttributes getAttributes() {
        return new PlayingCardAttributes(receivingObject._rank, receivingObject._suit, false)
    }

    @Override
    void faceUp() {
        receivingObject.currentState = receivingObject.faceUpPlayingCardState
    }

    @Override
    void faceDown() {
        receivingObject.currentState = receivingObject.faceDownPlayingCardState
    }

    @Override
    Color getColor() {
        return receivingObject._suit.color
    }

    @Override
    Integer getValue() {
        receivingObject._rank.value
    }

    @Override
    Rank getRank() {
        receivingObject._rank
    }

    @Override
    Suit getSuit() {
        receivingObject._suit
    }

    @Override
    Boolean hasSameColor(PlayingCard that) {
        assert that

        def returnValue = false

        if (receivingObject._suit.color == that.color){
            returnValue = true
        }

        return returnValue
    }

    @Override
    Boolean hasSameSuit(PlayingCard that) {
        assert that

        def returnValue = false

        if (receivingObject._suit == that.suit){
            returnValue = true
        }

        return returnValue
    }

    @Override
    Boolean hasDifferentSuit(PlayingCard that) {
        assert that

        def returnValue = true
        if (receivingObject._suit != that.suit){
            returnValue = false
        }

        return returnValue
    }

    @Override
    String toShortString() {
        return "$receivingObject._rank.symbol$receivingObject._suit.suitIndicator"
    }

    @Override
    String toString() {
        return "$receivingObject._rank.name of $receivingObject._suit.name"
    }

    @Override
    boolean equals(Object that) {
        assert that

        boolean returnValue = false

        if (that instanceof PlayingCard) {
            if (receivingObject._suit == that.suit && receivingObject._rank == that.rank){
                returnValue = true
            }
        }

        return returnValue
    }

    @Override
    int compareTo(Object o) {
        PlayingCard that = o as PlayingCard

        def returnValue = -1

        if (receivingObject._suit > that.suit) {
            returnValue = 1
        } else { // suit is equal or higher
            if (receivingObject._suit == that.suit){
                if (receivingObject._rank > that.rank){
                    returnValue = 1
                } else { // suit is equal, rank is equal or less
                    if (receivingObject._rank == that.rank){
                        returnValue = 0
                    }
                }
            }
        }
        return returnValue
    }
}
// https://en.wikipedia.org/wiki/Delegation_pattern
package info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard

class FaceDownPlayingCardState implements PlayingCardState {

    PlayingCard receivingObject

    FaceDownPlayingCardState(PlayingCard playingCard){
        receivingObject = playingCard
    }

    @Override
    Boolean isFaceDown() {
        return true
    }

    @Override
    Boolean isFaceUp() {
        return false
    }

    @Override
    PlayingCardAttributes getAttributes() {
        return new PlayingCardAttributes(receivingObject._rank, receivingObject._suit, true)
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
        return null
    }

    @Override
    Integer getValue() {
        return null
    }

    @Override
    Rank getRank() {
        return null
    }

    @Override
    Suit getSuit() {
        return null
    }

    @Override
    Boolean hasSameColor(PlayingCard card) {
        return null
    }

    @Override
    Boolean hasSameSuit(PlayingCard card) {
        return null
    }

    @Override
    Boolean hasDifferentSuit(PlayingCard card) {
        return null
    }

    @Override
    String toShortString() {
        return '*'
    }

    @Override
    String toString(){
        'Face Down'
    }

    @Override
    boolean equals(Object that) {
        return false
    }

    @Override
    int compareTo(Object o) {
        return -1
    }
}
// https://en.wikipedia.org/wiki/Delegation_pattern
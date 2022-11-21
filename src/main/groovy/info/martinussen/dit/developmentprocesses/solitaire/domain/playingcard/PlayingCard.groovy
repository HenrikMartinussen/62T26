package info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard

import groovy.transform.PackageScope

/**
 * Represents one of 52 unique playing cards of a deck of cards
 * Playing cards know their natural order (implements Comparable)
 * - lowest rank being Ace highest rank being King.
 * Natural order of suits are Diamonds, Clubs, Hearts, Spades (in ascending order)
 */
class PlayingCard implements Comparable {

    @PackageScope
    final Suit _suit
    @PackageScope
    final Rank _rank

    PlayingCardState currentState
    PlayingCardState faceDownPlayingCardState
    PlayingCardState faceUpPlayingCardState

    PlayingCard(Rank rank, Suit suit){
        _rank = rank
        _suit = suit
        this.faceDownPlayingCardState = new FaceDownPlayingCardState(this)
        this.faceUpPlayingCardState   = new FaceUpPlayingCardState(this)
        this.currentState             = faceUpPlayingCardState // initial state
    }

    Color getColor(){
        return currentState.getColor()
    }

    Integer getValue(){
        return currentState.getValue()
    }

    Rank getRank() {
        return currentState.getRank()
    }

    Suit getSuit() {
        return currentState.getSuit()
    }

    PlayingCard faceDown(){
        this.currentState.faceDown()
        return this
    }

    PlayingCard faceUp(){
        this.currentState.faceUp()
        return this
    }

    Boolean isFaceDown(){
        return currentState.isFaceDown()
    }

    Boolean isFaceUp() {
        return currentState.isFaceUp()
    }

    String toString(){
        return currentState.toString()
    }

    String toShortString(){
        return currentState.toShortString()
    }

    Boolean hasSameColor(PlayingCard that){
        assert that
        currentState.hasSameColor(that)
    }

    Boolean hasSameSuit(PlayingCard that){
        assert that
        currentState.hasSameSuit(that)
    }

    @Override
    boolean equals(Object that){
        currentState.equals(that)
    }

    @Override
    int compareTo(Object o) {
        currentState.compareTo(o)
    }

    PlayingCardAttributes getAttributes() {
        currentState.getAttributes()
    }
}


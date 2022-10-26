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
    PlayingCardState state


    PlayingCard(Rank rank, Suit suit){
        _rank = rank
        _suit = suit
        this.state = new FaceUpPlayingCardState(this) // initial state
    }

    Color getColor(){
        return state.getColor()
    }

    Integer getValue(){
        return state.getValue()
    }

    Rank getRank() {
        return state.getRank()
    }

    Suit getSuit() {
        return state.getSuit()
    }

    PlayingCard faceDown(){
        this.state = new FaceDownPlayingCardState(this)
        return this
    }

    PlayingCard faceUp(){
        this.state = new FaceUpPlayingCardState(this)
        return this
    }

    Boolean isFaceDown(){
        return state.isFaceDown()
    }

    Boolean isFaceUp() {
        return state.isFaceUp()
    }

    String toString(){
        return state.toString()
    }

    String toShortString(){
        return state.toShortString()
    }

    Boolean hasSameColor(PlayingCard that){
        assert that
        state.hasSameColor(that)
    }

    Boolean hasSameSuit(PlayingCard that){
        assert that
        state.hasSameSuit(that)
    }

    @Override
    boolean equals(Object that){
        state.equals(that)
    }

    @Override
    int compareTo(Object o) {
        state.compareTo(o)
    }


    PlayingCardAttributes getAttributes() {
        state.getAttributes()
    }
}


package info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard

import groovy.transform.ToString

/**
 * Behavior-less playing card attribute container
 * Is used to represent the information about a playing card - without being a playing card
 * objects of this class cannot be added to piles
 * objects of this class cannot change state
 * objects of this class has no behavior except being able to tell its suit (and color), rank (and value) and state (face up/face down)
 * If the Playing card which objects of this class represents are face down - null is returned for rank (and hence value) and suit (and hence color)
 * As syntactic sugar, objects of this class can both answer if they are face up and if they are face down - each returning the opposite Boolean value
 */
@ToString
class PlayingCardAttributes {
    final Rank rank
    final Suit suit
    final Boolean faceDown

    PlayingCardAttributes(Rank rank, Suit suit, Boolean faceDown) {
        this.rank = rank
        this.suit = suit
        this.faceDown = faceDown
    }

    Rank getRank() {
        rank
    }

    Suit getSuit() {
        suit
    }

    Color getColor() {
        suit?.color
    }

    Integer getValue() {
        rank?.value
    }

    Boolean isFaceUp() {
        !this.faceDown
    }

    Boolean isFaceDown() {
        this.faceDown
    }

}

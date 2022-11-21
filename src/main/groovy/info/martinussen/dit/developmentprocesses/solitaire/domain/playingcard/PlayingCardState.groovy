package info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit

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
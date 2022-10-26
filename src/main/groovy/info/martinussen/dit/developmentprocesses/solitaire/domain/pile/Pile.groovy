package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes

abstract class Pile {

    List cards

    Boolean isEmpty(){
        return cards?.isEmpty()
    }

    Boolean hasMoreCards(){
        return !cards?.isEmpty()
    }

    /**
     * Returns information about the topmost card, leaving it in its place. The cards in the Pile remain unchanged by this operation
     * In case the Pile is empty, null is returned
     * @return An immutable PlayingCardAttributes object  representing the attributes of the topmost card in this pile, null if this pile is empty
     */
    PlayingCardAttributes peek() {
        PlayingCardAttributes returnValue = null
        if (cards.size() > 0) {
            returnValue = cards.last().attributes
        }
        return returnValue
    }

    PlayingCard takeCard() {
        cards.pop()
    }

    void putCard(PlayingCard card) {
        cards.push(card)
    }

    abstract Boolean isAcceptable(PlayingCardAttributes cardAttributes)
}

// https://en.wikipedia.org/wiki/Glossary_of_patience_terms
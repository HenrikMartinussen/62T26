package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.SingleCardMove
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes

class StockPile extends Pile {

    StockPile(){
        this.cards = []
    }

    void putCards(List cards){
        assert cards // null or empty list is not accepted
        assert isAcceptable(cards)
        this.cards = cards
    }

    @Override
    Boolean isAcceptable(PlayingCardAttributes cardAttributes) {
        assert cardAttributes
        return false // no individual cards can be put (back) on the StockPile
    }

    /**
     *
     * @param listOfCardAttributes
     * @return true if this stock pile is empty and all cardAttributes in the list is face down. Otherwise false is returned
     */
    Boolean isAcceptable(List listOfCardAttributes) {
        assert listOfCardAttributes // null or empty list is not accepted
        def returnValue = false
        if (this.cards.isEmpty()) {
            if (listOfCardAttributes.every {it.isFaceDown()}) {
                returnValue = true
            }
        }
        return returnValue
    }

    @Override
    PlayingCard takeCard() {
        super.takeCard().faceUp()
    }

    SingleCardMove createMoveObject() {
        def moveObject = new SingleCardMove(this)
        return moveObject
    }

//    @Override
//    PlayingCardAttributes peek() {
//        PlayingCardAttributes returnValue = null
//        if (cards.size() > 0) {
//            if (cards.last().isFaceDown()) {
//                returnValue = cards.last().faceUp().attributes
//            } else {
//                returnValue = cards.last().attributes
//            }
//            returnValue = cards.last().faceUp().attributes // cards in a stockPile is faceDown
//        }
//        return returnValue
//    }

}

// https://playingcarddecks.com/blogs/how-to-play/solitaire-game-rules

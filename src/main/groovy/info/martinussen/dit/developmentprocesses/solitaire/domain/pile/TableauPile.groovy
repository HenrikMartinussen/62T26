package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.ColorRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.FaceUpRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.NoOpRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.RankRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.Rule

class TableauPile extends Pile {

    TableauPileState state

    Integer initialSize
    Boolean layoutComplete = false

    TableauPile(Integer initialSize){
        assert initialSize; assert initialSize > 0; assert initialSize < 8

        this.initialSize = initialSize

        state = new LayoutPhaseTableauPileState(this) // initial state

        this.cards = []
    }

    void addCard(PlayingCard card){
        assert card
        state.addCard(card)
    }

    @Override
    PlayingCard takeCard() {
        state.takeCard()
    }

    @Override
    Boolean isAcceptable(PlayingCardAttributes cardAttributes) {
        return state.isAcceptable(cardAttributes)
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
//            returnValue = cards.last().faceUp().attributes
//        }
//        return returnValue
//    }


    void startGame() {
        this.state = new GameStartedTableauPileState(this) // change state as game starts
    }

    Boolean isGameStarted(){
        return state.isGameStarted()
    }

    Boolean isLayoutComplete() {
        return layoutComplete
    }
}

//  https://playingcarddecks.com/blogs/how-to-play/solitaire-game-rules
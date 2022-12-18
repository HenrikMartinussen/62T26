package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes

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
package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.FaceRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.RankRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.Rule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.SuitRule

class FoundationPile extends Pile {

    Suit suit

    Rule suitRule
    Rule pileCompleteRule
    Rule faceUpRule
    Rule rankRule

    FoundationPile(Suit suit){
        this.suit = suit
        suitRule         = new SuitRule(suit)
        rankRule         = new RankRule(Rank.ACE)
        faceUpRule       = new FaceRule(true)
        pileCompleteRule = new RankRule(Rank.KING)
        cards = []
    }

    void addCard(PlayingCard card){
        assert card
        assert isAcceptable(card.attributes)
        cards.push(card)
        rankRule.next()
    }

    Boolean isComplete() {
        def returnValue = false

        if (pileCompleteRule.isValid(this.peek())) {
            returnValue = true
        }

        return returnValue
    }

    @Override
    Boolean isAcceptable(PlayingCardAttributes attributes) {
        def returnValue = false
        assert attributes
        if (faceUpRule.isValid(attributes) && suitRule.isValid(attributes) && rankRule.isValid(attributes) && !pileCompleteRule.isValid(this.peek())) {
            returnValue = true
        }
        return returnValue
    }
}

// foundation, foundation pile: A pile of cards, typically squared and face-up, and built on the bottom card which is the foundation card. As the tableau is cleared, cards are moved to the foundations.



package info.martinussen.dit.developmentprocesses.solitaire.domain.rule

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.FaceUpRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.Rule
import spock.lang.Specification

class FaceUpRuleSpec extends Specification {

    def 'Face up rule true should accept all face up cards'() {
        given:
        Rule faceUpRule = new FaceUpRule(true)
        def faceUpCard = new PlayingCard(Rank.SEVEN, Suit.DIAMONDS)
        assert faceUpCard.isFaceUp()
        def result

        when:
        result = faceUpRule.isValid(faceUpCard.attributes)

        then:
        result == true
    }

    def 'Face up rule true should not accept a face down card'() {
        given:
        Rule faceUpRule = new FaceUpRule(true)
        def faceUpCard = new PlayingCard(Rank.JACK, Suit.SPADES).faceDown()
        assert faceUpCard.isFaceDown()
        def result

        when:
        result = faceUpRule.isValid(faceUpCard.attributes)

        then:
        result == false
    }

}

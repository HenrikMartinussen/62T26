package info.martinussen.dit.developmentprocesses.solitaire.domain.rule

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.ColorRule
import spock.lang.Specification
import spock.lang.Unroll

import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color.*
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank.*
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit.*

class ColorRuleSpec extends Specification {

    @Unroll
    def 'ColorRule RED should accept #rank of #suit'(){
        given:
        def colorRule = new ColorRule(RED)
        def card = new PlayingCard(rank, suit)

        expect:
        colorRule.isValid(card.attributes)

        where:
        [rank, suit] << [(ACE..KING).toList(), [HEARTS, DIAMONDS]].combinations()
    }

    @Unroll
    def 'ColorRule RED should not accept #rank of #suit'(){
        given:
        def colorRule = new ColorRule(RED)
        def card = new PlayingCard(rank, suit)

        expect:
        !colorRule.isValid(card.attributes)

        where:
        [rank, suit] << [(ACE..KING).toList(), [CLUBS, SPADES]].combinations()
    }

    @Unroll
    def 'ColorRule BLACK should accept #rank of #suit'(){
        given:
        def colorRule = new ColorRule(BLACK)
        def card = new PlayingCard(rank, suit)

        expect:
        colorRule.isValid(card.attributes)

        where:
        [rank, suit] << [(ACE..KING).toList(), [SPADES, CLUBS]].combinations()
    }

    @Unroll
    def 'ColorRule BLACK should not accept #rank of #suit'(){
        given:
        def colorRule = new ColorRule(BLACK)
        def card = new PlayingCard(rank, suit)

        expect:
        !colorRule.isValid(card.attributes)

        where:
        [rank, suit] << [(ACE..KING).toList(), [DIAMONDS, HEARTS]].combinations()
    }

    def 'ColorRule should react to the increment operator by accepting the opposite color'() {
        given:
        def colorRule = new ColorRule(RED)
        def redCard = new PlayingCard(ACE, DIAMONDS)
        def blackCard = new PlayingCard(EIGHT, SPADES)
        assert colorRule.isValid(redCard.attributes)
        assert !colorRule.isValid(blackCard.attributes)

        when:
        colorRule.next()

        then:
        colorRule.isValid(blackCard.attributes)
        !colorRule.isValid(redCard.attributes)
    }

    def 'ColorRule should react to the decrement operator by accepting the opposite color'() {
        given:
        def colorRule = new ColorRule(RED)
        def redCard = new PlayingCard(ACE, HEARTS)
        def blackCard = new PlayingCard(EIGHT, CLUBS)
        assert colorRule.isValid(redCard.attributes)
        assert !colorRule.isValid(blackCard.attributes)

        when:
        colorRule.previous()

        then:
        colorRule.isValid(blackCard.attributes)
        !colorRule.isValid(redCard.attributes)
    }

}

package info.martinussen.dit.developmentprocesses.solitaire.domain.rule

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.ColorRule
import spock.lang.Specification
import spock.lang.Unroll

class ColorRuleSpec extends Specification {

    @Unroll
    def 'ColorRule RED should accept #rank of HEARTS'(){
        given:
        def colorRule = new ColorRule(Color.RED)
        def card = new PlayingCard(rank, Suit.HEARTS)

        expect:
        colorRule.isValid(card.attributes)

        where:
        rank << (Rank.ACE..Rank.KING)
    }

    @Unroll
    def 'ColorRule RED should accept #rank of DIAMONDS'(){
        given:
        def colorRule = new ColorRule(Color.RED)
        def card = new PlayingCard(rank, Suit.DIAMONDS)

        expect:
        colorRule.isValid(card.attributes)

        where:
        rank << (Rank.ACE..Rank.KING)
    }

    @Unroll
    def 'ColorRule RED should not accept #rank of SPADES'(){
        given:
        def colorRule = new ColorRule(Color.RED)
        def card = new PlayingCard(rank, Suit.SPADES)

        expect:
        !colorRule.isValid(card.attributes)

        where:
        rank << (Rank.ACE..Rank.KING)
    }

    @Unroll
    def 'ColorRule RED should not accept #rank of CLUBS'(){
        given:
        def colorRule = new ColorRule(Color.RED)
        def card = new PlayingCard(rank, Suit.CLUBS)

        expect:
        !colorRule.isValid(card.attributes)

        where:
        rank << (Rank.ACE..Rank.KING)
    }

    @Unroll
    def 'ColorRule BLACK should accept #rank of SPADES'(){
        given:
        def colorRule = new ColorRule(Color.BLACK)
        def card = new PlayingCard(rank, Suit.SPADES)

        expect:
        colorRule.isValid(card.attributes)

        where:
        rank << (Rank.ACE..Rank.KING)
    }

    @Unroll
    def 'ColorRule BLACK should accept #rank of CLUBS'(){
        given:
        def colorRule = new ColorRule(Color.BLACK)
        def card = new PlayingCard(rank, Suit.CLUBS)

        expect:
        colorRule.isValid(card.attributes)

        where:
        rank << (Rank.ACE..Rank.KING)
    }

    @Unroll
    def 'ColorRule BLACK should not accept #rank of HEARTS'(){
        given:
        def colorRule = new ColorRule(Color.BLACK)
        def card = new PlayingCard(rank, Suit.HEARTS)

        expect:
        !colorRule.isValid(card.attributes)

        where:
        rank << (Rank.ACE..Rank.KING)
    }

    @Unroll
    def 'ColorRule BLACK should not accept #rank of DIAMONDS'(){
        given:
        def colorRule = new ColorRule(Color.BLACK)
        def card = new PlayingCard(rank, Suit.DIAMONDS)

        expect:
        !colorRule.isValid(card.attributes)

        where:
        rank << (Rank.ACE..Rank.KING)
    }

    def 'ColorRule should react to the increment operator by accepting the opposite color'() {
        given:
        def colorRule = new ColorRule(Color.RED)
        def redCard = new PlayingCard(Rank.ACE, Suit.DIAMONDS)
        def blackCard = new PlayingCard(Rank.EIGHT, Suit.SPADES)
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
        def colorRule = new ColorRule(Color.RED)
        def redCard = new PlayingCard(Rank.ACE, Suit.HEARTS)
        def blackCard = new PlayingCard(Rank.EIGHT, Suit.CLUBS)
        assert colorRule.isValid(redCard.attributes)
        assert !colorRule.isValid(blackCard.attributes)

        when:
        colorRule.previous()

        then:
        colorRule.isValid(blackCard.attributes)
        !colorRule.isValid(redCard.attributes)
    }

}

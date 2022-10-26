package info.martinussen.dit.developmentprocesses.solitaire.domain.rule

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.RankRule
import spock.lang.Specification

class RankRuleSpec extends Specification {

    def 'Rank rule should accept cards of its specified rank, and not accept other ranks'() {
        given:
        def ranks = (Rank.ACE..Rank.KING) as List
        def rankRule

        when:
        rankRule = new RankRule(rank)

        then:
        rankRule.isValid(new PlayingCard(rank, Suit.CLUBS).attributes)

        and:
        (ranks - rank).each {inValidRank ->
            !rankRule.isValid(new PlayingCard(inValidRank, Suit.HEARTS).attributes)
        }

        where:
        rank << (Rank.ACE..Rank.KING)

    }

    def 'Rank rule should react to increment operator by accepting a card with the next higher rank'() {
        given:
        def rankRule = new RankRule(Rank.SEVEN)
        def sevenOfClubs = new PlayingCard(Rank.SEVEN, Suit.CLUBS)
        def eightOfDiamonds = new PlayingCard(Rank.EIGHT, Suit.DIAMONDS)
        assert rankRule.isValid(sevenOfClubs.attributes)
        assert !rankRule.isValid(eightOfDiamonds.attributes)

        when:
        rankRule++

        then:
        !rankRule.isValid(sevenOfClubs.attributes)
        rankRule.isValid(eightOfDiamonds.attributes)
    }

    def 'When rank is maxed out, Rank rule should react to increment operator by keeping the accepted rank (KING)'() {
        given:
        def rankRule = new RankRule(Rank.QUEEN)
        def queenOfClubs = new PlayingCard(Rank.QUEEN, Suit.CLUBS)
        def kingOfDiamonds = new PlayingCard(Rank.KING, Suit.DIAMONDS)
        assert rankRule.isValid(queenOfClubs.attributes)
        assert !rankRule.isValid(kingOfDiamonds.attributes)

        when:
        rankRule++

        then:
        !rankRule.isValid(queenOfClubs.attributes)
        rankRule.isValid(kingOfDiamonds.attributes)

        when:
        rankRule++

        then:
        !rankRule.isValid(queenOfClubs.attributes)
        rankRule.isValid(kingOfDiamonds.attributes)
    }

    def 'Rank rule should react to decrement operator by accepting a card with the next lower rank'() {
        given:
        def rankRule = new RankRule(Rank.EIGHT)
        def sevenOfClubs = new PlayingCard(Rank.SEVEN, Suit.CLUBS)
        def eightOfDiamonds = new PlayingCard(Rank.EIGHT, Suit.DIAMONDS)
        assert rankRule.isValid(eightOfDiamonds.attributes)
        assert !rankRule.isValid(sevenOfClubs.attributes)

        when:
        rankRule--

        then:
        !rankRule.isValid(eightOfDiamonds.attributes)
        rankRule.isValid(sevenOfClubs.attributes)
    }

    def 'When rank is already at minimum, Rank rule should react to decrement operator  by keeping the accepted rank (ACE)'() {
        given:
        def rankRule = new RankRule(Rank.TWO)
        def twoOfSpades = new PlayingCard(Rank.TWO, Suit.SPADES)
        def aceOfHearts = new PlayingCard(Rank.ACE, Suit.HEARTS)
        assert rankRule.isValid(twoOfSpades.attributes)
        assert !rankRule.isValid(aceOfHearts.attributes)

        when:
        rankRule--

        then:
        !rankRule.isValid(twoOfSpades.attributes)
        rankRule.isValid(aceOfHearts.attributes)

        when:
        rankRule--

        then:
        !rankRule.isValid(twoOfSpades.attributes)
        rankRule.isValid(aceOfHearts.attributes)
    }

}

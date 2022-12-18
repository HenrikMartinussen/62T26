package info.martinussen.dit.developmentprocesses.solitaire.domain.rule

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.FaceRule
import info.martinussen.dit.developmentprocesses.solitaire.domain.rules.Rule
import spock.lang.Specification

import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank.*
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit.*

class FaceRuleSpec extends Specification {

    def 'Face up rule should accept a face up card'() {
        given:
        Rule faceUpRule = new FaceRule(true)
        def faceUpCard = new PlayingCard(SEVEN, DIAMONDS)
        assert faceUpCard.isFaceUp()
        def result

        when:
        result = faceUpRule.isValid(faceUpCard.attributes)

        then:
        result == true
    }

    def 'Face up rule should not accept a face down card'() {
        given:
        Rule faceUpRule = new FaceRule(true)
        def faceUpCard = new PlayingCard(JACK, SPADES).faceDown()
        assert faceUpCard.isFaceDown()
        def result

        when:
        result = faceUpRule.isValid(faceUpCard.attributes)

        then:
        result == false
    }

    def 'Face down rule should accept face down card'() {
        given:
        Rule faceDownRule = new FaceRule(false)
        def faceDownCard = new PlayingCard(FOUR, CLUBS).faceDown()
        assert faceDownCard.isFaceDown()
        def result

        when:
        result = faceDownRule.isValid(faceDownCard.attributes)

        then:
        result == true
    }

    def 'Face down rule should not accept face up card'() {
        given:
        Rule faceDownRule = new FaceRule(false)
        def faceUpCard = new PlayingCard(JACK, SPADES)
        assert faceUpCard.isFaceUp()
        def result

        when:
        result = faceDownRule.isValid(faceUpCard.attributes)

        then:
        result == false
    }

    def 'Face up rule should react to the increment operator by becoming face down rule'(){
        given:
        Rule faceRule = new FaceRule(true)
        def faceUpCard = new PlayingCard(KING, HEARTS)
        assert faceUpCard.isFaceUp()
        def faceDownCard = new PlayingCard(EIGHT, SPADES).faceDown()
        assert faceDownCard.isFaceDown()
        assert faceRule.isValid(faceUpCard.attributes)   == true
        assert faceRule.isValid(faceDownCard.attributes) == false

        when:
        faceRule.next()

        then:
        faceRule.isValid(faceDownCard.attributes)
        !faceRule.isValid(faceUpCard.attributes)
    }

    def 'Face down rule should react to the increment operator by becoming face up rule'(){
        given:
        Rule faceRule = new FaceRule(false)
        def faceUpCard = new PlayingCard(TWO, DIAMONDS)
        assert faceUpCard.isFaceUp()
        def faceDownCard = new PlayingCard(Rank.FIVE, CLUBS).faceDown()
        assert faceDownCard.isFaceDown()
        assert faceRule.isValid(faceUpCard.attributes)   == false
        assert faceRule.isValid(faceDownCard.attributes) == true

        when:
        faceRule.next()

        then:
        !faceRule.isValid(faceDownCard.attributes)
        faceRule.isValid(faceUpCard.attributes)
    }

    def 'Face up rule should react to the decrement operator by becoming face down rule'(){
        given:
        Rule faceRule = new FaceRule(true)
        def faceUpCard = new PlayingCard(ACE, DIAMONDS)
        assert faceUpCard.isFaceUp()
        def faceDownCard = new PlayingCard(NINE, CLUBS).faceDown()
        assert faceDownCard.isFaceDown()
        assert faceRule.isValid(faceUpCard.attributes)   == true
        assert faceRule.isValid(faceDownCard.attributes) == false

        when:
        faceRule.previous()

        then:
        faceRule.isValid(faceDownCard.attributes)
        !faceRule.isValid(faceUpCard.attributes)
    }

    def 'Face down rule should react to the decrement operator by becoming face up rule'(){
        given:
        Rule faceRule = new FaceRule(false)
        def faceUpCard = new PlayingCard(THREE, HEARTS)
        assert faceUpCard.isFaceUp()
        def faceDownCard = new PlayingCard(FOUR, SPADES).faceDown()
        assert faceDownCard.isFaceDown()
        assert faceRule.isValid(faceUpCard.attributes)   == false
        assert faceRule.isValid(faceDownCard.attributes) == true

        when:
        faceRule.previous()

        then:
        !faceRule.isValid(faceDownCard.attributes)
        faceRule.isValid(faceUpCard.attributes)
    }

}

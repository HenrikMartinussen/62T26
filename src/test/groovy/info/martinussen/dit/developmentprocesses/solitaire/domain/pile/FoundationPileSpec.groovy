package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import org.codehaus.groovy.runtime.powerassert.PowerAssertionError
import spock.lang.Specification
import spock.lang.Unroll

import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank.*
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank.QUEEN
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit.*

class FoundationPileSpec extends Specification {

    @Unroll
    def 'empty foundation pile with suit #suit should report that it is empty and not yet complete'() {
        given:
        def foundationPile = new FoundationPile(suit)

        expect:
        foundationPile.getSuit() == suit
        foundationPile.isEmpty()
        !foundationPile.isComplete()

        where:
        suit << (DIAMONDS..SPADES)
    }

    @Unroll
    def 'empty #suit foundation pile should answer that Ace of #suit would be accepted, if added'() {
        given:
        def spadesFoundationPile = new FoundationPile(suit)
        assert spadesFoundationPile.isEmpty()
        assert !spadesFoundationPile.isComplete()

        expect:
        spadesFoundationPile.isAcceptable(new PlayingCard(ACE, suit).attributes)

        where:
        suit << (DIAMONDS..SPADES)
    }

    @Unroll
    def 'empty Spades foundation pile should answer that Ace of another suit (e.g. #suit) would not be accepted, if added'() {
        given:
        def spadesFoundationPile = new FoundationPile(SPADES)

        expect:
        !spadesFoundationPile.isAcceptable(new PlayingCard(ACE, suit).attributes)

        where:
        suit << (DIAMONDS..HEARTS)
    }

    @Unroll
    def 'empty foundation pile should answer that a face down Ace of its designated suit (e.g. #suit) would not be accepted'() {
        given:
        def foundationPile = new FoundationPile(suit)
        def faceDownAce = new PlayingCard(ACE, suit).faceDown()

        expect:
        !foundationPile.isAcceptable(faceDownAce.attributes)

        where:
        suit << (DIAMONDS..SPADES)
    }

    def 'empty foundation pile should accept Ace of its designated suit to be added to the pile'() {
        given:
        def spadesFoundationPile = new FoundationPile(SPADES)
        assert spadesFoundationPile.isEmpty()
        assert !spadesFoundationPile.isComplete()

        when:
        spadesFoundationPile.addCard(new PlayingCard(ACE, SPADES))
        def topOfPile = spadesFoundationPile.peek()

        then:
        topOfPile.rank == ACE
        topOfPile.suit == SPADES
        !topOfPile.faceDown
        topOfPile.faceUp
        !spadesFoundationPile.isEmpty()
        !spadesFoundationPile.isComplete()
    }

    @Unroll
    def 'empty foundation pile should throw an error when it is attempted to add an Ace of a suit (e.g. #suit) other than its designated suit'() {
        given:
        def spadesFoundationPile = new FoundationPile(SPADES)
        def offSuitAce = new PlayingCard(ACE, suit)

        assert !spadesFoundationPile.isAcceptable(offSuitAce.attributes)

        when:
        spadesFoundationPile.addCard(offSuitAce)

        then:
        thrown PowerAssertionError

        where:
        suit << (DIAMONDS..HEARTS)
    }

    @Unroll
    def 'empty foundation pile should throw an error, if it is attempted to add a face down Ace, of its designated suit (e.g. #suit)'() {
        given:
        def foundationPile = new FoundationPile(suit)

        def faceDownAceOfCorrectSuit = new PlayingCard(ACE, suit).faceDown()
        assert !foundationPile.isAcceptable(faceDownAceOfCorrectSuit.attributes)

        when:
        foundationPile.addCard(faceDownAceOfCorrectSuit)

        then:
        thrown PowerAssertionError

        where:
        suit << (DIAMONDS..SPADES)
    }

    @Unroll
    def 'empty foundation pile should report when asked that #rank is not accepted as its first card'() {
        given:
        def suit = SPADES
        def spadeFoundationPile = new FoundationPile(suit)
        def unexpectedRankCard = new PlayingCard(rank, suit) // correct suit, but unexpected rank


        when:
        def result = spadeFoundationPile.isAcceptable(unexpectedRankCard.attributes)

        then:
        result == false

        where:
        rank << (TWO..KING)
    }

    @Unroll
    def 'empty foundation pile should throw an error if it is attempted to add #rank, as its first card'() {
        given:
        def suit = SPADES
        def spadeFoundationPile = new FoundationPile(suit)
        def unexpectedRankCard = new PlayingCard(rank, suit) // correct suit, but unexpected rank

        assert !spadeFoundationPile.isAcceptable(unexpectedRankCard.attributes)

        when:
        spadeFoundationPile.addCard(unexpectedRankCard)

        then:
        thrown PowerAssertionError

        where:
        rank << (TWO..KING)
    }

    def 'foundation pile in progress should report that a card out of rank is not acceptable'() {
        given: 'a foundation pile in progress, holding cards up to, and including, Five (5)'
        Suit suit = SPADES

        def spadeFoundationPile = new FoundationPile(suit)

        (ACE..FIVE).each{ rank ->
            spadeFoundationPile.addCard new PlayingCard(rank, suit)
        }

        def cardOutOfRank = new PlayingCard(EIGHT, suit)

        when: 'you ask the foundation pile if a card out of rank, specifically eight (8), is acceptable... '
        def response = spadeFoundationPile.isAcceptable(cardOutOfRank.attributes)

        then:
        response == false

    }

    def 'foundation pile in progress should throw an error if it is attempted to add a card out of rank'() {
        given: 'a foundation pile in progress, holding cards up to, and including, Five (5)'
        Suit suit = SPADES

        def spadeFoundationPile = new FoundationPile(suit)

        (ACE..FIVE).each{ rank ->
            spadeFoundationPile.addCard new PlayingCard(rank, suit)
        }

        def cardOutOfRank = new PlayingCard(EIGHT, suit)
        assert !spadeFoundationPile.isAcceptable(cardOutOfRank.attributes)

        when: 'you, despite it not being acceptable, add a card out of rank, specifically eight (8)'
        spadeFoundationPile.addCard cardOutOfRank

        then: 'you get a slap on your wrist'
        thrown PowerAssertionError
    }

    def 'foundation pile in progress should report that a card of wrong suit is not acceptable'() {
        given: 'a Spades foundation pile in progress, holding cards up to, and including, Five of Spades '
        def spadeFoundationPile = new FoundationPile(SPADES)

        (ACE..FIVE).each{ rank ->
            spadeFoundationPile.addCard new PlayingCard(rank, SPADES)
        }

        when: 'you ask the foundation pile if a card of expected rank, but with wrong suit, is acceptable... '
        def wrongSuitCard = new PlayingCard(SIX, DIAMONDS)
        def response = spadeFoundationPile.isAcceptable(wrongSuitCard.attributes)

        then:
        response == false

    }

    def 'foundation pile in progress should throw an error when you attempt to add a card of wrong suit'() {
        given: 'a Spades foundation pile in progress, holding cards up to, and including, Five of Spades '
        def spadeFoundationPile = new FoundationPile(SPADES)

        (ACE..FIVE).each{ rank ->
            spadeFoundationPile.addCard new PlayingCard(rank, SPADES)
        }
        def wrongSuitCard = new PlayingCard(SIX, DIAMONDS)

        assert !spadeFoundationPile.isAcceptable(wrongSuitCard.attributes)

        when: 'you attempt to add a card of expected rank, but with wrong suit... '
        spadeFoundationPile.addCard wrongSuitCard

        then:
        thrown PowerAssertionError
    }

    def 'foundation pile holding two cards should return info regarding the latest added card when peeked'(){
        given:
        def foundationPile = new FoundationPile(HEARTS)
        foundationPile.addCard(new PlayingCard(ACE, HEARTS))
        foundationPile.addCard(new PlayingCard(TWO, HEARTS))

        when:
        def topOfPile = foundationPile.peek()

        then:
        topOfPile.rank == TWO
        topOfPile.suit == HEARTS
    }

    def 'foundation pile holding three cards should return info regarding the latest added card when peeked'(){
        given:
        def foundationPile = new FoundationPile(HEARTS)
        foundationPile.addCard(new PlayingCard(ACE, HEARTS))
        foundationPile.addCard(new PlayingCard(TWO, HEARTS))
        foundationPile.addCard(new PlayingCard(THREE, HEARTS))

        when:
        def topOfPile = foundationPile.peek()

        then:
        topOfPile.rank == THREE
        topOfPile.suit == HEARTS
    }

    def 'foundation pile should hand out cards according to LIFO principle'(){
        given:
        def foundationPile = new FoundationPile(HEARTS)
        foundationPile.addCard(new PlayingCard(ACE, HEARTS))
        foundationPile.addCard(new PlayingCard(TWO, HEARTS))
        foundationPile.addCard(new PlayingCard(THREE, HEARTS)) // Last In...

        when:
        def card = foundationPile.takeCard()

        then: '... First Out'
        card.rank == THREE
        card.suit == HEARTS

        when:
        card = foundationPile.takeCard()

        then:
        card.rank == TWO
        card.suit == HEARTS

        when:
        card = foundationPile.takeCard()

        then:
        card.rank == ACE
        card.suit == HEARTS
    }

    def 'foundation pile should indicate it is complete when it has been built up to and including "King"'(){
        given:
        def suit = DIAMONDS
        def foundationPile = new FoundationPile(suit)
        assert foundationPile.isEmpty()
        assert !foundationPile.isComplete()

        and:
        (ACE..QUEEN).each { rank ->
            foundationPile.addCard(new PlayingCard(rank, suit))
            assert !foundationPile.isEmpty()
            assert !foundationPile.isComplete()
        }

        when: 'topmost card of the foundation pile suit is added...'
        foundationPile.addCard(new PlayingCard(KING, suit))

        then: 'the foundation pile changes state to complete'
        !foundationPile.isEmpty()
        foundationPile.isComplete()

    }

}

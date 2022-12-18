package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import org.codehaus.groovy.runtime.powerassert.PowerAssertionError
import spock.lang.Specification

import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank.*
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit.*

class FoundationPileInProgressSpec extends Specification {

    def 'when face up Ace of its designated suit has been added to a an foundation pile, the ace should be on top of the pile and the pile should not be empty'() {
        given: 'An empty Spades foundation pile'
        def spadesFoundationPile = new FoundationPile(SPADES)
        assert spadesFoundationPile.isEmpty()
        assert !spadesFoundationPile.isComplete()

        and: 'Ace of Spades is added to the Spades foundation pile'
        spadesFoundationPile.addCard(new PlayingCard(ACE, SPADES))

        when: 'the top most card attributes are peeked'
        def topOfPile = spadesFoundationPile.peek()

        then: 'Ace of Spades should be on top of the pile'
        topOfPile.rank == ACE
        topOfPile.suit == SPADES

        and: 'the topmost card should be face up'
        !topOfPile.faceDown
        topOfPile.faceUp

        and: 'the foundation pile should not be empty'
        !spadesFoundationPile.isEmpty()

        and: 'it should be incomplete'
        !spadesFoundationPile.isComplete()
    }

    def 'foundation pile in progress should report that a card out of rank is not acceptable'() {
        given: 'a foundation pile in progress, holding cards up to, and including, Five (5)'
        Suit suit = SPADES

        def spadeFoundationPile = new FoundationPile(suit)

        (ACE..FIVE).each{ rank ->
            spadeFoundationPile.addCard new PlayingCard(rank, suit)
        }

        and: 'an out of rank card - specifically Eight (8)'
        def cardOutOfRank = new PlayingCard(EIGHT, suit)

        when: 'you ask the foundation pile if the out of rank card is acceptable... '
        def response = spadeFoundationPile.isAcceptable(cardOutOfRank.attributes)

        then: 'the response should be false'
        response == false

    }

    def 'foundation pile in progress should throw an error if it is attempted to add a card out of rank'() {
        given: 'a foundation pile in progress, holding cards up to, and including, Five (5)'
        Suit suit = SPADES

        def spadeFoundationPile = new FoundationPile(suit)

        (ACE..FIVE).each{ rank ->
            spadeFoundationPile.addCard new PlayingCard(rank, suit)
        }

        and: 'an out of rank card - specifically Eight (8)'
        def cardOutOfRank = new PlayingCard(EIGHT, suit)

        when: 'you add the out of rank card'
        spadeFoundationPile.addCard cardOutOfRank

        then: 'an error is expected to be thrown'
        thrown PowerAssertionError
    }

    def 'foundation pile in progress should report that a card of wrong suit is not acceptable'() {
        given: 'a Spades foundation pile in progress, holding cards up to, and including, Five of Spades '
        def spadeFoundationPile = new FoundationPile(SPADES)

        (ACE..FIVE).each{ rank ->
            spadeFoundationPile.addCard new PlayingCard(rank, SPADES)
        }

        and: 'a card with correct rank, but with wrong suit'
        def wrongSuitCard = new PlayingCard(SIX, DIAMONDS)


        when: 'you ask the foundation pile if the wrong suit, is acceptable... '
        def response = spadeFoundationPile.isAcceptable(wrongSuitCard.attributes)

        then: 'the response should be false'
        response == false

    }

    def 'foundation pile in progress should throw an error when you attempt to add a card of wrong suit'() {
        given: 'a Spades foundation pile in progress, holding cards up to, and including, Five of Spades '
        def spadeFoundationPile = new FoundationPile(SPADES)

        (ACE..FIVE).each{ rank ->
            spadeFoundationPile.addCard new PlayingCard(rank, SPADES)
        }

        and: 'a card with correct rank, but with wrong suit'
        def wrongSuitCard = new PlayingCard(SIX, DIAMONDS)

        when: 'you add the card with expected rank, but with wrong suit... '
        spadeFoundationPile.addCard wrongSuitCard

        then: 'an error is expected to be thrown'
        thrown PowerAssertionError
    }

    def 'foundation pile holding two cards should return info regarding the latest added card when peeked'(){
        given: 'a foundation pile holding two cards'
        def foundationPile = new FoundationPile(HEARTS)
        foundationPile.addCard(new PlayingCard(ACE, HEARTS))
        foundationPile.addCard(new PlayingCard(TWO, HEARTS))

        when: 'you peek the foundation pile for the attributes of the top most card'
        def topOfPile = foundationPile.peek()

        then: 'expect the attributes of the latest added (top most) card'
        topOfPile.rank == TWO
        topOfPile.suit == HEARTS
    }

    def 'foundation pile holding three cards should return info regarding the latest added card when peeked'(){
        given: 'a foundation pile holding three cards'
        def foundationPile = new FoundationPile(HEARTS)
        foundationPile.addCard(new PlayingCard(ACE, HEARTS))
        foundationPile.addCard(new PlayingCard(TWO, HEARTS))
        foundationPile.addCard(new PlayingCard(THREE, HEARTS))

        when: 'you peek the foundation pile for the attributes of the top most card'
        def topOfPile = foundationPile.peek()

        then: 'expect the attributes of the latest added (top most) card'
        topOfPile.rank == THREE
        topOfPile.suit == HEARTS
    }

    def 'foundation pile should hand out cards according to LIFO principle'(){
        given: 'A foundation pile with Three of Hearts as last added card'
        def foundationPile = new FoundationPile(HEARTS)
        foundationPile.addCard(new PlayingCard(ACE, HEARTS))
        foundationPile.addCard(new PlayingCard(TWO, HEARTS))
        foundationPile.addCard(new PlayingCard(THREE, HEARTS)) // Last In...

        when: 'a card is taken'
        def card = foundationPile.takeCard()

        then: 'the card is Three of Hearts'
        card.rank == THREE
        card.suit == HEARTS

        when: 'next card is taken'
        card = foundationPile.takeCard()

        then: 'the card is Two of Hearts'
        card.rank == TWO
        card.suit == HEARTS

        when: 'the last card is taken'
        card = foundationPile.takeCard()

        then: 'the card is Ace of Hearts'
        card.rank == ACE
        card.suit == HEARTS

        and: 'the foundation pile is expected to be empty'
        foundationPile.hasMoreCards() == false
    }

    def 'foundation pile should indicate it is complete when it has been built up to and including "King"'(){
        given: 'an empty Diamonds foundation pile'
        def suit = DIAMONDS
        def foundationPile = new FoundationPile(suit)
        assert foundationPile.isEmpty()
        assert !foundationPile.isComplete()

        and: 'sequentially add cards from Ace to Queen'
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

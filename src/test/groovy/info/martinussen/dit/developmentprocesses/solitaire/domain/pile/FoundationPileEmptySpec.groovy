package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import org.codehaus.groovy.runtime.powerassert.PowerAssertionError
import spock.lang.Specification
import spock.lang.Unroll

import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank.*
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit.*

class FoundationPileEmptySpec extends Specification {

    @Unroll
    def 'empty foundation pile with suit #suit should report that it is empty and not yet complete'() {
        given: 'an empty foundation pile'
        def foundationPile = new FoundationPile(suit)

        expect: 'it should be able to report its suit and the facts that it is empty and not yet complete'
        foundationPile.getSuit() == suit
        foundationPile.isEmpty()
        !foundationPile.isComplete()

        where:
        suit << (SPADES..CLUBS)
    }

    @Unroll
    def 'empty #suit foundation pile should answer that Ace of #suit would be accepted, if added'() {
        given: 'an empty foundation pile'
        def spadesFoundationPile = new FoundationPile(suit)
        assert spadesFoundationPile.isEmpty()
        assert !spadesFoundationPile.isComplete()

        expect: 'when asked if an Ace of correct suit is acceptable, the answer should be positive'
        spadesFoundationPile.isAcceptable(new PlayingCard(ACE, suit).attributes)

        where:
        suit << (SPADES..CLUBS)
    }

    @Unroll
    def 'empty Spades foundation pile should answer that an Ace of another suit (e.g. #suit) would not be accepted'() {
        given: 'an empty Spades foundation pile'
        def spadesFoundationPile = new FoundationPile(SPADES)

        and: 'an off suit Ace'
        def offSuitAce = new PlayingCard(ACE, suit)

        when: 'the foundation pile is asked if the off suit Ace is acceptable'
        def answer= spadesFoundationPile.isAcceptable(offSuitAce.attributes)

        then: 'the answer should be negative'
        answer == false

        where:
        suit << (HEARTS..CLUBS)
    }

    @Unroll
    def 'empty Spades foundation pile should throw an error when an Ace of another suit (e.g. #suit) is added'() {
        given: 'an empty Spades foundation pile'
        def spadesFoundationPile = new FoundationPile(SPADES)

        and: 'an off suit Ace'
        def offSuitAce = new PlayingCard(ACE, suit)

        when: 'the off suit ace is added to the foundation pile'
        spadesFoundationPile.addCard(offSuitAce)

        then: 'an error is expected to be thrown'
        thrown PowerAssertionError

        where:
        suit << (HEARTS..CLUBS)
    }

    @Unroll
    def 'empty foundation pile should answer that a face down Ace, of its designated suit (e.g. #suit), is not accepted as its first card'() {
        given: 'an empty Foundation pile'
        def foundationPile = new FoundationPile(suit)

        and: 'a face down Ace of correct suit'
        def faceDownAceOfCorrectSuit = new PlayingCard(ACE, suit).faceDown()

        when: 'when you ask the foundation pile if it is acceptable to add the Ace of correct suit face down'
        def answer = foundationPile.isAcceptable(faceDownAceOfCorrectSuit.attributes)

        then: 'the answer should be false'
        answer == false

        where:
        suit << (SPADES..CLUBS)
    }

    @Unroll
    def 'empty foundation pile should throw an error when a face down Ace, of its designated suit (e.g. #suit), is added'() {
        given: 'an empty foundation pile'
        def foundationPile = new FoundationPile(suit)

        and: 'a face down Ace of correct suit'
        def faceDownAceOfCorrectSuit = new PlayingCard(ACE, suit).faceDown()

        when: 'the face down ace is added to the foundation pile'
        foundationPile.addCard(faceDownAceOfCorrectSuit)

        then: 'an error is expected to be thrown'
        thrown PowerAssertionError

        where:
        suit << (SPADES..CLUBS)
    }

    @Unroll
    def 'empty foundation pile should answer that #rank is not accepted as its first card'() {
        given: 'an empty Spades Foundation pile'
        def suit = SPADES
        def spadeFoundationPile = new FoundationPile(suit)

        and: 'a card with correct suit, but unexpected rank'
        def unexpectedRankCard = new PlayingCard(rank, suit)

        when: 'when you ask the Spades foundation pile if it is acceptable to add the card with unexpected Rank as its first card'
        def answer = spadeFoundationPile.isAcceptable(unexpectedRankCard.attributes)

        then: 'the answer should be false'
        answer == false

        where:
        rank << (TWO..KING)
    }

    @Unroll
    def 'empty foundation pile should throw an error when #rank is added as its first card'() {
        given: 'An empty Spades foundation pile'
        def suit = SPADES
        def spadeFoundationPile = new FoundationPile(suit)

        and: 'a card with correct suit, but unexpected rank'
        def unexpectedRankCard = new PlayingCard(rank, suit)

        when: 'A card is added, which has unexpected Rank'
        spadeFoundationPile.addCard(unexpectedRankCard)

        then: 'an error is expected to be thrown'
        thrown PowerAssertionError

        where:
        rank << (TWO..KING)
    }

}

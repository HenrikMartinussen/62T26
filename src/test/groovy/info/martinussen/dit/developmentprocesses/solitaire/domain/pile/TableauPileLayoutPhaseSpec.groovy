package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.DeckOfCards
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import org.codehaus.groovy.runtime.powerassert.PowerAssertionError
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

//@Narrative()
@Title('Specification of a Tableau piles behavior in the layout phase, i.e. when the cards are initially dealt, before the game commences')
class TableauPileLayoutPhaseSpec extends Specification{

    @Unroll
    def 'a tableau pile cannot have an initial size of #size, only sizes between 1 and 7 are allowed'(){
        given:
        TableauPile tableauPile

        when:
        new TableauPile(size)

        then:
        thrown PowerAssertionError

        where:
        size << [-7, 0, 8, 42]
    }

    @Unroll
    def 'an empty tableau pile with initial size of #size should report that it is empty, layout not complete, game not started'(){
        given:
        def pile = new TableauPile(size)

        expect:
        assert pile.isEmpty()
        assert pile.isLayoutComplete() == false
        assert pile.isGameStarted() == false

        where:
        size << (1..7)
    }

    @Unroll
    def 'tableau pile should report that it is acceptable to add any face-up card e.g. #card to an empty tableau pile with initial size of 1'(){
        given:
        def pile = new TableauPile(1)
        assert pile.isEmpty()
        assert !pile.isGameStarted()
        assert !pile.isLayoutComplete()

        assert card.isFaceUp()

        def result

        when:
        result = pile.isAcceptable(card.attributes)

        then:
        result == true

        where:
        card                                     | _
        new PlayingCard(Rank.SEVEN, Suit.CLUBS)  | _
        new PlayingCard(Rank.KING, Suit.SPADES)  | _
        new PlayingCard(Rank.ACE, Suit.DIAMONDS) | _
        new PlayingCard(Rank.JACK, Suit.HEARTS)  | _
        new PlayingCard(Rank.QUEEN, Suit.CLUBS)  | _
    }


    @Unroll
    def 'it should be possible to add any face-up card e.g. #card to an empty tableau pile with initial size of 1, and the pile should then report that its layout is complete'(){
        given:
        def pile = new TableauPile(1)
        assert pile.isEmpty()
        assert !pile.isGameStarted()
        assert !pile.isLayoutComplete()

        assert card.isFaceUp()

        when:
        pile.addCard(card)

        then:
        !pile.isEmpty()
        pile.peek().suit == card.suit
        pile.peek().rank == card.rank
        pile.isLayoutComplete()
        !pile.isGameStarted()

        where:
        card                                     | _
        new PlayingCard(Rank.SEVEN, Suit.CLUBS)  | _
        new PlayingCard(Rank.KING, Suit.SPADES)  | _
        new PlayingCard(Rank.ACE, Suit.DIAMONDS) | _
        new PlayingCard(Rank.JACK, Suit.HEARTS)  | _
        new PlayingCard(Rank.QUEEN, Suit.CLUBS)  | _
    }

    @Unroll
    def 'tableau pile should report that it is not acceptable to add a face-up card to an empty tableau pile with initial size of #size in the layout phase'(){
        given:
        def result
        def pile = new TableauPile(size)
        assert pile.isEmpty()
        assert !pile.isGameStarted()
        assert !pile.isLayoutComplete()
        and:
        def card = new PlayingCard(Rank.QUEEN, Suit.HEARTS)
        assert card.isFaceUp()


        when:
        result = pile.isAcceptable(card.attributes)

        then:
        result == false

        where:
        size  <<  (2..7)
    }

    @Unroll
    def 'it should not be possible to add a face-up card to an empty tableau pile with initial size of #size in the layout phase'(){
        given:
        def pile = new TableauPile(size)
        assert pile.isEmpty()
        assert !pile.isGameStarted()
        assert !pile.isLayoutComplete()
        and:
        def card = new PlayingCard(Rank.QUEEN, Suit.HEARTS)
        assert card.isFaceUp()

        when:
        pile.addCard(card)

        then:
        thrown PowerAssertionError

        where:
        size  <<  (2..7)
    }

    def 'tableau pile should report that it is not acceptable to add a face-down card to an empty tableau pile with initial size of 1 in the layout phase'(){
        given:
        def pile = new TableauPile(1)
        assert pile.isEmpty()
        assert !pile.isGameStarted()
        assert !pile.isLayoutComplete()
        def card = new PlayingCard(Rank.QUEEN, Suit.HEARTS).faceDown()
        def result

        when:
        result = pile.isAcceptable(card.attributes)

        then:
        result == false
    }

    def 'it should not be possible to add a face-down card to an empty tableau pile with initial size of 1 in the layout phase'(){
        given:
        def pile = new TableauPile(1)
        assert pile.isEmpty()
        assert !pile.isGameStarted()
        assert !pile.isLayoutComplete()
        def card = new PlayingCard(Rank.QUEEN, Suit.HEARTS).faceDown()

        when:
        pile.addCard(card)

        then:
        thrown PowerAssertionError
    }

    @Unroll
    def 'it should be possible to add #faceDownCapacity face-down card(s) followed by one face-up card to an empty tableau pile with initial size of #size'(){
        given:
        def pile = new TableauPile(size)
        assert pile.isEmpty()
        assert pile.isGameStarted() == false
        def deck = new DeckOfCards()

        when:
        faceDownCapacity.times{
            pile.addCard(deck.draw().faceDown())
        }
        pile.addCard(deck.draw())

        then:
        !pile.isEmpty()
        pile.peek() != null
        pile.peek().suit != null
        pile.peek().rank != null
        pile.isLayoutComplete()

        where:
        size << (2..7)
        faceDownCapacity = size - 1
    }

    @Unroll
    def 'it should not be possible to add a face-up card to a tableau pile with initial size of #size, unless it is the last card'(){
        given: 'a new empty tableau pile'
        def pile = new TableauPile(size)
        assert pile.isEmpty()
        assert !pile.isGameStarted()
        assert !pile.isLayoutComplete()

        and: 'a new deck of cards'
        def deck = new DeckOfCards()

        and: 'add one too few face down cards'
        (faceDownCapacity - 1).times{
            pile.addCard(deck.draw().faceDown())
        }
        assert !pile.isEmpty()
        assert !pile.isLayoutComplete()

        when: 'adding a face-up card too early'
        pile.addCard(deck.draw()) // face up

        then:
        thrown PowerAssertionError

        where:
        size << (3..7)
        faceDownCapacity = size - 1
    }

    @Unroll
    def 'it should be possible to add no more than #faceDownCardCapacity consecutive face-down cards to an empty tableau pile with initial size of #size'(){
        given:
        def pile = new TableauPile(size)
        assert pile.isEmpty()
        assert pile.isGameStarted() == false

        def deck = new DeckOfCards()

        when:
        faceDownCardCapacity.times{
            pile.addCard(deck.draw().faceDown())
        }

        then:
        !pile.isEmpty()
        pile.peek().suit     != null
        pile.peek().rank     != null
        pile.peek().faceDown == true

        when: 'An additional face down card is added'
        pile.addCard(deck.draw().faceDown())

        then:
        thrown PowerAssertionError

        where:
        size << (2..7)
        faceDownCardCapacity = size - 1
    }


}

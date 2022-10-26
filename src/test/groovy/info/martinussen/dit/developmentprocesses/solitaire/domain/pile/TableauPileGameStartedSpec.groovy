package info.martinussen.dit.developmentprocesses.solitaire.domain.pile

import info.martinussen.dit.developmentprocesses.solitaire.domain.DeckOfCards
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import org.codehaus.groovy.runtime.powerassert.PowerAssertionError
import spock.lang.Specification
import spock.lang.Unroll

import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank.*
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit.*

class TableauPileGameStartedSpec extends Specification{

    @Unroll
    def 'tableau pile should should report that it is acceptable to add King of #suit to a cleared tableau pile when the game has started'(){
        given:
        def pile = buildEmptyStartedTableauPile(4)

        expect:
        pile.isAcceptable(new PlayingCard(KING, suit).attributes)

        where:
        suit << (DIAMONDS..SPADES)
    }

    @Unroll
    def 'it should be possible to add King of #suit to a cleared tableau pile when the game has started'(){
        given:
        def pile = buildEmptyStartedTableauPile(4)

        when:
        pile.addCard(new PlayingCard(KING, suit))

        then:
        !pile.isEmpty()
        pile.peek().suit == suit
        pile.peek().rank == KING

        where:
        suit << (DIAMONDS..SPADES)
    }

    @Unroll
    def 'it should not be possible to add cards other than Kings (e.g. #notAKing) to a cleared tableau pile'(){
        given:
        def pile = buildEmptyStartedTableauPile(1)

        when:
        pile.addCard(notAKing)

        then:
        thrown PowerAssertionError

        where:
        notAKing                                   | _
        new PlayingCard(SEVEN, SPADES)   | _
        new PlayingCard(ACE,   HEARTS)   | _
        new PlayingCard(JACK,  DIAMONDS) | _
        new PlayingCard(QUEEN, CLUBS) | _

    }

    @Unroll
    def 'it should be possible to build sequences from King and downwards in descending order and in alternating colors on a cleared tableau pile, regardless of its initial capacity (e.g. #size)'(){
        given:
        def pile = buildEmptyStartedTableauPile(size)

        when: 'the pile is filled, starting with a KING, according to the rule of alternating color'
        pile.addCard(new PlayingCard(KING, DIAMONDS)) // red
        pile.addCard(new PlayingCard(QUEEN, CLUBS))   // black
        pile.addCard(new PlayingCard(JACK, HEARTS))   // red
        pile.addCard(new PlayingCard(TEN, SPADES))    // black
        pile.addCard(new PlayingCard(NINE, HEARTS))   // red
        pile.addCard(new PlayingCard(EIGHT, CLUBS))   // black
        pile.addCard(new PlayingCard(SEVEN, HEARTS))  // red
        pile.addCard(new PlayingCard(SIX, SPADES))    // black
        pile.addCard(new PlayingCard(FIVE, HEARTS))   // red

        then:
        !pile.isEmpty()
        pile.peek().suit == HEARTS
        pile.peek().rank == FIVE

        where:
        size << (1..7)
    }

    @Unroll
    def 'it should not be possible to add cards to a completely built sequences from King downwards to Ace, regardless of its initial capacity (e.g. #size)'(){
        given:
        def pile = buildEmptyStartedTableauPile(size)

        and: 'complete the pile by filling it, starting with a KING, according to the rule of alternating color'
        pile.addCard(new PlayingCard(KING,  DIAMONDS)) // red
        pile.addCard(new PlayingCard(QUEEN, CLUBS))    // black
        pile.addCard(new PlayingCard(JACK,  HEARTS))   // red
        pile.addCard(new PlayingCard(TEN,   SPADES))   // black
        pile.addCard(new PlayingCard(NINE,  HEARTS))   // red
        pile.addCard(new PlayingCard(EIGHT, CLUBS))    // black
        pile.addCard(new PlayingCard(SEVEN, HEARTS))   // red
        pile.addCard(new PlayingCard(SIX,   SPADES))   // black
        pile.addCard(new PlayingCard(FIVE,  HEARTS))   // red
        pile.addCard(new PlayingCard(FOUR,  CLUBS))    // black
        pile.addCard(new PlayingCard(THREE, HEARTS))   // red
        pile.addCard(new PlayingCard(TWO,   CLUBS))    // black
        pile.addCard(new PlayingCard(ACE,   DIAMONDS)) // red   - Pile is "complete"

        when: 'add an additional black Ace'
        pile.addCard(new PlayingCard(ACE,   SPADES))   // black

        then:
        thrown PowerAssertionError

        where:
        size << (1..7)
    }

    @Unroll
    def 'it should be possible to remove the last card from a completely built sequence, from King downwards to Ace, and then add it again, regardless of the piles initial capacity (e.g. #size)'(){
        given:
        def pile = buildEmptyStartedTableauPile(size)

        and: 'complete the pile by filling it, starting with a KING, according to the rule of alternating color'
        pile.addCard(new PlayingCard(KING,  DIAMONDS)) // red
        pile.addCard(new PlayingCard(QUEEN, CLUBS))    // black
        pile.addCard(new PlayingCard(JACK,  HEARTS))   // red
        pile.addCard(new PlayingCard(TEN,   SPADES))   // black
        pile.addCard(new PlayingCard(NINE,  HEARTS))   // red
        pile.addCard(new PlayingCard(EIGHT, CLUBS))    // black
        pile.addCard(new PlayingCard(SEVEN, HEARTS))   // red
        pile.addCard(new PlayingCard(SIX,   SPADES))   // black
        pile.addCard(new PlayingCard(FIVE,  HEARTS))   // red
        pile.addCard(new PlayingCard(FOUR,  CLUBS))    // black
        pile.addCard(new PlayingCard(THREE, HEARTS))   // red
        pile.addCard(new PlayingCard(TWO,   CLUBS))    // black
        pile.addCard(new PlayingCard(ACE,   DIAMONDS)) // red   - Pile is "complete"

        when: 'the last card is removed'
        def lastCard =  pile.takeCard()

        then: 'last card should be Ace of Diamonds'
        lastCard.rank == ACE
        lastCard.suit == DIAMONDS

        and: 'top card on the pile should be Two of Clubs'
        pile.peek().rank == TWO
        pile.peek().suit == CLUBS

        when: 'add the removed card again'
        pile.addCard(lastCard)

        then: 'top card on the pile should now be Ace of Diamonds'
        pile.peek().rank == ACE
        pile.peek().suit == DIAMONDS

        where:
        size << (1..7)
    }

    @Unroll
    def 'it should not be possible to add face-down cards (not even Kings) (e.g. #suit) to a cleared tableau pile'(){
        given:
        def pile = buildEmptyStartedTableauPile(1)

        when:
        pile.addCard(new PlayingCard(KING, suit).faceDown())

        then:
        thrown PowerAssertionError

        where:
        suit     | _
        SPADES   | _
        HEARTS   | _
        DIAMONDS | _
        CLUBS    | _

    }

    def 'tableau pile should report that it is not acceptable to add face-down cards to a laid out tableau pile, after the game has started'(){
        given:
        TableauPile pile = new TableauPile(1)
        assert pile.isEmpty()
        pile.addCard(new PlayingCard(KING, DIAMONDS))
        assert pile.isLayoutComplete()
        pile.startGame()
        assert pile.isGameStarted()

        expect: 'card which adheres to the rank and color rules, but is face down, is not expected to be acceptable'
        !pile.isAcceptable(new PlayingCard(QUEEN, CLUBS).faceDown().attributes)
    }

    def 'it should not be possible to add face-down cards to a laid out tableau pile, after the game has started'(){
        given:
        TableauPile pile = new TableauPile(1)
        assert pile.empty
        pile.addCard(new PlayingCard(KING, DIAMONDS))
        assert pile.layoutComplete
        pile.startGame()
        assert pile.isGameStarted()

        when: 'Adding card that adheres to the rank and color rules, but is face down'
        pile.addCard(new PlayingCard(QUEEN, CLUBS).faceDown())

        then:
        thrown PowerAssertionError
    }

    def 'it should not be possible to add a card in the same color as the top card, when the game is started'(){
        given:
        def pile = buildEmptyStartedTableauPile(1)

        when:
        pile.addCard(new PlayingCard(KING,  HEARTS))   // Red
        pile.addCard(new PlayingCard(QUEEN, SPADES))   // Black
        pile.addCard(new PlayingCard(JACK,  DIAMONDS)) // Red
        pile.addCard(new PlayingCard(TEN,   HEARTS))   // Red again - in violation of the rules

        then:
        thrown PowerAssertionError
    }

    def 'tableau pile should report that it is acceptable to add a card, according to the rules, to a laid out pile when the game starts'() {
        given:
        def pile = new TableauPile(3)
        assert pile.isEmpty()
        pile.addCard(new PlayingCard(EIGHT, CLUBS).faceDown())  // Not a King ;-)
        pile.addCard(new PlayingCard(TEN,   SPADES).faceDown()) // same color
        pile.addCard(new PlayingCard(QUEEN, SPADES))            // same color
        assert pile.isLayoutComplete()
        pile.startGame()
        assert pile.isGameStarted()

        when:
        def result = pile.isAcceptable(new PlayingCard(JACK, HEARTS).attributes)

        then:
        result == true
    }

    def 'it should be possible to add a card, according to the rules, to a laid out pile when the game starts'() {
        given:
        def pile = new TableauPile(3)
        assert pile.isEmpty()
        pile.addCard(new PlayingCard(JACK,  DIAMONDS).faceDown()) // Not a King ;-)
        pile.addCard(new PlayingCard(THREE, HEARTS).faceDown())   // same color
        pile.addCard(new PlayingCard(FOUR,  DIAMONDS))            // same color
        assert pile.isLayoutComplete()
        pile.startGame()
        assert pile.isGameStarted()

        when:
        pile.addCard(new PlayingCard(THREE, CLUBS))

        then:
        def attributes = pile.peek()
        attributes.rank == THREE
        attributes.suit == CLUBS
    }

    def 'tableau pile should report that it is not acceptable to add a card, in conflict with the rank rules, to a laid out pile when the game has been started'() {
        given:
        def pile = new TableauPile(3)
        assert pile.isEmpty()
        pile.addCard(new PlayingCard(ACE,   CLUBS).faceDown())  // Not a King ;-)
        pile.addCard(new PlayingCard(TWO,   SPADES).faceDown()) // same color
        pile.addCard(new PlayingCard(EIGHT, SPADES))            // same color
        assert pile.isLayoutComplete()
        pile.startGame()
        assert pile.isGameStarted()

        when:
        def result = pile.isAcceptable(new PlayingCard(SIX, HEARTS).attributes) // correct color wrong rank (should have been a SEVEN)

        then:
        result == false
    }

    def 'it should not be possible to add a card, in conflict with the rank rules, to a laid out pile when the game starts'() {
        given:
        def pile = new TableauPile(3)
        assert pile.isEmpty()
        pile.addCard(new PlayingCard(THREE,  DIAMONDS).faceDown()) // Not a King ;-)
        pile.addCard(new PlayingCard(THREE, HEARTS).faceDown())    // same color
        pile.addCard(new PlayingCard(FIVE,  DIAMONDS))              // same color
        assert pile.isLayoutComplete()
        pile.startGame()
        assert pile.isGameStarted()

        when:
        pile.addCard(new PlayingCard(JACK, CLUBS)) // correct color wrong rank (should have been a SEVEN)

        then:
        thrown PowerAssertionError
    }

    def 'tableau pile should report that it is not acceptable to add a card, in conflict with the suit rules, to a laid out pile when the game starts'() {
        given:
        def pile = new TableauPile(3)
        assert pile.isEmpty()
        pile.addCard(new PlayingCard(ACE,   CLUBS).faceDown())  // Not a King ;-)
        pile.addCard(new PlayingCard(TWO,   SPADES).faceDown()) // same color
        pile.addCard(new PlayingCard(EIGHT, SPADES))            // same color
        assert pile.isLayoutComplete()
        pile.startGame()
        assert pile.isGameStarted()

        when:
        def result = pile.isAcceptable(new PlayingCard(SEVEN, CLUBS).attributes) // correct rank wrong color (should have been a RED)

        then:
        result == false
    }

    def 'it should not be possible to add a card, in conflict with the suit rules, to a laid out pile when the game starts'() {
        given:
        def pile = new TableauPile(3)
        assert pile.isEmpty()
        pile.addCard(new PlayingCard(THREE, DIAMONDS).faceDown()) // Not a King ;-)
        pile.addCard(new PlayingCard(THREE, HEARTS).faceDown())   // same color
        pile.addCard(new PlayingCard(FIVE,  DIAMONDS))            // same color
        assert pile.isLayoutComplete()
        pile.startGame()
        assert pile.isGameStarted()

        when:
        pile.addCard(new PlayingCard(FOUR, HEARTS)) // correct rank wrong color (should have been a BLACK)

        then:
        thrown PowerAssertionError
    }

    @Unroll
    def 'when you take the topmost card from a laid out tableau pile, the new top most card should flip, and hence be face up'() {
        given: 'a new empty tableau file'
        def pile = new TableauPile(size)
        assert pile.isEmpty()
        def deck = new DeckOfCards().shuffle()

        and: 'simulate layout phase by filling the pile from the shuffled deck of cards, until it is completely laid out - cards faced according to the rules'
        (size - 1).times {
            pile.addCard(deck.draw().faceDown())
        }
        pile.addCard(deck.draw())
        assert pile.isLayoutComplete()

        and: 'start the game'
        pile.startGame()
        assert pile.isGameStarted()


        when: 'you take the top most face up card'
        pile.takeCard()

        then: 'the new top most card should have flipped, to now be face up'
        pile.peek().faceUp

        where:
        size << (2..7)

    }

    private TableauPile buildEmptyStartedTableauPile(Integer initialSize) {
        // create empty tableau pile
        TableauPile tableauPile = new TableauPile(initialSize)
        assert tableauPile.empty

        // create new shuffled deck of cards
        DeckOfCards cards = new DeckOfCards().shuffle()

        // lay out the tableau pile with cards from the deck of cards - first the face down cards...
        (initialSize - 1).times {
            tableauPile.addCard(cards.draw().faceDown())
        }
        assert !tableauPile.layoutComplete

        // and then the face up card
        tableauPile.addCard(cards.draw())
        assert tableauPile.layoutComplete

        // start the game
        tableauPile.startGame()
        assert tableauPile.gameStarted

        // empty the pile
        initialSize.times {
            assert tableauPile.hasMoreCards()
            tableauPile.takeCard()
        }
        assert tableauPile.isEmpty()

        return tableauPile
    }


}

package info.martinussen.dit.developmentprocesses.solitaire.domain

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import spock.lang.Specification
import spock.lang.Unroll

import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color.BLACK
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color.RED
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank.*
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit.*

class PlayingCardSpec extends Specification{

    @Unroll
    def 'playingCard #rank of #suit should have expected (derived) attribute values'(){
        given:
        def card = new PlayingCard(rank, suit)

        expect:
        card.toShortString() == expectedShortString
        card.toString()      == expectedString
        card.value           == expectedValue
        card.rank            == expectedRank
        card.suit            == expectedSuit
        card.color           == expectedColor

        where:
        rank   | suit     | expectedShortString | expectedString      | expectedValue | expectedRank   | expectedSuit   | expectedColor
        ACE    | SPADES   | 'A♠'                | 'Ace of Spades'     |             1 | ACE            | SPADES         | BLACK
        FIVE   | CLUBS    | '5♣'                | 'Five of Clubs'     |             5 | FIVE           | CLUBS          | BLACK
        TEN    | HEARTS   | '10♡'               | 'Ten of Hearts'     |            10 | TEN            | HEARTS         | RED
        JACK   | DIAMONDS | 'J♢'                | 'Jack of Diamonds'  |            11 | JACK           | DIAMONDS       | RED
        QUEEN  | SPADES   | 'Q♠'                | 'Queen of Spades'   |            12 | QUEEN          | SPADES         | BLACK
        KING   | HEARTS   | 'K♡'                | 'King of Hearts'    |            13 | KING           | HEARTS         | RED
    }

    def 'playingCard Ace of Spades should be able to return its corresponding attribute container, containing expected values'(){
        given:
        def aceOfSpades = new PlayingCard(ACE, SPADES)

        when:
        def aceOfSpadesAttributes = aceOfSpades.getAttributes()

        then:
        aceOfSpadesAttributes.value == 1
        aceOfSpadesAttributes.rank  == ACE
        aceOfSpadesAttributes.suit  == SPADES
        aceOfSpadesAttributes.color == BLACK
    }

    def 'playingCard which has its face down should return a String representing that fact when asked'(){
        given:
        def sevenOfDiamonds = new PlayingCard(SEVEN, DIAMONDS)
        assert sevenOfDiamonds.isFaceUp()

        when: 'flip the card to face down'
        sevenOfDiamonds.faceDown()

        then: 'card reflects that it is face down'
        sevenOfDiamonds.isFaceDown()
        !sevenOfDiamonds.isFaceUp()
        sevenOfDiamonds.toString()      == 'Face Down'
        sevenOfDiamonds.toShortString() == '*'
        sevenOfDiamonds.rank            == null
        sevenOfDiamonds.suit            == null
        sevenOfDiamonds.value           == null
        sevenOfDiamonds.color           == null

        when: 'Flip the card to face up again'
        sevenOfDiamonds.faceUp()

        then: 'card returns all its attribute values'
        !sevenOfDiamonds.isFaceDown()
        sevenOfDiamonds.toString() == 'Seven of Diamonds'
        sevenOfDiamonds.toShortString() == '7♢'

        sevenOfDiamonds.rank == SEVEN
        sevenOfDiamonds.suit == DIAMONDS
        sevenOfDiamonds.color == Color.RED
        sevenOfDiamonds.value == 7
    }

    def 'playingCards with the same suit and rank are considered equal'(){
        given:
        def card1 = new PlayingCard(KING, DIAMONDS)
        def card2 = new PlayingCard(KING, DIAMONDS)

        expect:
        card1.equals(card2)
        card1 == card2
        card1.compareTo(card2) == 0
    }

    def 'playingCards with the same suit and different rank have expected natural order (and are hence not equal)'(){
        given:
        def card1 = new PlayingCard(JACK, SPADES)
        def card2 = new PlayingCard(KING, SPADES)

        expect:
        card1 < card2
        card2 > card1
        card1.compareTo(card2) < 0
        card2.compareTo(card1) > 0
        card1 != card2
        !card1.equals(card2)
    }

    def 'playingCards are sorted according to Rank (and hence value) as expected'() {
        given:
        def cards = [new PlayingCard(THREE, CLUBS),
                     new PlayingCard(TWO,   CLUBS),
                     new PlayingCard(EIGHT, CLUBS),
                     new PlayingCard(SEVEN, CLUBS),
                     new PlayingCard(KING,  CLUBS),
                     new PlayingCard(JACK,  CLUBS),
                     new PlayingCard(ACE,   CLUBS)]

        when:
        cards.sort()

        then:
        cards == [new PlayingCard(ACE,   CLUBS),
                  new PlayingCard(TWO,   CLUBS),
                  new PlayingCard(THREE, CLUBS),
                  new PlayingCard(SEVEN, CLUBS),
                  new PlayingCard(EIGHT, CLUBS),
                  new PlayingCard(JACK,  CLUBS),
                  new PlayingCard(KING,  CLUBS)]
    }

    def 'playingCards are sorted according to Suit as expected'() {
        given:
        def cards = [new PlayingCard(ACE, CLUBS),
                     new PlayingCard(ACE, SPADES),
                     new PlayingCard(ACE, HEARTS),
                     new PlayingCard(ACE, DIAMONDS)]

        when:
        cards.sort()

        then:
        cards == [new PlayingCard(ACE, SPADES),
                  new PlayingCard(ACE, HEARTS),
                  new PlayingCard(ACE, DIAMONDS),
                  new PlayingCard(ACE, CLUBS),]
    }


    def 'playingCards with the same suit (and hence color) are expected to report that fact when asked'(){
        given:
        def card1 = new PlayingCard(TEN, CLUBS)
        def card2 = new PlayingCard(THREE, CLUBS)

        expect:
        card1.hasSameSuit(card2)
        card2.hasSameSuit(card1)
        card1.hasSameColor(card2)
        card2.hasSameColor(card1)

    }

    def 'playingCards with the same color but different suit are expected to report that fact when asked'(){
        given:
        def card1 = new PlayingCard(TEN, CLUBS)
        def card2 = new PlayingCard(THREE, SPADES)

        expect:
        !card1.hasSameSuit(card2)
        !card2.hasSameSuit(card1)
        card1.hasSameColor(card2)
        card2.hasSameColor(card1)

    }

    def 'playingCards with different suit (and hence different color) (but same rank) are expected to report that fact when asked'(){
        given:
        def card1 = new PlayingCard(TEN, CLUBS)
        def card2 = new PlayingCard(TEN, DIAMONDS)

        expect:
        !card1.equals(card2)
        !card2.equals(card1)
        !card1.hasSameSuit(card2)
        !card2.hasSameSuit(card1)
        !card1.hasSameColor(card2)
        !card2.hasSameColor(card1)

    }

}

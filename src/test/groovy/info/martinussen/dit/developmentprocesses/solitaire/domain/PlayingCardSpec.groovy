package info.martinussen.dit.developmentprocesses.solitaire.domain

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCard
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import spock.lang.Specification

class PlayingCardSpec extends Specification{

    def 'playingCard Ace of Spades should be able to return its expected (derived) attribute values'(){
        given:
        def aceOfSpades = new PlayingCard(Rank.ACE, Suit.SPADES)

        expect:
        aceOfSpades.toShortString() == 'A♠'
        aceOfSpades.toString()      == 'Ace of Spades'
        aceOfSpades.value           == 1
        aceOfSpades.rank            == Rank.ACE
        aceOfSpades.suit            == Suit.SPADES
        aceOfSpades.color           == Color.BLACK
    }

    def 'playingCard Ace of Spades should be able to return its corresponding attribute container, containing expected values'(){
        given:
        def aceOfSpades = new PlayingCard(Rank.ACE, Suit.SPADES)

        when:
        def aceOfSpadesAttributes = aceOfSpades.getAttributes()

        then:
        aceOfSpadesAttributes.value           == 1
        aceOfSpadesAttributes.rank            == Rank.ACE
        aceOfSpadesAttributes.suit            == Suit.SPADES
        aceOfSpadesAttributes.color           == Color.BLACK
    }

    def 'playingCard Queen of Diamonds should be able to return its expected (derived) attribute values'(){
        given:
        def queenOfDiamonds = new PlayingCard(Rank.QUEEN, Suit.DIAMONDS)

        expect:
        queenOfDiamonds.toShortString() == 'Q♢'
        queenOfDiamonds.toString()      == 'Queen of Diamonds'
        queenOfDiamonds.value           == 12
        queenOfDiamonds.rank            == Rank.QUEEN
        queenOfDiamonds.suit            == Suit.DIAMONDS
        queenOfDiamonds.color           == Color.RED
    }

    def 'playingCard Five of Clubs should be able to return its expected (derived) attribute values'(){
        given:
        def fiveOfClubs = new PlayingCard(Rank.FIVE, Suit.CLUBS)

        expect:
        fiveOfClubs.toShortString() == '5♣'
        fiveOfClubs.toString()      == 'Five of Clubs'
        fiveOfClubs.value           == 5
        fiveOfClubs.rank            == Rank.FIVE
        fiveOfClubs.suit            == Suit.CLUBS
        fiveOfClubs.color           == Color.BLACK
    }

    def 'playingCard Ten of Hearts should be able to return its expected (derived) attribute values'(){
        given:
        def tenOfHearts = new PlayingCard(Rank.TEN, Suit.HEARTS)

        expect:
        tenOfHearts.toShortString() == '10♡'
        tenOfHearts.toString()      == 'Ten of Hearts'
        tenOfHearts.value           == 10
        tenOfHearts.rank            == Rank.TEN
        tenOfHearts.suit            == Suit.HEARTS
        tenOfHearts.color           == Color.RED
    }

    def 'playingCard which has its face down should return a String representing that fact when asked'(){
        given:
        def sevenOfDiamonds = new PlayingCard(Rank.SEVEN, Suit.DIAMONDS)

        when: 'flip the card to face down'
        sevenOfDiamonds.faceDown()

        then: 'card reflects that it is face down'
        sevenOfDiamonds.isFaceDown()
        !sevenOfDiamonds.isFaceUp()
        sevenOfDiamonds.toString() == 'Face Down'
        sevenOfDiamonds.toShortString() == '*'
        sevenOfDiamonds.rank == null
        sevenOfDiamonds.suit == null
        sevenOfDiamonds.value == null
        sevenOfDiamonds.color == null

        when: 'Flip the card to face up again'
        sevenOfDiamonds.faceUp()

        then: 'card returns all its attribute values'
        !sevenOfDiamonds.isFaceDown()
        sevenOfDiamonds.toString() == 'Seven of Diamonds'
        sevenOfDiamonds.toShortString() == '7♢'

        sevenOfDiamonds.rank == Rank.SEVEN
        sevenOfDiamonds.suit == Suit.DIAMONDS
        sevenOfDiamonds.color == Color.RED
        sevenOfDiamonds.value == 7
    }

    def 'playingCards with the same suit and rank are considered equal'(){
        given:
        def card1 = new PlayingCard(Rank.KING, Suit.DIAMONDS)
        def card2 = new PlayingCard(Rank.KING, Suit.DIAMONDS)

        expect:
        card1.equals(card2)
        card1 == card2
        card1.compareTo(card2) == 0
    }

    def 'playingCards with the same suit and different rank have expected natural order (and are hence not equal)'(){
        given:
        def card1 = new PlayingCard(Rank.JACK, Suit.SPADES)
        def card2 = new PlayingCard(Rank.KING, Suit.SPADES)

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
        def cards = [new PlayingCard(Rank.THREE, Suit.CLUBS),
                                          new PlayingCard(Rank.TWO,   Suit.CLUBS),
                                          new PlayingCard(Rank.EIGHT, Suit.CLUBS),
                                          new PlayingCard(Rank.SEVEN, Suit.CLUBS),
                                          new PlayingCard(Rank.KING,  Suit.CLUBS),
                                          new PlayingCard(Rank.JACK,  Suit.CLUBS),
                                          new PlayingCard(Rank.ACE,   Suit.CLUBS)]

        when:
        cards.sort()

        then:
        cards == [new PlayingCard(Rank.ACE,   Suit.CLUBS),
                  new PlayingCard(Rank.TWO,   Suit.CLUBS),
                  new PlayingCard(Rank.THREE, Suit.CLUBS),
                  new PlayingCard(Rank.SEVEN, Suit.CLUBS),
                  new PlayingCard(Rank.EIGHT, Suit.CLUBS),
                  new PlayingCard(Rank.JACK,  Suit.CLUBS),
                  new PlayingCard(Rank.KING,  Suit.CLUBS)]
    }

    def 'playingCards are sorted according to Suit as expected'() {
        given:
        def cards = [new PlayingCard(Rank.ACE, Suit.CLUBS),
                                          new PlayingCard(Rank.ACE, Suit.SPADES),
                                          new PlayingCard(Rank.ACE, Suit.HEARTS),
                                          new PlayingCard(Rank.ACE, Suit.DIAMONDS)]

        when:
        cards.sort()

        then:
        cards == [new PlayingCard(Rank.ACE, Suit.DIAMONDS),
                  new PlayingCard(Rank.ACE, Suit.CLUBS),
                  new PlayingCard(Rank.ACE, Suit.HEARTS),
                  new PlayingCard(Rank.ACE, Suit.SPADES)]
    }


    def 'playingCards with the same suit (and hence color) are expected to report that fact when asked'(){
        given:
        def card1 = new PlayingCard(Rank.TEN, Suit.CLUBS)
        def card2 = new PlayingCard(Rank.THREE, Suit.CLUBS)

        expect:
        card1.hasSameSuit(card2)
        card2.hasSameSuit(card1)
        card1.hasSameColor(card2)
        card2.hasSameColor(card1)

    }

    def 'playingCards with the same color but different suit are expected to report that fact when asked'(){
        given:
        def card1 = new PlayingCard(Rank.TEN, Suit.CLUBS)
        def card2 = new PlayingCard(Rank.THREE, Suit.SPADES)

        expect:
        !card1.hasSameSuit(card2)
        !card2.hasSameSuit(card1)
        card1.hasSameColor(card2)
        card2.hasSameColor(card1)

    }

    def 'playingCards with different suit (and hence different color) (but same rank) are expected to report that fact when asked'(){
        given:
        def card1 = new PlayingCard(Rank.TEN, Suit.CLUBS)
        def card2 = new PlayingCard(Rank.TEN, Suit.DIAMONDS)

        expect:
        !card1.equals(card2)
        !card2.equals(card1)
        !card1.hasSameSuit(card2)
        !card2.hasSameSuit(card1)
        !card1.hasSameColor(card2)
        !card2.hasSameColor(card1)

    }

}

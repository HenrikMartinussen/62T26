package info.martinussen.dit.developmentprocesses.solitaire.domain

import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit
import spock.lang.Specification
import spock.lang.Unroll

class SuitSpec extends Specification {

    @Unroll
    def 'Suit #suit have color #color'(){
        expect:
        suit.color == color

        where:
        suit          | color
        Suit.DIAMONDS | Color.RED
        Suit.CLUBS    | Color.BLACK
        Suit.HEARTS   | Color.RED
        Suit.SPADES   | Color.BLACK
    }


}

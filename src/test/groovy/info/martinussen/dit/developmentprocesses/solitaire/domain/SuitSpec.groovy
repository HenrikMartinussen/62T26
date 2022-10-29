package info.martinussen.dit.developmentprocesses.solitaire.domain


import spock.lang.Specification
import spock.lang.Unroll

import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color.BLACK
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Color.RED
import static info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit.*

class SuitSpec extends Specification {

    @Unroll
    def 'Suit #suit have color #color'(){
        expect:
        suit.color == color

        where:
        suit     | color
        SPADES   | BLACK
        HEARTS   | RED
        DIAMONDS | RED
        CLUBS    | BLACK
    }

    @Unroll
    def 'Suit #suit have ordinal value #expectedOrdinalValue'() {
        expect:
        suit.ordinal() == expectedOrdinalValue

        where:
        suit     | expectedOrdinalValue
        SPADES   | 0
        HEARTS   | 1
        DIAMONDS | 2
        CLUBS    | 3
    }


}

package info.martinussen.dit.developmentprocesses.solitaire.domain.rules

import groovy.transform.ToString
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.PlayingCardAttributes
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Rank

@ToString
class RankRule implements Rule {

    Rank rank

    RankRule(Rank rank) {
        this.rank = rank
    }

    @Override
    Boolean isValid(PlayingCardAttributes attributes) {
        return attributes?.rank == rank
    }

    @Override
    RankRule next() {
        if (rank < Rank.KING) {
            rank++
        }
        return this
    }

    @Override
    RankRule previous() {
        if (rank > Rank.ACE) {
            rank--
        }
        return this
    }
}

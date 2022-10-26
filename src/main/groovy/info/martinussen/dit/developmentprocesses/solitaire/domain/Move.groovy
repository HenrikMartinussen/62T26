package info.martinussen.dit.developmentprocesses.solitaire.domain

import info.martinussen.dit.developmentprocesses.solitaire.domain.pile.Pile

interface Move {

    void setTarget(Pile pile)
    void execute()
    Boolean isSuccess()

}
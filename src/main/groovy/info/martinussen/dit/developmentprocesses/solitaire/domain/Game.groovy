package info.martinussen.dit.developmentprocesses.solitaire.domain


import info.martinussen.dit.developmentprocesses.solitaire.domain.pile.FoundationPile
import info.martinussen.dit.developmentprocesses.solitaire.domain.pile.Pile
import info.martinussen.dit.developmentprocesses.solitaire.domain.pile.StockPile
import info.martinussen.dit.developmentprocesses.solitaire.domain.pile.TableauPile
import info.martinussen.dit.developmentprocesses.solitaire.domain.pile.WastePile
import info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard.Suit

class Game {

    def tableauPiles = []
    def spadesFoundationPile
    def clubsFoundationPile
    def heartsFoundationPile
    def diamondsFoundationPile
    def deck
    def wastePile
    def stockPile

    Game() {
        deck = new DeckOfCards()
        deck.shuffle()

        layout()

    }


    private void layout(){
        spadesFoundationPile   = new FoundationPile(Suit.SPADES)
        clubsFoundationPile    = new FoundationPile(Suit.CLUBS)
        heartsFoundationPile   = new FoundationPile(Suit.HEARTS)
        diamondsFoundationPile = new FoundationPile(Suit.DIAMONDS)

        (1..7).each{ capacity ->
            tableauPiles << new TableauPile(capacity)
        }

        wastePile = new WastePile()
        stockPile = new StockPile()

        deal()



    }

    private void deal(){
        (0..6).each{startColumn ->
            def firstInRow = true
            (startColumn..6).each{columnNumber ->
                if (firstInRow){
                    tableauPiles[columnNumber].addCard(deck.draw.faceUp())
                    firstInRow = false
                } else {
                    tableauPiles[columnNumber].addCard(deck.draw.faceDown())
                }
            }
        }
    }
}

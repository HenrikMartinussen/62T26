package info.martinussen.dit.developmentprocesses.solitaire.domain.playingcard

enum Rank {
    ACE  ('Ace',   'A',  1),
    TWO  ('Two',   '2',  2),
    THREE('Three', '3',  3),
    FOUR ('Four',  '4',  4),
    FIVE ('Five',  '5',  5),
    SIX  ('Six',   '6',  6),
    SEVEN('Seven', '7',  7),
    EIGHT('Eight', '8',  8),
    NINE ('Nine',  '9',  9),
    TEN  ('Ten',   '10',10),
    JACK ('Jack',  'J', 11),
    QUEEN('Queen', 'Q', 12),
    KING ('King',  'K', 13),


    public final String name
    public final Integer value
    public final String symbol

    private Rank(String name, String symbol, Integer value){
        this.name = name
        this.symbol = symbol
        this.value = value
    }

}

// https://en.wikipedia.org/wiki/Playing_card#French-suited_decks

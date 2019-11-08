import java.util.Random;

public class Deque{
    DoubleLinkedListOfCards deck;

    public Deque(){
        deck = new DoubleLinkedListOfCards();
    }
    /**
     * Retorna uma carta aleatoria de deck
     * @return A carta aleatoria
     */
    public Card compraCard(){
        Random r = new Random();
        int randomIndex = r.nextInt(deck.size());
        return deck.remove(randomIndex);
    }
    /**
     * Retorna uma string contendo a informacao de todas as cartas contidas em deck
     * @return s
     */
    public String printDeque(){
        //TODO
        String s;

        return s;
    }
}
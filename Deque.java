import java.util.Random;

public class Deque extends DoubleLinkedListOfCards{
    private DoubleLinkedListOfCards deck;

    public Deque(){
        super();
    }
    /**
     * Retorna uma carta aleatoria de deck
     * @return A carta aleatoria
     */
    public Card compraCard(){
        Random r = new Random();
        int randomIndex = r.nextInt(deck.size());
        return super.remove(randomIndex);
    }
    /**
     * Retorna uma string contendo a informacao de todas as cartas contidas em deck
     * @return s
     */
    public String printDeque(){
        //TODO
        String s ="";
        for (Card card : this) {
            s = s + card.toString()+"\n";
        }
        return s;
    }
}
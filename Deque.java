import java.util.Random;

public class Deque extends DoubleLinkedListOfCards{
    public Deque(){
        super();
    }
    /**
     * Retorna uma carta aleatoria de deck
     * @return A carta aleatoria
     */
    public Card compraCard(){
        if(!super.isEmpty()){
        Random r = new Random();
        int randomIndex = r.nextInt(super.size());
        return super.remove(randomIndex);
        }
        return null;
    }
    /**
     * Retorna uma string contendo a informacao de todas as cartas contidas em deck
     * @return s
     */
    public String printDeque(){
        String s ="";
        for (Card card : this) {
            s = s + card.toString()+"\n";
        }
        return s;
    }
}
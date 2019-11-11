public class Jogo{
    private Deque deck;
    private static int IDcounter = 1;
    private int ID;
    private DoubleLinkedListOfPlayers jogadores;
    private boolean ordemNormal;

    public Jogo(){
        deck = new Deque();
        jogadores = new DoubleLinkedListOfPlayers();
        ordemNormal = true;
        ID = IDcounter;
        IDcounter++;
    }

    /**
     * @return o deck
     */
    public Deque getDeck() {
        return deck;
    }

    /**
     * @return A ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @return Lista jogadores
     */
    public DoubleLinkedListOfPlayers getJogadores() {
        return jogadores;
    }
    /**
     * @return true se e ordem normal de jogo e false se a ordem estiver invertida
     */
    public boolean getOrdem(){
        return ordemNormal;
    }
}
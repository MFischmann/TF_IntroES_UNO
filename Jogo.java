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
}
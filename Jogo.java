public class Jogo{
    private Deque deck;
    private static int IDcounter = 1;
    private int ID;
    private DoubleLinkedListOfPlayers jogadores;
    private boolean ordemNormal;
    private String currentCor;
    private String currentValor;
    private Card lastCard;
    public Jogo(){
        deck = new Deque();
        jogadores = new DoubleLinkedListOfPlayers();
        ordemNormal = true;
        ID = IDcounter;
        IDcounter++;
        currentCor = "N/A";
        currentValor = "N/A";

        Card c = deck.compraCard(); //usa carta aleatoria para inicializar a partida
        
        while(c.getValor().equals("+4")){ //enquanto a carta do topo for +4 coringa reembaralha e compra outra carta
            deck.add(c);
            c = deck.compraCard();
        }  

        usaCarta(c);
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
    /**
     * Aplica efeito de ordem inversa
     */
    public void inverteOrdem(){
        ordemNormal = !ordemNormal;
    }

    public void compraCarta(){
        Jogador atual = jogadores.getCurrentPlayer();
        Card topo = deck.compraCard();
        atual.getHand().add(topo);
    }
    /**
     * Tenta jogar a carta e aplica efeitos
     * @param carta a ser jogada
     * @return true se foi possivel jogar a carta e false se nao foi possivel
     */
    public boolean usaCarta(Card carta){
        if(ehValida(carta)){
            currentCor = carta.getCor();
            currentValor = carta.getValor();
            jogadores.setNextPlayer(ordemNormal);
            if(carta.getCor().equals("Multi")){
                setCurrentCor();
                currentValor = "N/A";
            }

            if(carta.getValor().equals("Inverte")){
                inverteOrdem();
            }

            if(carta.getValor().equals("Pula")){
                jogadores.setNextPlayer(ordemNormal);
            }

            if(carta.getValor().equals("+2")){
                for(int i = 0; i < 2; i++){
                    Card aux = deck.compraCard();
                    jogadores.getCurrentPlayer().getHand().add(aux);
                }

                jogadores.setNextPlayer(ordemNormal);
            }

            if(carta.getValor().equals("+4")){
                for(int i = 0; i < 4; i++){
                    Card aux = deck.compraCard();
                    jogadores.getCurrentPlayer().getHand().add(aux);
                }

                jogadores.setNextPlayer(ordemNormal);
            }
            return true;
        }

        return false;
    }
    /**
     * 
     * @param carta carta candidata a ser jogada
     * @return true se a carta pode ser jogada, false se nao pode
     */
    public boolean ehValida(Card carta){
        if(currentCor.equals("N/A") || carta.getCor().equals("Multi")){ //se e inicio do jogo ou se a carta e coringa
            return true;
        }

        if(carta.getCor().equals(currentCor)||carta.getValor().equals(currentValor)){
            return true;
        }

        return false;
    }

    public void setCurrentCor(){
        //TODO
    }
}
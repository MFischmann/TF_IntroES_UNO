import java.util.Scanner;

public class Jogo{
    private Deque deck;
    private static int IDcounter = 1;
    private int ID;
    private DoubleLinkedListOfPlayers jogadores;
    private boolean ordemNormal;
    private String currentCor;
    private String currentValor;
    private Card lastCard;
    private final int initialHandSize = 7;
    public Jogo(){
        deck = new Deque();
        jogadores = new DoubleLinkedListOfPlayers();
        ordemNormal = true;
        ID = IDcounter;
        IDcounter++;
        currentCor = "N/A";
        currentValor = "N/A";
    }

    /**
     * @return o deck
     */
    public Deque getDeck() {
        return deck;
    }

    /**
     * @return the lastCard
     */
    public Card getLastCard() {
        return lastCard;
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

    /**
     * Inicializa um jogo do zero
     */
    public void iniciaJogo(){

        Card c = deck.compraCard(); //usa carta aleatoria para inicializar a partida
        
        while(c.getValor().equals("+4")){ //enquanto a carta do topo for +4 coringa reembaralha e compra outra carta
            deck.add(c);
            c = deck.compraCard();
        }  

        usaCarta(c);
        if(c.getCor().equals("Multi")){ //se for coringa tem que resetar p/ jogador inicial
            inverteOrdem();
            jogadores.setNextPlayer(ordemNormal); //volta ao jogador original
            inverteOrdem(); //seta novamente para ordem normal
        }

        Card compra;
        for(int i = 0; i < jogadores.size(); i++){ //percorre todos jogadores
            for(int j = 0; j < initialHandSize; j++){ //compra cartas suficientes para compor mao inicial
                compra = deck.compraCard(); 
                jogadores.getCurrentPlayer().getHand().add(compra);
            }
            jogadores.setNextPlayer(ordemNormal); //pega prox jogador
        }

        jogadores.setNextPlayer(ordemNormal); //volta ao jogador inicial
    }

    public Card compraCarta(){
        Jogador atual = jogadores.getCurrentPlayer();
        Card topo = deck.compraCard();
        atual.getHand().add(topo);
        return topo;
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
            lastCard = carta;
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

            jogadores.setNextPlayer(ordemNormal); //Acoes antes desta linha precisam ocorrer antes de ir para prox jogador

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

    public boolean tentaCarta(int index){
        Card carta = getJogadores().getCurrentPlayer().getHand().getCard(index);
        if(ehValida(carta)){
            getJogadores().getCurrentPlayer().getHand().remove(index);
        }
        return usaCarta(carta);
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
        Scanner sCor = new Scanner(System.in);
        boolean confirm = false;
        do{
            System.out.println("Escolha (digite numero) a cor atual:\n1: Amarelo \n2: Azul \n3: Verde \n4: Vermelho");
            int novaCor = sCor.nextInt();
            switch(novaCor){
                case 1:
                    currentCor = "Amerelo";
                    confirm = true;
                    break;
                case 2:
                    currentCor = "Azul";
                    confirm = true;
                    break;
                case 3:
                    currentCor = "Verde";
                    confirm = true;
                    break;
                case 4:
                    currentCor = "Vermelho";
                    confirm = true;
                    break;
                default:
                    System.out.println("Opcao inexistente.");
            }
        }
        while(!confirm);
        System.out.println("Cor escolhida: "+ currentCor);
        sCor.close();
    }

    public void addJogador(String nome){
        Jogador temp = new Jogador(nome);
        jogadores.add(temp);
    }

    public void penaliza(){
        //TODO 
    }

    public void adicionaScore(){
        //TODO
    }
    public void printScore(){
        //TODO
    }
    /**
     * Verifica se o jogador atual ganhou
     * @return true se a mao do jogador estiver vazia, false se nao estiver
     */
    public boolean verificaVitoria(){
        return jogadores.getCurrentPlayer().getHand().isEmpty();
    }
}
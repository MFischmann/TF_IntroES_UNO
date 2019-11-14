import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Jogo{
    private Deque deck;
    private static int IDcounter = 1;
    private int ID;
    private DoubleLinkedListOfPlayers jogadores;
    private boolean ordemNormal;
    private String currentCor;
    private String currentValor;
    private Card lastCard;
    private final int initialHandSize = 4;
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
        carrega("DequeDefault");
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

        for(int i = 0; i < jogadores.size(); i++){ //percorre todos jogadores
            for(int j = 0; j < initialHandSize; j++){ //compra cartas suficientes para compor mao inicial
                    compraCarta();
            }
            jogadores.setNextPlayer(ordemNormal); //pega prox jogador
        }
        jogadores.setCurrentInicial();
    }
    /**
     * Salva o jogo atual
     */
    public String salva(){
        String fileName = "JogoUno"+ID+".txt";
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+"\\"+fileName;
        Path path = Paths.get(nameComplete);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))){
            writer.println("Deque");
            for(Card c:deck){
                String linha = c.getValor()+","+c.getCor();
                writer.println(linha);
            }
            writer.println(";");
            Jogador currentP;
            DoubleLinkedListOfCards currentHand;
            for(int i = 0; i < jogadores.size(); i++){
                currentP = jogadores.getCurrentPlayer();
                writer.println(currentP.getNome());
                currentHand = currentP.getHand();
                for (Card c : currentHand) {
                    String linha = c.getValor()+","+c.getCor();
                    writer.println(linha);
                }
                writer.println(";");
                jogadores.setNextPlayer(ordemNormal);
            }
            writer.println("LastCard");
            String linha = lastCard.getValor()+","+lastCard.getCor();
            writer.println(linha);
            if(lastCard.getCor().equals("Multi")){
                writer.println(currentCor);
            }
        }catch (IOException x){
          System.err.format("Erro de E/S: %s%n", x);
      }        
      finally{
          return fileName;
      }
    }

    /**
     * Realiza leitura de arquivo para carregar deque, jogadores e mao dos jogadores.
     * @param string nome do arquivo a ser lido
     */
    public boolean carrega(String fileName) {
        System.out.println("Inicializando leitura do arquivo "+fileName);
        String line;
        String currDir = Paths.get("").toAbsolutePath().toString();
        // Monta o nome do arquivo
        String nameComplete = currDir+"\\"+fileName+".txt";
        // Cria acesso ao "diretorio" da mídia (disco)
        Path path = Paths.get(nameComplete);
        Jogador newPlayer;
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))){
            sc.useDelimiter("[\n]"); // separador:
            while (sc.hasNext()) {
                line = sc.next().trim();
                if(line.equals("Deque")){//adiciona ao deque
                    System.out.println("Carregando deque.");
                    line = sc.next().trim();
                    while(sc.hasNext()&&!line.equals(";")){
                        String[] componente = line.split(",");
                        deck.add(new Card(componente[0], componente[1]));
                        line = sc.next().trim();
                    }
                }
                else if(line.equals("LastCard")){
                    System.out.println("Carregando ultima carta jogada.");
                    line = sc.next().trim();
                    String[] componente = line.split(",");
                    lastCard = new Card(componente[0], componente[1]);
                    currentCor = lastCard.getCor();
                    currentValor = lastCard.getValor();
                    if(currentCor.equals("Multi")){
                        line = sc.next().trim();
                        currentCor = line;
                    }
                }
                else{//Adiciona jogador e mao
                    //TODO
                    System.out.println("Carregando jogador "+line);
                    jogadores.add(new Jogador(line));
                    if(jogadores.size() == 1){
                        jogadores.setCurrentInicial();
                    }
                    else{
                        jogadores.setNextPlayer(ordemNormal);
                    }
                    newPlayer = jogadores.getCurrentPlayer();
                    line = sc.next().trim();
                    while(sc.hasNext()&&!line.equals(";")){
                        String[] componente = line.split(",");
                        newPlayer.getHand().add(new Card(componente[0], componente[1]));
                        line = sc.next().trim();
                    }

                }
            }
            jogadores.setCurrentInicial();
            return true;
        }
        catch (IOException x){
            System.err.format("Erro de E/S: %s%n", x);
            return false;
        }
    }

    /**
     * Tenta comprar carta para jogador atual
     * 
     * @return true se pode comprar, false se deck vazio
     */
    public boolean compraCarta(){
        if(deck.isEmpty()){
            System.out.println("Deck vazio.");
            return false;
        }
        Jogador atual = jogadores.getCurrentPlayer();
        Card topo = deck.compraCard();
        atual.getHand().add(topo);
        return true;
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
                if(jogadores.size()==2){
                    pulaTurno();
                }
                else{
                    inverteOrdem();
                }
            }

            if(carta.getValor().equals("Pula")){
                pulaTurno();
            }

            jogadores.setNextPlayer(ordemNormal); //Acoes antes desta linha precisam ocorrer antes de ir para prox jogador

            if(carta.getValor().equals("+2")){
                for(int i = 0; i < 2; i++){
                    compraCarta();
                }

                jogadores.setNextPlayer(ordemNormal);
            }

            if(carta.getValor().equals("+4")){
                for(int i = 0; i < 4; i++){
                    compraCarta();
                }

                jogadores.setNextPlayer(ordemNormal);
            }
            return true;
        }

        return false;
    }
    public void pulaTurno(){
        jogadores.setNextPlayer(ordemNormal);
    }

    public boolean tentaCarta(int index){
        Card carta = getJogadores().getCurrentPlayer().getHand().getCard(index-1);
        if(ehValida(carta)){
            getJogadores().getCurrentPlayer().getHand().remove(index-1);
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
    /**
     * @return the currentCor
     */
    public String getCurrentCor() {
        return currentCor;
    }
    public void setCurrentCor(){
        Scanner sCor = new Scanner(System.in);
        boolean confirm = false;
        do{
            System.out.println("Escolha (digite numero) a cor atual:\n1: Amarelo \n2: Azul \n3: Verde \n4: Vermelho");
            int novaCor = sCor.nextInt();
            sCor.nextLine();
            switch(novaCor){
                case 1:
                    currentCor = "Amarelo";
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
        //sCor.close();
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
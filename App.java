import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
  
    private Scanner scan;
    private Jogo uno;
    public App(){
      scan = new Scanner(System.in);
    }
    public static void main(String[] args) {
      App app = new App();
      app.menuInicial();
      /*
      Jogo teste = new Jogo();
      teste.carrega("Default");
      System.out.println(teste.getDeck().size());
      System.out.println(teste.getDeck().getCard(1));
      System.out.println(teste.getDeck().getCard(1).getCor().equals("Azul"));
      System.out.println(teste.getDeck().getCard(1).getCor().length());
      System.out.println(teste.getDeck().getCard(1).getValor().equals("0"));
      */
    }
    private void menuInicial(){
      int opcao;
      boolean finaliza = false;
      do{
        System.out.println("Bem vindo ao jogo de Uno. \nDigite a opcao desejada:");
        System.out.println("1: Inicializar novo jogo.\n2: Carregar um jogo salvo.\n3: Finalizar programa.");
        opcao = numberReader();
        switch(opcao){
          case 1:
            inicializaJogo();
            break;
          case 2:
            carregaJogo();
            break;
          case 3:
            System.out.println("Finalizando programa.");
            finaliza = true;
            break;
          default:
            System.out.println("Opcao nao encontrada.");    
        }
      }
      while(!finaliza);
      scan.close();
    }
    
  private void inicializaJogo(){
    uno = new Jogo();
    int qtdJogadores;
    do{
      System.out.println("\nDigite numero de jogadores (minimo 2 e maximo 8): ");
      qtdJogadores = numberReader();
    }
    while(qtdJogadores < 2 || qtdJogadores > 8);

    String nomes;
    for(int i = 1; i <= qtdJogadores; i++){ //inicializa jogadores
      System.out.println("\nDigite nome do jogador "+ i+": ");
      nomes = scan.nextLine();
      uno.addJogador(nomes);
    }
      System.out.println("\n \nJogo sera inicializado agora.");
      uno.iniciaJogo();
      menuJogo();
  }
  private void printLastCard(){
    Card last = uno.getLastCard();
    System.out.println("Ultima carta jogada: "+last);
    if(last.getCor().equals("Multi")){
      System.out.println("Cor escolhida: "+uno.getCurrentCor());
    } 
  }

  private int numberReader(){
    int aux;
    try {
      aux = scan.nextInt();
      scan.nextLine();
      return aux;
    } catch (InputMismatchException e) {
      scan.next();
      System.out.println("\nFavor digitar numero.\n");
      aux = -1;
      return aux;
    }
  }
  private void menuJogo(){
    boolean podePular;
    boolean saiJogo = false;
    while(true){ //loop principal do jogo
      boolean acaocompleta = false;
      podePular = false;
      int acao;
      System.out.println("\n \n \nTurno do jogador "+uno.getJogadores().getCurrentPlayer().getNome());
     
      printCurrentHand();
      do{
        System.out.println("\nEscolha uma opcao: \n1: Jogar uma carta.\n2: Comprar carta. \n3: Pular turno. \n4: Salvar Jogo.\n5: Encerrar jogo sem salvar.");
        printLastCard();
        acao = numberReader();
        switch(acao){
            case 1:
              System.out.println("\nQual carta (digite numero) deve ser jogada?");
              printCurrentHand();
              int index = numberReader();
                try {
                  acaocompleta = uno.tentaCarta(index);
                  if(acaocompleta){
                    System.out.println("Carta jogada com sucesso.");
                  }
                  else{
                    System.out.println("Carta nao valida.");
                    //pode chamar penalidade
                  }
                } catch (IndexOutOfBoundsException e) {
                  System.out.println("Numero invalido de carta.");
                }

              //}
              break;
              case 2:
                if(!podePular){
                  if(uno.compraCarta()){
                    int lastIndex = uno.getJogadores().getCurrentPlayer().getHand().size()-1;
                    Card compra = uno.getJogadores().getCurrentPlayer().getHand().getCard(lastIndex);
                    System.out.println("Carta comprada: "+compra);
                  }
                  podePular = true;
                }
                else{
                  System.out.println("Carta ja foi comprada.");
                }
              break;
          
            case 3:
              if(podePular){
                uno.pulaTurno();
                acaocompleta = true;
                System.out.println("Pulando turno.");
              }
              else{
                System.out.println("Precisa comprar uma carta antes de pular turno.");
              }
              break;
            
            case 4:
              if(!salvaJogo()){
                System.out.println("Saindo do jogo.");
                saiJogo = true;
                //scan.close();
                //System.exit(0);
              }
              acaocompleta = true;
              break;
            case 5:
              System.out.println("Saindo do jogo.");
              saiJogo = true;
              //scan.close();
              //System.exit(0);
              acaocompleta = true;
              break;
            default:
              System.out.println("Acao invalida.");
          }
        }
      while(!acaocompleta);
      if(uno.verificaVitoria()){ //se alguem obteve vitoria, encerra round
        //pode contar score aqui
        Jogador w = uno.getWinner();
        System.out.println("Vitoria do jogador "+w.getNome());
        System.out.println("Score atual do jogador: "+w.getScore());
        System.out.println("Jogar novamente? (S/N)");
        if(simNao()){
          uno.reinicia();
        }
        else{
          break;
        }
        
      }
      if(saiJogo){
        break;
      }
      //pesquisar metodo para limpar console
    }
  }
  private void printCurrentHand(){
    System.out.println("Cartas na sua mao:");
    int contAux = 0;
    DoubleLinkedListOfCards aux = uno.getJogadores().getCurrentPlayer().getHand();
    for (Card card : aux) {
      contAux++;
      System.out.println("Carta N"+contAux+": "+card);
    }
  }
  private void carregaJogo(){
    uno = new Jogo();
    System.out.println("Digite o nome do jogo a ser carregado.");
    String loadName = scan.nextLine().trim();
    boolean loaded = uno.carrega(loadName);
    if(loaded){
      menuJogo();
    }
    else{
      System.out.println("Jogo nao pode ser carregado.");
    }
    
  }
  private boolean simNao(){
    while(true){
      String ans = scan.nextLine().trim();
        if(ans.equalsIgnoreCase("S")){
          return true;
        }
        else if(ans.equalsIgnoreCase("N")){
          return false;
        }
      }
  }

  private boolean salvaJogo(){
    System.out.println("Salvando jogo. Digite nome do arquivo.");
    String fileName = scan.nextLine().trim();
    fileName = uno.salva(fileName);
    System.out.println("Jogo salvo no arquivo "+fileName);
      System.out.println("Deseja continuar jogando? (S/N)");
      return simNao();
    
  }
}
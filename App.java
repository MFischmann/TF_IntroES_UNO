import java.util.Scanner;

public class App {
  
    private Scanner scan;
    private Jogo uno;
    public App(){
      scan = new Scanner(System.in);
      uno = new Jogo();
    }
    public static void main(String[] args) {
      App app = new App();
      app.menuInicial();

    }
    private void menuInicial(){
      System.out.println("Bem vindo ao jogo de Uno. \nDigite a opcao desejada:");
      System.out.println("1: Inicializar novo jogo.\n2: Carregar um jogo salvo. \n 3: Finalizar programa.");
      int opcao;
      boolean finaliza = false;
      do{
        opcao = scan.nextInt();
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
    int qtdJogadores;
    do{
      System.out.println("Digite numero de jogadores (minimo 2 e maximo 8): ");
      qtdJogadores = scan.nextInt();
      scan.nextLine();
    }
    while(qtdJogadores < 2 || qtdJogadores > 8);

    String nomes;
    for(int i = 1; i <= qtdJogadores; i++){ //inicializa jogadores
      System.out.println("Digite nome do jogador "+ i+": ");
      nomes = scan.nextLine();
      uno.addJogador(nomes);
    }
      System.out.println("Jogo sera inicializado agora.");
      uno.iniciaJogo();
      menuJogo();
  }

  private void menuJogo(){
    //TODO
    while(true){ //loop principal do jogo
      System.out.println("Turno do jogador "+uno.getJogadores().getCurrentPlayer().getNome());
      System.out.println("Ultima carta jogada: "+uno.getLastCard());
      printCurrentHand();
      boolean acaocompleta = false;
      int acao;
      do{
        System.out.println("Escolha uma opcao: \n1: Jogar uma carta.\n2: Comprar carta.\n3: Salvar Jogo.\n4: Encerrar jogo sem salvar.");
        acao = scan.nextInt();
        switch(acao){
          case 1:
            System.out.println("Qual carta (digite numero) deve ser jogada?");
            printCurrentHand();
            int index = scan.nextInt();
            acaocompleta = uno.tentaCarta(index);
            if(acaocompleta){
              System.out.println("Carta jogada com sucesso.");
            }
            else{
              System.out.println("Carta nao valida.");
              //pode chamar penalidade
            }
            break;
            case 2:
              if(uno.compraCarta()){
                int lastIndex = uno.getJogadores().getCurrentPlayer().getHand().size()-1;
                Card compra = uno.getJogadores().getCurrentPlayer().getHand().getCard(lastIndex);
                System.out.println("Carta comprada: "+compra);
                /*
                if(uno.ehValida(compra)){
                  acaocompleta = uno.tentaCarta(uno.getJogadores().getCurrentPlayer().getHand().size()-1);
                  //utiliza ultima carta
                  if(acaocompleta){
                    System.out.println("Carta jogada com sucesso.");
                  }
                  else{
                    System.out.println("Carta nao valida.");
                    //pode chamar penalidade
                  }
              }
              */
            }
            break;

          case 3:
            if(!salvaJogo()){
              System.out.println("Finalizando programa.");
              scan.close();
              System.exit(0);
            }
            break;
          case 4:
            System.out.println("Finalizando programa.");
            scan.close();
            System.exit(0);
            break;
          default:
            System.out.println("Acao invalida.");
        }
      }
      while(!acaocompleta);
      if(uno.verificaVitoria()){ //se alguem obtem vitoria, encerra round
        //pode contar score aqui
        break;
      }
      //pesquisar metodo para limpar console
    }
  }
  private void printCurrentHand(){
    System.out.println("Cartas na sua mão:");
    int contAux = 0;
    DoubleLinkedListOfCards aux = uno.getJogadores().getCurrentPlayer().getHand();
    for (Card card : aux) {
      contAux++;
      System.out.println("Carta N"+contAux+": "+card);
    }
  }
  private void carregaJogo(){
    //TODO
  }

  private boolean salvaJogo(){
    //TODO
    return true; //temp
  }
}
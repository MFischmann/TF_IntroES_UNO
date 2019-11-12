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
      System.out.println("Bem vindo ao jogo de Uno. \nDigite a opção desejada:");
      System.out.println("1: Inicializar novo jogo. \n2: Carregar um jogo salvo. \n 3: Finalizar programa.");
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
    //TODO
    boolean check = false;
    int qtdJogadores;
    do{
      System.out.println("Digite numero de jogadores (minimo 2 e maximo 8): ");
      qtdJogadores = scan.nextInt();
    }
    while(qtdJogadores < 2 || qtdJogadores > 8);

    String nomes;
    for(int i = 1; i <= qtdJogadores; i++){ //inicializa jogadores
      System.out.println("Digite nome do jogador "+ i+": ");
      nomes = scan.nextLine();
      uno.addJogador(nomes);
      System.out.println("Jogo sera inicializado agora.");
      uno.iniciaJogo();
      menuJogo();
    }
  }

  private void menuJogo(){
    //TODO
  }

  private void carregaJogo(){
    //TODO
  }

  private void salvaJogo(){
    //TODO
  }
}
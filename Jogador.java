public class Jogador{
    private String nome;
    private DoubleLinkedListOfCards hand;
    private int score;

    public Jogador(String nome){
        this.nome = nome;
        hand = new DoubleLinkedListOfCards();
        this.score = 0;
    }

    public Jogador(String nome, int score){
        this.nome = nome;
        hand = new DoubleLinkedListOfCards();
        this.score = score;
    }

    public String getNome(){
        return nome;
    }

    public DoubleLinkedListOfCards getHand(){
        return hand;
    }

    public int getScore(){
        return score;
    }

    public void addWin(){
        score++;
    }

    public void setScore(int score){
        this.score = score;
    }
}
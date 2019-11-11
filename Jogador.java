public class Jogador{
    private String nome;
    private DoubleLinkedListOfCards hand;

    public Jogador(String nome){
        this.nome = nome;
        hand = new DoubleLinkedListOfCards();
    }

    public String getNome(){
        return nome;
    }

    public DoubleLinkedListOfCards getHand(){
        return hand;
    }
}
public class App {
    public static void main(String[] args) {
        Card c1 = new Card("1", "Verde");
        Card c2 = new Card("3", "Azul");
        Card c3 = new Card("Inverte", "Vermelho");
        DoubleLinkedListOfCards deque = new DoubleLinkedListOfCards();
        deque.add(c1);
        deque.add(new Card("+2", "Amarelo"));
        deque.add(c3);
        //System.out.println(c1);
        //System.out.println(deque.getCard(1));
        //Card c4 = deque.remove(2);
        //System.out.println(c4);
        //System.out.println(c2.getCor());
        //System.out.println(c3.getValor());
        //System.out.println(deque.size());

        Deque d = new Deque();
        d.add(c1);
        d.add(c2);
        d.add(c3);
        d.add(new Card("+2", "Amarelo"));
        System.out.println(d.size());
        System.out.println(d.printDeque());

        System.out.println(d.iterator().next());
        for (Card card : d) {
          System.out.println(card.getCor());  
        }
    }
}
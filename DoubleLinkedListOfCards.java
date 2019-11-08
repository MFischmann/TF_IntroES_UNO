import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedListOfCards implements Iterable<Card>{
    private Node header;
    private Node trailer;
    private int count;

    private class Node {
        public Card element;
        public Node next;
        public Node prev;
        public Node(Card e) {
            element = e;
            next = null;
            prev = null;
        }
    }
    public class DoubleListIterator implements Iterator<Card> {
        // instance variable
        private Node current = header.next;
        
        public boolean hasNext() {
          return current.next != null;
        }
        public Card next() {
            if (!hasNext()) throw new NoSuchElementException();
            Card atual = current.element;
            current = current.next;  // if next is null, hasNext will return false.
            return atual;
    
        }
        public void remove() { throw new UnsupportedOperationException(); }
    }

    public DoubleLinkedListOfCards(){
        header = new Node(null);
        trailer = new Node(null);
        header.next = trailer;
        trailer.prev = header;
        count = 0;
    }
    /**
     * Adiciona Card element no fim da lista
     * @param element
     */
    public void add(Card element){
	// Cria o nodo
    Node n = new Node(element);
		
    // Atualiza as referencias do nodo criado para liga-lo na lista
    Node last = trailer.prev;
    n.prev = last;
    n.next = trailer;
    
    // Atualiza as referencias para incluir o nodo criado
    last.next = n;
    trailer.prev = n;
    
    // Atualiza o contador
    count++;       
    }
    /**
     * Metodo otimizado para obter referencia a um nodo
     * @param index posicao do nodo
     * @return o nodo desejado
     */
    private Node getRefNode(int index) {
        Node aux = null;
        if (index <= count / 2) {
            aux = header.next;
            for (int i = 0; i < index; i++) {
                aux = aux.next;
            }
        } else {
            aux = trailer.prev;
            for (int i = count - 1; i > index; i--) {
                aux = aux.prev;
            }
        }
        return aux;       
    }
    /**
     * Remove Card da posicao index e a retorna
     * @param index posicao da Card a ser removida
     * @return a Card removida
     * @throws IndexOutOfBoundsException se posicao inexiste
     */
    public Card remove(int index){
        if(index < 0 || index >= count){
            throw new IndexOutOfBoundsException("Valor invalido de index.");
        }

        Node n = getRefNode(index);

        n.prev.next = n.next;
        n.next.prev = n.prev; 
        count--;
        return n.element;
    }

        /**
     * Retorna Card da posicao index
     * @param index posicao da Card a ser retornada
     * @return a Card removida
     * @throws IndexOutOfBoundsException se posicao inexiste
     */
    public Card getCard(int index){
        if(index < 0 || index >= count){
            throw new IndexOutOfBoundsException("Valor invalido de index.");
        }

        Node n = getRefNode(index);

        return n.element;
    }
    /**
     * Retorna tamanho da lista
     * @return tamanho da lista
     */
    public int size(){
        return count;
    }
    /**
     * Informa se lista esta vazia
     * @return true se vazio e false se ha elementos
     */
    public boolean isEmpty(){
        return count==0;
    }
    /**
     * Limpa a lista
     */
    public void clear(){
        header.next = trailer;
        trailer.prev = header;
        count = 0;
    }

    @Override
    public DoubleListIterator iterator() {
        return new DoubleListIterator();
    }

}

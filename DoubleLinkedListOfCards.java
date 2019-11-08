public class DoubleLinkedListOfCards{
    private Node header;
    private Node trailer;
    int count;

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
        //TODO
    }
    /**
     * Remove Card da posicao index e a retorna
     * @param index posicao da Card a ser removida
     * @return a Card removida
     * @throws IndexOutOfBoundsException se posicao inexiste
     */
    public Card remove(int index){
        //TODO
        if(index < 0 || index > count){
            throw new IndexOutOfBoundsException("Valor invalido de index.");
        }
    }

        /**
     * Retorna Card da posicao index
     * @param index posicao da Card a ser retornada
     * @return a Card removida
     * @throws IndexOutOfBoundsException se posicao inexiste
     */
    public Card getCard(int index){
        //TODO
        if(index < 0 || index > count){
            throw new IndexOutOfBoundsException("Valor invalido de index.");
        }
    }
    /**
     * Retorna tamanho da lista
     * @return tamanho da lista
     */
    public int size(){
        return count;
    }
}
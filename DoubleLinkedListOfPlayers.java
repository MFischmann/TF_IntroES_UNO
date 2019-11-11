public class DoubleLinkedListOfPlayers{
    private Node header;
    private Node trailer;
    private int count;
    private Node current;
    private class Node {
        public Jogador element;
        public Node next;
        public Node prev;
        public Node(Jogador e) {
            element = e;
            next = null;
            prev = null;
        }
    }

    public DoubleLinkedListOfPlayers(){
        header = new Node(null);
        trailer = new Node(null);
        current = header;
        header.next = trailer;
        trailer.prev = header;
        count = 0;
    }
    /**
     * Adiciona Jogador element no fim da lista
     * @param element
     */
    public void add(Jogador element){
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
     * Remove Jogador da posicao index e a retorna
     * @param index posicao da Jogador a ser removida
     * @return a Jogador removida
     * @throws IndexOutOfBoundsException se posicao inexiste
     */
    public Jogador remove(int index){
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
     * Retorna Jogador da posicao index
     * @param index posicao da Jogador a ser retornada
     * @return a Jogador removida
     * @throws IndexOutOfBoundsException se posicao inexiste
     */
    public Jogador getJogador(int index){
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
        current = header;
        count = 0;
}
    /**
     * Seta current para primeiro jogador
     */
    public void setCurrentInicial(){
        if(count != 0){
            current = header.next;
        }

        else{
            throw new IndexOutOfBoundsException("Lista jogadores vazia. \n Nao foi possivel iniciar ordem de turno.");
        }
    }

    public Jogador getCurrentPlayer(){
        return current.element;
    }

    public void setNextPlayer(boolean ordemNormal){
        if(ordemNormal){
            if(current.next == trailer){ //se atualmente for ultimo jogador
                current = header.next;
            }
            else{
                current = current.next;
            }
        }
        else{
            if(current.prev == header){ //se atualmente for primeiro jogador
                current = trailer.prev;
            }
            else{
                current = current.prev;
            }
        }
    }
}
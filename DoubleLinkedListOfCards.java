public class DoubleLinkedListOfCards{


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
}
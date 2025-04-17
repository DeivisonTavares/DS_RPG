// Nó genérico para listas encadeadas
class Node {
    Object data;
    Node next;
    Node prev;

    public Node(Object data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

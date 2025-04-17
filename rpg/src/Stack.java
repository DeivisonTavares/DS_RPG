// Pilha encadeada para histórico ou colocações
class Stack {
    Node top;
    int size;

    public Stack() {
        top = null;
        size = 0;
    }

    public void push(Object data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public Object pop() {
        if (isEmpty()) return null;
        Node temp = top;
        top = top.next;
        size--;
        return temp.data;
    }

    public Object peek() {
        if (isEmpty()) return null;
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
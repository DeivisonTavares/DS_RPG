// Fila encadeada para ordem de turnos
class Queue {
    Node head;
    Node tail;

    public Queue() {
        head = null;
        tail = null;
    }

    public void enqueue(Object data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public Object dequeue() {
        if (isEmpty()) return null;
        Node temp = head;
        head = head.next;
        if (head == null) tail = null;
        else head.prev = null;
        return temp.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void display() {
        Node current = head;
        System.out.print("Fila de turnos: ");
        while (current != null) {
            Entidade e = (Entidade) current.data;
            System.out.print(e.nome + " ");
            current = current.next;
        }
        System.out.println();
    }
}
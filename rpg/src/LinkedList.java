// Lista encadeada simples para inventário e personagens
class LinkedList {
    Node head;
    int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public void add(Object data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
            newNode.prev = current;
        }
        size++;
    }

    public Object get(int index) {
        if (index < 0 || index >= size) return null;
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void display(String type) {
        Node current = head;
        int index = 1;
        System.out.println(type + " disponíveis:");
        while (current != null) {
            if (type.equals("Habilidades")) {
                Habilidade h = (Habilidade) current.data;
                System.out.println(index + ". " + h.nome + " (ID: " + h.id + ", Dano: " + h.dano + ", Custo Mana: " + h.custoMana + ", Preço: " + h.preco + " moedas)");
            } else if (type.equals("Itens")) {
                Item i = (Item) current.data;
                System.out.println(index + ". " + i.nome + " (ID: " + i.id + ", Efeito: " + i.efeito + " " + i.tipoEfeito + ", Preço: " + i.preco + " moedas)");
            } else {
                Entidade p = (Entidade) current.data;
                System.out.println("- " + p.nome + " (Nível " + p.nivel + ", Vida " + p.vidaAtual + "/" + p.vidaMaxima + ")");
            }
            current = current.next;
            index++;
        }
        if (isEmpty()) {
            System.out.println("Nenhum " + type.toLowerCase() + " disponível.");
        }
    }

    public boolean removeById(String id) {
        Node current = head;
        while (current != null) {
            Object data = current.data;
            String currentId = null;
            if (data instanceof Item) {
                currentId = ((Item) data).id;
            } else if (data instanceof Habilidade) {
                currentId = ((Habilidade) data).id;
            }
            if (currentId != null && currentId.equals(id)) {
                if (current.prev == null) {
                    head = current.next;
                    if (head != null) head.prev = null;
                } else if (current.next == null) {
                    current.prev.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
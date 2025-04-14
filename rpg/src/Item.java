// Classe Item
class Item {
    String id;
    String nome;
    int custoMana;
    int efeito;
    int preco;

    public Item(String id, String nome, int custoMana, int efeito, int preco) {
        this.id = id;
        this.nome = nome;
        this.custoMana = custoMana;
        this.efeito = efeito;
        this.preco = preco;
    }

    public Item(String id, String nome, int custoMana, int efeito) {
        this(id, nome, custoMana, efeito, 0);
    }

    public void usar(Entidade usuario) {
        usuario.curar(efeito);
        System.out.println(usuario.nome + " usou " + nome);
    }
}
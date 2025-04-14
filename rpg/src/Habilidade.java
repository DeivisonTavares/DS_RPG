// Classe Habilidade
class Habilidade {
    String id;
    String nome;
    int custoMana;
    int dano;
    int preco;

    public Habilidade(String id, String nome, int custoMana, int dano, int preco) {
        this.id = id;
        this.nome = nome;
        this.custoMana = custoMana;
        this.dano = dano;
        this.preco = preco;
    }

    public Habilidade(String id, String nome, int custoMana, int dano) {
        this(id, nome, custoMana, dano, 0);
    }
}
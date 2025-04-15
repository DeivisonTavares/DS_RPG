// Classe Item
class Item {
    String id;
    String nome;
    int custoMana;
    int efeito;
    int preco;
    String tipoEfeito;

    public Item(String id, String nome, int custoMana, int efeito, int preco, String tipoEfeito) {
        this.id = id;
        this.nome = nome;
        this.custoMana = custoMana;
        this.efeito = efeito;
        this.preco = preco;
        this.tipoEfeito = tipoEfeito;
    }

    public void usar(Entidade usuario) {
        if (tipoEfeito.equals("vida")) {
            usuario.curar(efeito);
        } else if (tipoEfeito.equals("mana")) {
            usuario.restaurarMana(efeito);
        }
        System.out.println(usuario.nome + " usou " + nome);
    }
}
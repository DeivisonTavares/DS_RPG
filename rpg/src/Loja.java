class Loja {
    LinkedList habilidades;
    LinkedList itens;

    public Loja() {
        habilidades = new LinkedList();
        itens = new LinkedList();
        habilidades.add(new Habilidade("3", "Raio Congelante", 15, 25, 50));
        habilidades.add(new Habilidade("4", "Explosão Arcana", 20, 35, 80));
        itens.add(new Item("2", "Poção de Mana", 0, 20, 30));
        itens.add(new Item("3", "Elixir de Força", 0, 50, 60));
    }

    public void exibirLoja() {
        System.out.println("\n=== Loja ===");
        habilidades.display("Habilidades");
        itens.display("Itens");
    }

    public void comprarHabilidade(Jogador jogador, PersonagemJogador personagem, String id) {
        for (int i = 0; i < habilidades.size; i++) {
            Habilidade h = (Habilidade) habilidades.get(i);
            if (h.id.equals(id)) {
                if (jogador.saldoMoedas >= h.preco) {
                    jogador.saldoMoedas -= h.preco;
                    personagem.habilidades.add(new Habilidade(h.id, h.nome, h.custoMana, h.dano, 0));
                    System.out.println("Habilidade " + h.nome + " comprada!");
                } else {
                    System.out.println("Moedas insuficientes!");
                }
                return;
            }
        }
        System.out.println("Habilidade não encontrada!");
    }

    public void comprarItem(Jogador jogador, PersonagemJogador personagem, String id) {
        for (int i = 0; i < itens.size; i++) {
            Item item = (Item) itens.get(i);
            if (item.id.equals(id)) {
                if (jogador.saldoMoedas >= item.preco) {
                    jogador.saldoMoedas -= item.preco;
                    personagem.inventario.add(new Item(item.id, item.nome, item.custoMana, item.efeito, 0));
                    System.out.println("Item " + item.nome + " comprado!");
                } else {
                    System.out.println("Moedas insuficientes!");
                }
                return;
            }
        }
        System.out.println("Item não encontrado!");
    }
}
// Classe Loja
class Loja {
    LinkedList habilidades;
    LinkedList itens;

    public Loja() {
        habilidades = new LinkedList();
        itens = new LinkedList();
        habilidades.add(new Habilidade("3", "Raio Congelante", 15, 25, 50));
        habilidades.add(new Habilidade("4", "Explosão Arcana", 20, 35, 80));
        itens.add(new Item("2", "Poção de Mana", 0, 20, 30, "mana"));
        itens.add(new Item("3", "Elixir de Força", 0, 50, 60, "vida"));
    }

    public void exibirLoja() {
        System.out.println("\n=== Loja ===");
        habilidades.display("Habilidades");
        itens.display("Itens");
    }

    public void comprarHabilidade(Jogador jogador, PersonagemJogador personagem, String id) {
        System.out.println("Tentando comprar habilidade com ID: " + id);
        for (int i = 0; i < habilidades.size; i++) {
            Habilidade h = (Habilidade) habilidades.get(i);
            System.out.println("Verificando habilidade: " + h.nome + " (ID: " + h.id + ")");
            if (h.id.equals(id)) {
                if (jogador.saldoMoedas >= h.preco) {
                    jogador.saldoMoedas -= h.preco;
                    personagem.habilidades.add(new Habilidade(h.id, h.nome, h.custoMana, h.dano, 0));
                    System.out.println("Habilidade " + h.nome + " comprada por " + h.preco + " moedas!");
                    System.out.println("Saldo restante: " + jogador.saldoMoedas + " moedas");
                } else {
                    System.out.println("Moedas insuficientes! Necessário: " + h.preco + ", Disponível: " + jogador.saldoMoedas);
                }
                return;
            }
        }
        System.out.println("Habilidade com ID " + id + " não encontrada!");
    }

    public void comprarItem(Jogador jogador, PersonagemJogador personagem, String id) {
        System.out.println("Tentando comprar item com ID: " + id);
        for (int i = 0; i < itens.size; i++) {
            Item item = (Item) itens.get(i);
            System.out.println("Verificando item: " + item.nome + " (ID: " + item.id + ")");
            if (item.id.equals(id)) {
                if (jogador.saldoMoedas >= item.preco) {
                    jogador.saldoMoedas -= item.preco;
                    personagem.inventario.add(new Item(item.id, item.nome, item.custoMana, item.efeito, 0, item.tipoEfeito));
                    System.out.println("Item " + item.nome + " comprado por " + item.preco + " moedas!");
                    System.out.println("Saldo restante: " + jogador.saldoMoedas + " moedas");
                } else {
                    System.out.println("Moedas insuficientes! Necessário: " + item.preco + ", Disponível: " + jogador.saldoMoedas);
                }
                return;
            }
        }
        System.out.println("Item com ID " + id + " não encontrado!");
    }
}
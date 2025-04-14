class PersonagemJogador extends Entidade {
    public PersonagemJogador(String id, String nome, int vidaMax, int manaMax) {
        super(id, nome, vidaMax, manaMax);
        habilidades.add(new Habilidade("1", "Ataque Básico", 0, 10));
        habilidades.add(new Habilidade("2", "Bola de Fogo", 10, 20));
        inventario.add(new Item("1", "Poção de Vida", 0, 30));
    }

    @Override
    public void usarHabilidade(int id, Entidade alvo) {
        for (int i = 0; i < habilidades.size; i++) {
            Habilidade h = (Habilidade) habilidades.get(i);
            if (h.id.equals(String.valueOf(id))) {
                if (manaAtual >= h.custoMana) {
                    manaAtual -= h.custoMana;
                    alvo.receberDano(h.dano);
                    System.out.println(nome + " usou " + h.nome + " em " + alvo.nome);
                } else {
                    System.out.println("Mana insuficiente!");
                }
                return;
            }
        }
        System.out.println("Habilidade não encontrada!");
    }

    public void usarItem(int id) {
        for (int i = 0; i < inventario.size; i++) {
            Item item = (Item) inventario.get(i);
            if (item.id.equals(String.valueOf(id))) {
                item.usar(this);
                inventario.removeById(String.valueOf(id)); // Remove o item após o uso
                System.out.println("Item " + item.nome + " foi removido do inventário.");
                return;
            }
        }
        System.out.println("Item não encontrado!");
    }
}

// Classe Monstro
class Monstro extends Entidade {
    public Monstro(String id, String nome, int vidaMax, int manaMax) {
        super(id, nome, vidaMax, manaMax);
        habilidades.add(new Habilidade("1", "Mordida", 0, 8));
    }

    @Override
    public void usarHabilidade(int id, Entidade alvo) {
        Habilidade h = (Habilidade) habilidades.get(0);
        alvo.receberDano(h.dano);
        System.out.println(nome + " usou " + h.nome + " em " + alvo.nome);
    }
}
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
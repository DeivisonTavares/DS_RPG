import java.util.Random;
// Classe abstrata Entidade
abstract class Entidade {
    String id;
    String nome;
    int nivel;
    int vidaMaxima;
    int vidaAtual;
    int manaMaxima;
    int manaAtual;
    LinkedList habilidades;
    LinkedList inventario;
    boolean defendendo;
    Random random;

    public Entidade(String id, String nome, int vidaMax, int manaMax) {
        this.id = id;
        this.nome = nome;
        this.nivel = 1;
        this.vidaMaxima = vidaMax;
        this.vidaAtual = vidaMax;
        this.manaMaxima = manaMax;
        this.manaAtual = manaMax;
        this.habilidades = new LinkedList();
        this.inventario = new LinkedList();
        this.defendendo = false;
        this.random = new Random();
    }

    public void receberDano(int valor) {
        if (defendendo) {
            if (random.nextDouble() < 0.5) {
                System.out.println(nome + " bloqueou o ataque!");
                defendendo = false;
                return;
            } else {
                System.out.println(nome + " tentou defender, mas falhou!");
            }
        }
        vidaAtual -= valor;
        if (vidaAtual < 0) vidaAtual = 0;
        System.out.println(nome + " recebeu " + valor + " de dano. Vida: " + vidaAtual);
        defendendo = false;
    }

    public void curar(int valor) {
        vidaAtual += valor;
        if (vidaAtual > vidaMaxima) vidaAtual = vidaMaxima;
        System.out.println(nome + " curou " + valor + ". Vida: " + vidaAtual);
    }

    public void restaurarMana(int valor) {
        manaAtual += valor;
        if (manaAtual > manaMaxima) manaAtual = manaMaxima;
        System.out.println(nome + " restaurou " + valor + " de mana. Mana: " + manaAtual);
    }

    public boolean estaVivo() {
        return vidaAtual > 0;
    }

    public void subirNivel() {
        nivel++;
        vidaMaxima += 10;
        manaMaxima += 5;
        vidaAtual = vidaMaxima;
        manaAtual = manaMaxima;
        System.out.println(nome + " subiu para o nível " + nivel + "!");
    }

    public void restaurarVida() {
        vidaAtual = vidaMaxima;
        manaAtual = manaMaxima;
        System.out.println(nome + " teve vida e mana restauradas!");
    }

    abstract void usarHabilidade(int id, Entidade alvo);
}
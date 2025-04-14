// classe Jogador
class Jogador {
    String idJogador;
    String nome;
    String senha;
    int saldoMoedas;
    LinkedList personagens;

    public Jogador(String id, String nome, String senha) {
        this.idJogador = id;
        this.nome = nome;
        this.senha = senha;
        this.saldoMoedas = 100;
        this.personagens = new LinkedList();
    }

    public void cadastrar() {
        System.out.println("Jogador " + nome + " cadastrado com ID: " + idJogador);
    }

    public boolean autenticar(String nome, String senha) {
        return this.nome.equals(nome) && this.senha.equals(senha);
    }

    public void criarPersonagem(String nome, int vidaMax, int manaMax) {
        PersonagemJogador p = new PersonagemJogador("P" + (personagens.size + 1), nome, vidaMax, manaMax);
        personagens.add(p);
        System.out.println("Personagem " + nome + " criado!");
    }

    public Entidade selecionarPersonagem(String nome) {
        for (int i = 0; i < personagens.size; i++) {
            Entidade p = (Entidade) personagens.get(i);
            if (p.nome.equals(nome)) return p;
        }
        return null;
    }

    public void exibirPersonagens() {
        System.out.println("Personagens de " + nome + ":");
        for (int i = 0; i < personagens.size; i++) {
            Entidade p = (Entidade) personagens.get(i);
            System.out.println("- " + p.nome + " (Nível " + p.nivel + ", Vida " + p.vidaAtual + "/" + p.vidaMaxima + ")");
        }
    }
}
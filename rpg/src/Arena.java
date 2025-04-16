import java.util.Scanner;

class Arena {
    String idBatalha;
    Queue turnos;
    Stack colocacoes;
    Stack historicoTurnos;
    Entidade[] participantes;
    int turnoAtual;
    String estadoBatalha;

    public Arena(String id, Entidade[] participantes) {
        this.idBatalha = id;
        this.participantes = participantes;
        this.turnos = new Queue();
        this.colocacoes = new Stack();
        this.historicoTurnos = new Stack();
        this.turnoAtual = 0;
        this.estadoBatalha = "EmAndamento";
    }

    public void iniciarBatalha() {
        for (Entidade p : participantes) {
            if (p != null && p.estaVivo()) {
                turnos.enqueue(p);
            }
        }
        System.out.println("Batalha iniciada!");
        turnos.display();
    }

    public void executarTurno(Scanner scanner, Jogador jogadorAtual, Jogador adversario) {
        if (turnos.isEmpty() || estadoBatalha.equals("Finalizada")) {
            estadoBatalha = "Finalizada";
            return;
        }
        turnoAtual++;
        System.out.println("\nTurno " + turnoAtual + ":");
        turnos.display();
        Entidade atual = (Entidade) turnos.dequeue();
        if (atual != null && atual.estaVivo()) {
            // Determina qual jogador está controlando o personagem atual
            Jogador controlador = (atual == participantes[0]) ? jogadorAtual : adversario;
            System.out.println("Turno de " + controlador.nome + " controlando " + atual.nome + " (Vida: " + atual.vidaAtual + ", Mana: " + atual.manaAtual + ")\n");
            atual.habilidades.display("Habilidades");
            atual.inventario.display("Itens");
            System.out.println("Escolha uma ação:");
            System.out.println("1. Atacar");
            System.out.println("2. Usar Habilidade");
            System.out.println("3. Usar Item");
            System.out.println("4. Defender");
            System.out.println("5. Fugir");
            System.out.print("Opção: ");
            int acao = scanner.nextInt();
            scanner.nextLine();

            if (acao == 1) {
                escolherAlvo(atual, scanner);
            } else if (acao == 2) {
                System.out.print("ID da Habilidade: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                escolherAlvo(atual, scanner, id);
            } else if (acao == 3) {
                System.out.print("ID do Item: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                ((PersonagemJogador) atual).usarItem(id);
            } else if (acao == 4) {
                atual.defendendo = true;
                System.out.println(atual.nome + " está defendendo!");
                historicoTurnos.push(atual.nome + " defendeu no turno " + turnoAtual);
            } else if (acao == 5) {
                System.out.println(atual.nome + " fugiu!");
                historicoTurnos.push(atual.nome + " fugiu no turno " + turnoAtual);
                estadoBatalha = "Finalizada";
                return;
            } else {
                historicoTurnos.push(atual.nome + " agiu no turno " + turnoAtual);
            }
            if (atual.estaVivo()) {
                turnos.enqueue(atual);
            }
        }
        verificarDerrotados();
        verificarVencedor();
    }

    private void escolherAlvo(Entidade atual, Scanner scanner) {
        escolherAlvo(atual, scanner, 1);
    }

    private void escolherAlvo(Entidade atual, Scanner scanner, int idHabilidade) {
        System.out.println("Escolha o alvo:");
        int alvosVivos = 0;
        for (int i = 0; i < participantes.length; i++) {
            if (participantes[i].estaVivo() && participantes[i] != atual) {
                System.out.println((alvosVivos + 1) + ". " + participantes[i].nome);
                alvosVivos++;
            }
        }
        if (alvosVivos == 0) {
            System.out.println("Não há alvos vivos!");
            return;
        }
        System.out.print("Alvo: ");
        int escolha = scanner.nextInt() - 1;
        scanner.nextLine();
        int indiceAlvo = -1;
        int contador = 0;
        for (int i = 0; i < participantes.length; i++) {
            if (participantes[i].estaVivo() && participantes[i] != atual) {
                if (contador == escolha) {
                    indiceAlvo = i;
                    break;
                }
                contador++;
            }
        }
        if (indiceAlvo >= 0 && indiceAlvo < participantes.length && participantes[indiceAlvo].estaVivo()) {
            atual.usarHabilidade(idHabilidade, participantes[indiceAlvo]);
        } else {
            System.out.println("Alvo inválido!");
        }
    }

    private void verificarDerrotados() {
        for (Entidade p : participantes) {
            if (!p.estaVivo() && !isInColocacoes(p)) {
                colocacoes.push(p);
                System.out.println(p.nome + " foi derrotado!");
            }
        }
    }

    private boolean isInColocacoes(Entidade p) {
        Node current = colocacoes.top;
        while (current != null) {
            if (current.data == p) return true;
            current = current.next;
        }
        return false;
    }

    public Entidade verificarVencedor() {
        int vivos = 0;
        Entidade ultimoVivo = null;
        for (Entidade p : participantes) {
            if (p.estaVivo()) {
                vivos++;
                ultimoVivo = p;
            }
        }
        if (vivos <= 1) {
            estadoBatalha = "Finalizada";
            if (vivos == 1 && ultimoVivo != null) {
                colocacoes.push(ultimoVivo);
                System.out.println(ultimoVivo.nome + " é o vencedor!");
                if (ultimoVivo instanceof PersonagemJogador) {
                    ultimoVivo.subirNivel();
                }
                return ultimoVivo;
            } else {
                System.out.println("Todos os participantes foram derrotados!");
                return null;
            }
        }
        return null;
    }

    public void exibirRankingFinal() {
        System.out.println("\nRanking Final:");
        Stack temp = new Stack();
        while (!colocacoes.isEmpty()) {
            Entidade p = (Entidade) colocacoes.pop();
            temp.push(p);
        }
        int pos = 1;
        while (!temp.isEmpty()) {
            Entidade p = (Entidade) temp.pop();
            System.out.println(pos + "º: " + p.nome + " (Vivo: " + p.estaVivo() + ")");
            pos++;
        }
        for (Entidade p : participantes) {
            if (p != null) {
                p.restaurarVida();
            }
        }
    }
}
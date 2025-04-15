import java.util.Scanner;
// Classe Arena
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

    public void executarTurno(Scanner scanner) {
        if (turnos.isEmpty()) {
            estadoBatalha = "Finalizada";
            return;
        }
        turnoAtual++;
        System.out.println("\nTurno " + turnoAtual + ":");
        turnos.display();
        Entidade atual = (Entidade) turnos.dequeue();
        if (atual.estaVivo()) {
            if (atual instanceof PersonagemJogador) {
                System.out.println(atual.nome + " (Vida: " + atual.vidaAtual + ", Mana: " + atual.manaAtual + ")");
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
                    escolherAlvo(atual, scanner, 1);
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
                    return;
                } else {
                    historicoTurnos.push(atual.nome + " agiu no turno " + turnoAtual);
                }
            } else {
                for (Entidade p : participantes) {
                    if (p != atual && p.estaVivo()) {
                        atual.usarHabilidade(1, p);
                        historicoTurnos.push(atual.nome + " atacou " + p.nome);
                        break;
                    }
                }
            }
            if (atual.estaVivo()) {
                turnos.enqueue(atual);
            }
        }
        verificarDerrotados();
    }

    private void escolherAlvo(Entidade atual, Scanner scanner, int idHabilidade) {
        System.out.println("Escolha o alvo:");
        for (int i = 0; i < participantes.length; i++) {
            if (participantes[i].estaVivo() && participantes[i] != atual) {
                System.out.println(i + 1 + ". " + participantes[i].nome);
            }
        }
        System.out.print("Alvo: ");
        int alvoIdx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (alvoIdx >= 0 && alvoIdx < participantes.length && participantes[alvoIdx].estaVivo()) {
            atual.usarHabilidade(idHabilidade, participantes[alvoIdx]);
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
            if (ultimoVivo != null) {
                colocacoes.push(ultimoVivo);
                System.out.println(ultimoVivo.nome + " é o vencedor!");
                if (ultimoVivo instanceof PersonagemJogador) {
                    ultimoVivo.subirNivel();
                }
                return ultimoVivo;
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
            p.restaurarVida();
        }
    }
}
import java.util.Scanner;
// Classe principal do jogo
class Jogo {
    LinkedList jogadores;
    Arena arenaAtual;
    Loja loja;

    public Jogo() {
        jogadores = new LinkedList();
        loja = new Loja();
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== RPG de Batalha ===");
            System.out.println("1. Cadastrar Jogador");
            System.out.println("2. Login");
            System.out.println("3. Sair");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("Senha: ");
                String senha = scanner.nextLine();
                Jogador j = new Jogador("J" + (jogadores.size + 1), nome, senha);
                j.cadastrar();
                jogadores.add(j);
            } else if (opcao == 2) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("Senha: ");
                String senha = scanner.nextLine();
                Jogador jogador = autenticar(nome, senha);
                if (jogador != null) {
                    menuJogador(jogador, scanner);
                } else {
                    System.out.println("Credenciais inválidas!");
                }
            } else if (opcao == 3) {
                System.out.println("Saindo...");
                break;
            }
        }
        scanner.close();
    }

    private Jogador autenticar(String nome, String senha) {
        for (int i = 0; i < jogadores.size; i++) {
            Jogador j = (Jogador) jogadores.get(i);
            if (j.autenticar(nome, senha)) return j;
        }
        return null;
    }

    private void menuJogador(Jogador jogador, Scanner scanner) {
        while (true) {
            System.out.println("\nBem-vindo, " + jogador.nome + " (Moedas: " + jogador.saldoMoedas + ")");
            System.out.println("1. Criar Personagem");
            System.out.println("2. Ver Personagens");
            System.out.println("3. Lutar (PvE)");
            System.out.println("4. Lutar (PvP)");
            System.out.println("5. Visitar Loja");
            System.out.println("6. Voltar");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                System.out.print("Nome do Personagem: ");
                String nome = scanner.nextLine();
                jogador.criarPersonagem(nome, 100, 50);
            } else if (opcao == 2) {
                jogador.exibirPersonagens();
            } else if (opcao == 3) {
                System.out.print("Nome do Personagem: ");
                String nome = scanner.nextLine();
                Entidade p = jogador.selecionarPersonagem(nome);
                if (p != null) {
                    Entidade monstro = new Monstro("M1", "Monstro", 80, 20);
                    Entidade[] participantes = {p, monstro};
                    arenaAtual = new Arena("Batalha" + System.currentTimeMillis(), participantes);
                    arenaAtual.iniciarBatalha();
                    while (arenaAtual.estadoBatalha.equals("EmAndamento")) {
                        arenaAtual.executarTurno(scanner);
                        arenaAtual.verificarVencedor();
                    }
                    arenaAtual.exibirRankingFinal();
                    if (arenaAtual.verificarVencedor() == p) {
                        jogador.saldoMoedas += 50;
                        System.out.println(jogador.nome + " ganhou 50 moedas!");
                    }
                    System.out.println("Deseja continuar? (1. Sim / 2. Não)");
                    int continuar = scanner.nextInt();
                    scanner.nextLine();
                    if (continuar == 2) break;
                } else {
                    System.out.println("Personagem não encontrado!");
                }
            } else if (opcao == 4) {
                System.out.print("Nome do adversário: ");
                String nomeAdv = scanner.nextLine();
                Jogador adversario = null;
                for (int i = 0; i < jogadores.size; i++) {
                    Jogador j = (Jogador) jogadores.get(i);
                    if (j.nome.equals(nomeAdv)) {
                        adversario = j;
                        break;
                    }
                }
                if (adversario != null) {
                    System.out.print("Seu personagem: ");
                    String nomeP1 = scanner.nextLine();
                    System.out.print("Personagem do adversário: ");
                    String nomeP2 = scanner.nextLine();
                    Entidade p1 = jogador.selecionarPersonagem(nomeP1);
                    Entidade p2 = adversario.selecionarPersonagem(nomeP2);
                    if (p1 != null && p2 != null) {
                        Entidade[] participantes = {p1, p2};
                        arenaAtual = new Arena("BatalhaPvP" + System.currentTimeMillis(), participantes);
                        arenaAtual.iniciarBatalha();
                        while (arenaAtual.estadoBatalha.equals("EmAndamento")) {
                            arenaAtual.executarTurno(scanner);
                            arenaAtual.verificarVencedor();
                        }
                        arenaAtual.exibirRankingFinal();
                        if (arenaAtual.verificarVencedor() == p1) {
                            jogador.saldoMoedas += 100;
                            System.out.println(jogador.nome + " ganhou 100 moedas!");
                        }
                        System.out.println("Deseja continuar? (1. Sim / 2. Não)");
                        int continuar = scanner.nextInt();
                        scanner.nextLine();
                        if (continuar == 2) break;
                    } else {
                        System.out.println("Personagem(s) não encontrado(s)!");
                    }
                } else {
                    System.out.println("Adversário não encontrado!");
                }
            } else if (opcao == 5) {
                loja.exibirLoja();
                System.out.println("1. Comprar Habilidade");
                System.out.println("2. Comprar Item");
                System.out.println("3. Voltar");
                System.out.print("Opção: ");
                int lojaOpcao = scanner.nextInt();
                scanner.nextLine();
                if (lojaOpcao == 1 || lojaOpcao == 2) {
                    System.out.print("Nome do Personagem: ");
                    String nomePers = scanner.nextLine();
                    Entidade p = jogador.selecionarPersonagem(nomePers);
                    if (p instanceof PersonagemJogador) {
                        System.out.print("ID do item/habilidade: ");
                        String id = scanner.nextLine();
                        if (lojaOpcao == 1) {
                            loja.comprarHabilidade(jogador, (PersonagemJogador) p, id);
                        } else {
                            loja.comprarItem(jogador, (PersonagemJogador) p, id);
                        }
                    } else {
                        System.out.println("Personagem inválido!");
                    }
                }
            } else if (opcao == 6) {
                break;
            }
        }
    }
}
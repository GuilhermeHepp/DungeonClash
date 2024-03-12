package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Jogo {
    private Scanner scanner;
    Equipe equipeHerois = new Equipe();
    Equipe equipeInimigos = new Equipe();

    public Jogo() {
        scanner = new Scanner(System.in);
    }

    public void iniciarJogo() {
        System.out.println("####### Bem Vindo ao Dungeon Clash #######");
        System.out.println("       ###Selecione uma opção###:         ");
        System.out.println("           1-Iniciar o jogo               ");
        System.out.println("           2-Sair do jogo                 ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                criarEquipe();
                carregarFases("game.txt");
                break;
            case 2:
                System.out.println("Obrigado por jogar Dungeon Clash!");
                System.exit(0);
                break;

            default:
                System.out.println("Opção inválida. Tente novamente.");
                iniciarJogo(); // Chama novamente o método para permitir uma nova entrada
                break;
        }
    }

    private void criarEquipe() {
        System.out.println("Crie uma equipe de personagens digitando o numero de 1 até 3 personagens:");
        int quantidadePerso = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < quantidadePerso; i++) {
            System.out.println("Digite o nome do personagem:");
            String nome = scanner.nextLine();
            System.out.println("Escolha a classe do personagem:");
            System.out.println("1-Arqueiro");
            System.out.println("2-Guerreiro");
            System.out.println("3-Mago");
            int escolhaClasse = scanner.nextInt();
            scanner.nextLine();
            Classe classe = null;
            switch (escolhaClasse) {
                case 1:
                    classe = new Arqueiro();
                    break;
                case 2:
                    classe = new Guerreiro();
                    break;
                case 3:
                    classe = new Mago();
                    break;
                default:
                    break;
            }
            Personagem heroi = new Personagem(nome, classe);
            equipeHerois.adicionarEquipe(heroi);
            imprimirStatus(heroi, classe);
        }

        System.out.println("Equipe criada com sucesso!");
    }

    public void carregarFases(String nomeArquivo) {
        System.out.println("\n");
        System.out.println("Carregando fases...");
        try {
            Scanner scanner = new Scanner(new File(nomeArquivo));
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.contains("inimigos")) {
                    System.out.println("Descrição de " + linha);
                    String[] vetor = linha.split(" ");
                    String nomeInimigo = vetor[1];
                    int nivelInimigo = Integer.parseInt(vetor[3]);
                    String escolhaClasse = vetor[2];
                    Classe classeInimigo = null;
                    switch (escolhaClasse) {
                        case "Arqueiro":
                            classeInimigo = new Arqueiro();
                            break;

                        case "Guerreiro":
                            classeInimigo = new Guerreiro();
                            break;

                        case "Mago":
                            classeInimigo = new Mago();
                            break;

                        case "Monstro":
                            classeInimigo = new Monstro();
                            break;
                        default:
                            break;
                    }

                    Personagem inimigo = new Personagem(nomeInimigo, classeInimigo);
                    inimigo.setNivel(nivelInimigo);
                    equipeInimigos.adicionarEquipe(inimigo);
                    imprimirStatus(inimigo, classeInimigo);
                }

                if (linha.contains("fase")) {
                    System.out.println("Descrição de fase: " + linha);
                    System.out.println("\n");
                    comecarBatalha();
                    boolean continuar = continuarJogo();
                    if (continuar) {
                        if (equipeHerois.isEmpty()) {
                            System.out.println("Equipe de heróis derrotada!");
                            System.out.println("Reinicie o jogo para tentar novamente.");
                            System.out.println("Obrigado por jogar Dungeon Clash!");
                            System.exit(0);
                        }
                        System.out.println("Continuando o jogo...");
                    } else {
                        System.out.println("Obrigado por jogar Dungeon Clash!");
                        System.out.println("Jogo finalizado");
                        System.exit(0);
                    }

                }

                if (linha.contains("fim")) {
                    System.out.println("Parabens! Você venceu o jogo!");
                    System.out.println("Fim do jogo!");
                    System.exit(0);
                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao carregar fases: arquivo não encontrado");
            System.exit(1);
        }
        System.out.println("Fases carregadas com sucesso!");

        // Exibir uma mensagem informando que a próxima fase está começando
        System.out.println("Preparando para a próxima fase...");
    }

    public boolean continuarJogo() {
        System.out.println("Deseja continuar o jogo?");
        System.out.println("1-Sim");
        System.out.println("2-Não");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        if (opcao == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void comecarBatalha() {
        System.out.println("Batalha iniciada!");
        // Determina o próximo atacante
        while (!equipeHerois.isEmpty() && !equipeInimigos.isEmpty()) {
            Equipe atacantes, alvos;
            if (equipeHerois.éSuaVezDeAtacar()) {
                atacantes = equipeHerois;
                alvos = equipeInimigos;
            } else {
                atacantes = equipeInimigos;
                alvos = equipeHerois;
            }

            for (Personagem atacante : atacantes.getMembros()) {
                if (atacante.getTempoEspera() > 0) {
                    System.out.println(atacante.getNome() + " está em repouso.");
                    continue;
                }
                if (equipeHerois.isEmpty()) {
                    System.out.println("Equipe de heróis derrotada!");
                    break;
                }
                if (equipeInimigos.isEmpty()) {
                    System.out.println("Equipe de inimigos derrotada!");
                    equipeHerois.resetarTempoEspera();
                    // Definir que a próxima rodada é dos heróis
                    equipeHerois.setSuaVezDeAtacar(true);
                    break;
                }
                Personagem alvo = alvos.getMembros().get(0);
                mostrarStatus(atacante, alvo);
                mostrarHabilidades(atacante);
                System.out.println("Digite o número da habilidade que deseja usar:");
                scanner = new Scanner(System.in);
                int indexHabilidade = scanner.nextInt();

                try {
                    atacante.atacar(atacante, alvo, indexHabilidade, alvos);
                    mostrarStatus(atacante, alvo);
                    Iterator<Personagem> iteratorHerois = equipeHerois.getMembros().iterator();
                    while (iteratorHerois.hasNext()) {
                        Personagem membroHeroi = iteratorHerois.next();
                        if (membroHeroi.getPV() <= 0) {
                            System.out.println(membroHeroi.getNome() + " foi derrotado!");
                            membroHeroi.setTempoEspera(10); // Herói em repouso por 10 rodadas
                            iteratorHerois.remove(); // Remove o membro derrotado usando o iterador
                        }
                    }

                    // Remover membros derrotados da equipe de inimigos
                    Iterator<Personagem> iteratorInimigos = equipeInimigos.getMembros().iterator();
                    while (iteratorInimigos.hasNext()) {
                        Personagem membroInimigo = iteratorInimigos.next();
                        if (membroInimigo.getPV() <= 0) {
                            System.out.println(membroInimigo.getNome() + " foi derrotado!");
                            atacante.ganharExperiencia(alvo.getNivel(), equipeHerois);
                            iteratorInimigos.remove(); // Remove o membro derrotado usando o iterador
                        }
                    }
                } catch (SemManaException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }

            // Alterna a vez de atacar entre as equipes
            equipeHerois.setSuaVezDeAtacar(!equipeHerois.éSuaVezDeAtacar());
            equipeInimigos.setSuaVezDeAtacar(!equipeInimigos.éSuaVezDeAtacar());
            // Atualiza o tempo de espera de todos os personagens
            equipeHerois.atualizarTempoEspera();
            equipeInimigos.atualizarTempoEspera();
        }
        equipeHerois.resetarTempoEspera();
        // Definir que a próxima rodada é dos heróis
        equipeHerois.setSuaVezDeAtacar(true);
    }

    public void mostrarHabilidades(Personagem atacante) {
        System.out.format("Habilidades do %s:\n", atacante.getNome());
        Classe classeDoAtacante = atacante.getClasse();
        // Verifica o tipo de classe do atacante e exibe suas habilidades
        // Exibe as habilidades do Arqueiro
        if (classeDoAtacante instanceof Arqueiro) {
            Arqueiro arqueiro = (Arqueiro) classeDoAtacante;
            ArrayList<Habilidade> habilidades = arqueiro.getHabilidades();
            for (Habilidade habilidade : habilidades) {
                System.out.format("%d-%s\n", habilidade.getID(), habilidade.getNome());
                System.out.println("Tempo de espera: " + habilidade.getTempo() + " rodadas");
                System.out.println("Afeta todos: " + habilidade.isAfetaTodos());
                System.out.println("Afeta amigos: " + habilidade.isAfetaAmigos());
                System.out.println("Mana necessária: " + habilidade.getManaNecessaria(habilidade.getNome(), atacante.getNivel(), atacante));
                System.out.println("\n");
            }
        }
        // Exibe as habilidades do Guerreiro
        else if (classeDoAtacante instanceof Guerreiro) {
            Guerreiro guerreiro = (Guerreiro) classeDoAtacante;
            ArrayList<Habilidade> habilidades = guerreiro.getHabilidades();
            for (Habilidade habilidade : habilidades) {
                System.out.format("%d-%s\n", habilidade.getID(), habilidade.getNome());
                System.out.println("Tempo de espera: " + habilidade.getTempo() + " rodadas");
                System.out.println("Afeta todos: " + habilidade.isAfetaTodos());
                System.out.println("Afeta amigos: " + habilidade.isAfetaAmigos());
                System.out.println("Mana necessária: " + habilidade.getManaNecessaria(habilidade.getNome(), atacante.getNivel(), atacante));
                System.out.println("\n");
            }
        }
        // Exibe as habilidades do Mago
        else if (classeDoAtacante instanceof Mago) {
            Mago mago = (Mago) classeDoAtacante;
            ArrayList<Habilidade> habilidades = mago.getHabilidades();
            for (Habilidade habilidade : habilidades) {
                System.out.format("%d-%s\n", habilidade.getID(), habilidade.getNome());
                System.out.println("Tempo de espera: " + habilidade.getTempo() + " rodadas");
                System.out.println("Afeta todos: " + habilidade.isAfetaTodos());
                System.out.println("Afeta amigos: " + habilidade.isAfetaAmigos());
                System.out.println("Mana necessária: " + habilidade.getManaNecessaria(habilidade.getNome(), atacante.getNivel(), atacante));
                System.out.println("\n");
            }
        }
        // Exibe as habilidades do Monstro
        else if (classeDoAtacante instanceof Monstro) {
            Monstro monstro = (Monstro) classeDoAtacante;
            ArrayList<Habilidade> habilidades = monstro.getHabilidades();
            for (Habilidade habilidade : habilidades) {
                System.out.format("%d-%s\n", habilidade.getID(), habilidade.getNome());
                System.out.println("Tempo de espera: " + habilidade.getTempo() + " rodadas");
                System.out.println("Afeta todos: " + habilidade.isAfetaTodos());
                System.out.println("Afeta amigos: " + habilidade.isAfetaAmigos());
                System.out.println("Mana necessária: 0");
                System.out.println("\n");
            }
        }
    }

    public void imprimirStatus(Personagem personagem, Classe classe) {
        System.out.println("Nome: " + personagem.getNome());
        System.out.println("Classe: " + classe.getClass().getSimpleName());
        System.out.println("Nível: " + personagem.getNivel());
        System.out.println("Pontos de Experiência: " + personagem.getPE());
        System.out.println("Pontos de Vida: " + personagem.getPV());
        System.out.println("Pontos de Magia: " + personagem.getPM());
    }

    public void mostrarStatus(Personagem atacante, Personagem alvo) {
        System.out.println("Nome: " + atacante.getNome());
        System.out.println("Pontos de Vida: " + atacante.getPV());
        System.out.println("Pontos de Magia: " + atacante.getPM());
        System.out.println("Classe: " + atacante.getClasse().getClass().getSimpleName());
        System.out.println("Nível: " + atacante.getNivel());
        System.out.println("Tempo de espera: " + atacante.getTempoEspera() + " rodadas");
        System.out.println("ID: " + atacante.getID());
        System.out.println("\n");

        System.out.println("Nome: " + alvo.getNome());
        System.out.println("Pontos de Vida: " + alvo.getPV());
        System.out.println("Pontos de Magia: " + alvo.getPM());
        System.out.println("Classe: " + alvo.getClasse().getClass().getSimpleName());
        System.out.println("Nível: " + alvo.getNivel());
        System.out.println("Tempo de espera: " + alvo.getTempoEspera() + " rodadas");
        System.out.println("ID: " + alvo.getID());
        System.out.println("\n");
    }

}

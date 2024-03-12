package classes;

import java.util.ArrayList;

public class Personagem {
    private String nome; // Nome do personagem
    private int nivel, PE; // Nível do personagem, Pontos de Experiência
    private float PV, PM; // Pontos de Vida, Pontos de Magia
    private int tempoEspera; // Tempo de espera resultando do uso de uma habilidade
    private Classe classe; // Define a classe do personagem (Arqueiro/Guerreiro/Mago/ Monstro)
    private int ID;
    private static int contadorIDs = 0;

    // Metodo Construtor
    public Personagem(String nome, Classe classe) {
        this.nome = nome;
        this.nivel = 1;
        this.classe = classe;
        this.PE = 0;
        this.PV = calculoPvMax();
        this.PM = calculoPmMax();
        this.tempoEspera = 0;
        this.ID = contadorIDs++;
    }

    // Metodo que calcula PVMAX
    private float calculoPvMax() {
        return this.nivel * this.classe.getForca() + (this.nivel * this.classe.getAgilidade() / 2);
    }

    // Metodo que calcula PMMAX
    private float calculoPmMax() {
        return this.nivel * this.classe.getInteligencia() + (this.nivel * this.classe.getAgilidade() / 3);
    }

    // Metodo Ganho de Experiencia
    public void ganharExperiencia(int nivelInimigo, Equipe equipeHerois) {
        int ganhoexp = nivelInimigo * 5;
        for(Personagem heroi : equipeHerois.getMembros()){
            heroi.PE += ganhoexp;
        }
        // Verificar se os personagens podem subir de nível
        for (Personagem heroi : equipeHerois.getMembros()) {
            if (PE >= heroi.getNivel() * 25) {
                int novoNivel = subirNivel(heroi);
                System.out.println(heroi.getNome() + " subiu de nível para " + novoNivel);
            }
        }
    }

    public int subirNivel(Personagem heroi) {
        heroi.nivel += 1;
        heroi.setPE(0);
        
        Classe classe = heroi.getClasse();
        if (classe instanceof Arqueiro) {
            classe.setAgilidade(classe.getAgilidade() + 3);
            classe.setInteligencia(classe.getInteligencia() + 2);
            classe.setForca(classe.getForca() + 1);
        } else if (classe instanceof Guerreiro) {
            classe.setAgilidade(classe.getAgilidade() + 1);
            classe.setInteligencia(classe.getInteligencia() + 1);
            classe.setForca(classe.getForca() + 4);
        } else if (classe instanceof Mago) {
            classe.setAgilidade(classe.getAgilidade() + 2);
            classe.setInteligencia(classe.getInteligencia() + 3);
            classe.setForca(classe.getForca() + 1);
        }
        return heroi.getNivel();
    }

    public void atacar(Personagem atacante, Personagem alvo, int indexHabilidade, Equipe equipeHerois)
            throws SemManaException {
        if (atacante.tempoEspera != 0) {
            System.out.println(
                    atacante.nome + " não pode atacar, pois está esperando " + atacante.tempoEspera + " rodadas");
            return;
        }

        Classe classeDoAtacante = atacante.getClasse();
        if (classeDoAtacante == null) {
            System.out.println("Erro: Classe do atacante não encontrada.");
            return;
        }

        if (classeDoAtacante instanceof Arqueiro) {
            processarAtaqueArqueiro(atacante, alvo, indexHabilidade);
        } else if (classeDoAtacante instanceof Guerreiro) {
            processarAtaqueGuerreiro(atacante, alvo, indexHabilidade);
        } else if (classeDoAtacante instanceof Mago) {
            processarAtaqueMago(atacante, alvo, indexHabilidade, equipeHerois);
        } else if (classeDoAtacante instanceof Monstro) {
            processarAtaqueMonstro(atacante, alvo, indexHabilidade, equipeHerois);
        } else {
            System.out.println("Tipo de classe de atacante não suportado.");
        }
    }

    private void processarAtaqueArqueiro(Personagem atacante, Personagem alvo, int indexHabilidade)
            throws SemManaException {
        Arqueiro arqueiro = (Arqueiro) atacante.getClasse();
        // Lógica para processar ataques de arqueiro
        // Acesso para habilidade do arqueiro de acordo com o indexHabilidade
        ArrayList<Habilidade> habilidades = arqueiro.getHabilidades();

        if (indexHabilidade == habilidades.get(0).getID()) {
            double dano = habilidades.get(0).calcularDanoSocarA(arqueiro, atacante.getNivel());
            alvo.PV -= dano;
            atacante.setTempoEspera(habilidades.get(0).getTempo());
            System.out.println(atacante.nome + " atacou " + alvo.nome + " com Socar causando " + dano + " de dano");
        } else if (indexHabilidade == habilidades.get(1).getID()) {
            double dano = habilidades.get(1).calcularAtirarFlecha(arqueiro, atacante.getNivel());
            alvo.PV -= dano;
            atacante.setTempoEspera(habilidades.get(1).getTempo());
            System.out.println(
                    atacante.nome + " atacou " + alvo.nome + " com Atirar Flecha causando " + dano + " de dano");

        } else if (indexHabilidade == habilidades.get(2).getID()) {
            double dano = habilidades.get(2).calcularFlechaEncantada(atacante.getNivel(), arqueiro, atacante.getPM());
            alvo.PV -= dano;
            atacante.setPM(habilidades.get(2).calcularManaNecessariaFlecha(atacante.getNivel(), arqueiro));
            atacante.setTempoEspera(habilidades.get(2).getTempo());
            System.out.println(
                    atacante.nome + " atacou " + alvo.nome + " com Flecha Encantada causando " + dano + " de dano");

        }
    }

    private void processarAtaqueGuerreiro(Personagem atacante, Personagem alvo, int indexHabilidade)
            throws SemManaException {
        Guerreiro guerreiro = (Guerreiro) atacante.getClasse();
        // Lógica para processar ataques de guerreiro
        ArrayList<Habilidade> habilidades = guerreiro.getHabilidades();
        if (indexHabilidade == habilidades.get(0).getID()) {
            double dano = habilidades.get(0).calcularDanoSocarG(guerreiro, atacante.getNivel());
            alvo.PV -= dano;
            atacante.setTempoEspera(habilidades.get(0).getTempo());
            System.out.println(atacante.nome + " atacou " + alvo.nome + " com Socar causando " + dano + " de dano");

        } else if (indexHabilidade == habilidades.get(1).getID()) {
            double dano = habilidades.get(1).calcularGolpeDeEspada(guerreiro, atacante.getNivel());
            alvo.PV -= dano;
            atacante.setTempoEspera(habilidades.get(1).getTempo());
            System.out.println(
                    atacante.nome + " atacou " + alvo.nome + " com Golpe de Espada causando " + dano + " de dano");

        } else if (indexHabilidade == habilidades.get(2).getID()) {
            double dano = habilidades.get(2).calcularEspadaFlamejante(atacante.getNivel(), guerreiro, atacante.getPM());
            alvo.PV -= dano;
            atacante.setPM(habilidades.get(2).calcularManaNecessariaEspada(atacante.getNivel(), guerreiro));
            atacante.setTempoEspera(habilidades.get(2).getTempo());
            System.out.println(
                    atacante.nome + " atacou " + alvo.nome + " com Espada Flamejante causando " + dano + " de dano");

        }
    }

    private void processarAtaqueMago(Personagem atacante, Personagem alvo, int indexHabilidade, Equipe equipeHerois)
            throws SemManaException {
        Mago mago = (Mago) atacante.getClasse();
        // Lógica para processar ataques de mago
        ArrayList<Habilidade> habilidades = mago.getHabilidades();
        if (indexHabilidade == habilidades.get(0).getID()) {
            double dano = habilidades.get(0).calcularDanoSocarMa(atacante.getNivel(), mago);
            alvo.PV -= dano;
            atacante.setTempoEspera(habilidades.get(0).getTempo());
            System.out.println(atacante.nome + " atacou " + alvo.nome + " com Socar causando " + dano + " de dano");

        }
        if (indexHabilidade == habilidades.get(1).getID()) {
            double dano = habilidades.get(1).calcularEnfraquecer(atacante.getNivel(), mago, atacante.getPM());
            alvo.PV -= dano;
            atacante.setPM(habilidades.get(1).calcularManaNecessariaEnfra(atacante.getNivel(), mago));
            atacante.setTempoEspera(habilidades.get(1).getTempo());
            System.out
                    .println(atacante.nome + " atacou " + alvo.nome + " com Enfrquecer causando " + dano + " de dano");

        }
        if (indexHabilidade == habilidades.get(2).getID()) {
            double cura = habilidades.get(2).calcularCuraAmigo(atacante.getNivel(), mago, atacante.getPM());
            equipeHerois.getMembros().forEach((personagem) -> {
                personagem.PV += cura;
            });
            atacante.setPM(habilidades.get(2).calcularManaNecessariaCura(atacante.getNivel(), mago));
            atacante.setTempoEspera(habilidades.get(2).getTempo());
            System.out.println(atacante.nome + " curou " + equipeHerois.getMembros() + " com Cura Amigo causando "
                    + cura + " de cura");

        }
    }

    private void processarAtaqueMonstro(Personagem atacante, Personagem alvo, int indexHabilidade, Equipe equipeHerois)
            throws SemManaException {
        Monstro monstro = (Monstro) atacante.getClasse();
        // Lógica para processar ataques de monstro
        ArrayList<Habilidade> habilidades = monstro.getHabilidades();
        if (indexHabilidade == habilidades.get(0).getID()) {
            double dano = habilidades.get(0).calcularDanoSocarMo(atacante.getNivel(), monstro);
            alvo.PV -= dano;
            atacante.setTempoEspera(habilidades.get(0).getTempo());
            System.out.println(atacante.nome + " atacou " + alvo.nome + " com Socar causando " + dano + " de dano");

        }
        if (indexHabilidade == habilidades.get(1).getID()) {
            double dano = habilidades.get(1).calcularChutar(atacante.getNivel(), monstro);
            alvo.PV -= dano;
            atacante.setTempoEspera(habilidades.get(0).getTempo());
            System.out.println(atacante.nome + " atacou " + alvo.nome + " com Chutar causando " + dano + " de dano");

        }
        if (indexHabilidade == habilidades.get(2).getID()) {
            double dano = habilidades.get(2).calcularGritoAtordoante(atacante.getNivel(), monstro);
            equipeHerois.getMembros().forEach((personagem) -> {
                personagem.PV -= dano;
            });
            atacante.setTempoEspera(habilidades.get(2).getTempo());
            System.out.println(atacante.nome + " atacou com Grito Atordoante causando " + dano
                    + " de dano em todos os membros da equipe de herois");

        }
    }

    public String getNome() {
        return nome;
    }

    public int getNivel() {
        return nivel;
    }

    public int getPE() {
        return PE;
    }

    public float getPV() {
        return PV;
    }

    public float getPM() {
        return PM;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public void setPV(double d) {
        this.PV = this.PV + (float) d;
    }

    public void setPM(double d) {
        this.PM = this.PM - (float) d;
    }

    public Classe getClasse() {
        return classe;
    }

    public int getID() {
        return ID;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    private void setPE(int i) {
        this.PE = i;
    }

}

package classes;

public class Habilidade {
    private String nome; // Nome da habilidade
    private PesosDeAtributos pesosDano, pesosMana; // Define pesos de dano e mana(magia) associados a habilidade
    private int tempo; // Tempo de espera associado a habilidade
    private boolean afetaTodos; // Se afeta todos os personagens da equipe adversária
    private boolean afetaAmigos; // Se pode ser utilizada em amigos
    private int ID; // Identificador único da habilidade. Lembre do uso da técnica de atributos
                    // estaticos (static) para implementar um contador automatizado.
    private static int contadorID = 0;

    public Habilidade(String nome, PesosDeAtributos pesoDano, PesosDeAtributos pesoMana, int tempo, boolean afetaAmigos,
            boolean afetaTodos, int ID) {
        this.nome = nome;
        this.ID = contadorID++;
        this.tempo = tempo;
        this.pesosDano = pesoDano;
        this.pesosMana = pesoMana;
        this.afetaAmigos = afetaAmigos;
        this.afetaTodos = afetaTodos;
    }

    // ARQUEIRO
    // calcula o dano da habilidade socar do Arqueiro
    public double calcularDanoSocarA(Arqueiro arqueiro, int nivel) {
        pesosDano = arqueiro.pesosDanoSocar();
        double dano = nivel * Math
                .ceil(arqueiro.agilidade * pesosDano.getPesoAgilidade() + arqueiro.forca * pesosDano.getPesoForca());
        return dano;
    }

    // calcula o dano da habilidade atirar flecha do arqueiro
    public double calcularAtirarFlecha(Arqueiro arqueiro, int nivel) {
        pesosDano = arqueiro.pesosDanoAtirarFlecha();
        double dano = nivel * Math
                .ceil(arqueiro.agilidade * pesosDano.getPesoAgilidade() + arqueiro.forca * pesosDano.getPesoForca());
        return dano;
    }

    // calcula o dano da habilidade flecha encantadada do arqueiro
    public double calcularFlechaEncantada(int nivel, Arqueiro arqueiro, float PM) throws SemManaException {
        // nível*Math.ceil( inteligência+agilidade*0.2)
        double manaNecessaria = calcularManaNecessariaFlecha(nivel, arqueiro);
        if (PM >= manaNecessaria) {
            pesosDano = arqueiro.pesosDanoFlechaEncantada();
            double dano = nivel * Math
                    .ceil(arqueiro.forca * pesosDano.getPesoForca() + arqueiro.agilidade * pesosDano.getPesoAgilidade()
                            + arqueiro.inteligencia * pesosDano.getPesoInteligencia());
            return dano;
        }
        throw new SemManaException("Sem mana Suficiente");
    }
    public double calcularManaNecessariaFlecha(int nivel, Arqueiro arqueiro) {
        pesosMana = arqueiro.pesosManaFlechaEncantada();
        double manaNecessaria = nivel
                * Math.ceil((arqueiro.inteligencia + arqueiro.agilidade) * pesosMana.getPesoInteligencia());
        return (double) manaNecessaria;
    }

    // GUERREIRO
    // calcula o dano da habilidade socar do Guerreiro
    public double calcularDanoSocarG(Guerreiro guerreiro, int nivel) {
        pesosDano = guerreiro.pesosDanoSocar();
        double dano = nivel * Math
                .ceil(guerreiro.agilidade * pesosDano.getPesoAgilidade() + guerreiro.forca * pesosDano.getPesoForca());
        return dano;
    }

    // calcula o dano da habilidade Golpe de Espada do Guerreiro
    public double calcularGolpeDeEspada(Guerreiro guerreiro, int nivel) {
        pesosDano = guerreiro.pesosDanoGolpeDeEspada();
        double dano = nivel * Math
                .ceil(guerreiro.agilidade * pesosDano.getPesoAgilidade() + guerreiro.forca * pesosDano.getPesoForca());
        return dano;
    }

    // calcula o dano da habilidade Espada Flamejante do Guerreiro
    public double calcularEspadaFlamejante(int nivel, Guerreiro guerreiro, float f) throws SemManaException {
        // nível*Math.ceil( inteligência+agilidade*0.2)
        double manaNecessaria = calcularManaNecessariaEspada(nivel, guerreiro);
        if (f >= manaNecessaria) {
            pesosDano = guerreiro.pesosDanoEspadaFlamejante();
            double dano = nivel * Math.ceil(
                    guerreiro.forca * pesosDano.getPesoForca() + guerreiro.agilidade * pesosDano.getPesoAgilidade()
                            + guerreiro.inteligencia * pesosDano.getPesoInteligencia());
            return dano;
        }
        throw new SemManaException("Sem mana Suficiente");
    }

    public double calcularManaNecessariaEspada(int nivel, Guerreiro guerreiro) {
        pesosMana = guerreiro.pesosManaEspadaFlamejante();
        double manaNecessaria = nivel
                * Math.ceil((guerreiro.inteligencia + guerreiro.agilidade) * pesosMana.getPesoInteligencia());
        return (double) manaNecessaria;
    }

    // MAGO
    // calcula o dano da habilidade socar do Mago
    public double calcularDanoSocarMa(int nivel, Mago mago) {
        pesosDano = mago.pesosDanoSocar();
        double dano = nivel
                * Math.ceil(mago.agilidade * pesosDano.getPesoAgilidade() + mago.forca * pesosDano.getPesoForca());
        return dano;
    }

    // calcula o dano da habilidade enfraquecer do Mago
    public double calcularEnfraquecer(int nivel, Mago mago, float PM) throws SemManaException {
        double manaNecessaria = calcularManaNecessariaEnfra(nivel, mago);

        if (PM >= manaNecessaria) {
            pesosDano = mago.pesosDanoEnfraquecer();
            double dano = nivel * Math.ceil(mago.agilidade * pesosDano.getPesoAgilidade()
                    + mago.forca * pesosDano.getPesoForca() + mago.inteligencia * pesosDano.getPesoInteligencia());
            return dano;
        }
        throw new SemManaException("Sem mana Suficiente");
    }

    public double calcularManaNecessariaEnfra(int nivel, Mago mago) {
        pesosMana = mago.pesosManaEnfraquecer();
        double manaNecessaria = nivel * Math.ceil(mago.inteligencia * pesosMana.getPesoInteligencia());
        return (double) manaNecessaria;
    }

    // calcula o dano da habilidade cura amigo do mago
    public double calcularCuraAmigo(int nivel, Mago mago, float PM) throws SemManaException {
        // nível*Math.ceil( inteligência+agilidade*0.2)
        double manaNecessaria = calcularManaNecessariaCura(nivel, mago);

        if (PM >= manaNecessaria) {
            pesosDano = mago.pesosDanoCurarAmigo();
            double dano = nivel
                    * Math.ceil(mago.forca * pesosDano.getPesoForca() + mago.agilidade * pesosDano.getPesoAgilidade()
                            + mago.inteligencia * pesosDano.getPesoInteligencia());
            return dano;
        }
        throw new SemManaException("Sem mana Suficiente");
    }

    public double calcularManaNecessariaCura(int nivel, Mago mago) {
        pesosMana = mago.pesosManaCurarAmigo();
        double manaNecessaria = nivel * Math.ceil(mago.inteligencia * pesosMana.getPesoInteligencia());
        return (double) manaNecessaria;
    }

    // MONSTRO
    // calcula o dano da habilidade socar do Monstro
    public double calcularDanoSocarMo(int nivel, Monstro monstro) {
        pesosDano = monstro.pesosDanoSocar();
        double dano = nivel * Math
                .ceil(monstro.agilidade * pesosDano.getPesoAgilidade() + monstro.forca * pesosDano.getPesoForca());
        return dano;
    }

    // calcula o dano da habilidade chutar do Monstro
    public double calcularChutar(int nivel, Monstro monstro) {
        pesosDano = monstro.pesosDanoChutar();
        double dano = nivel * Math
                .ceil(monstro.agilidade * pesosDano.getPesoAgilidade() + monstro.forca * pesosDano.getPesoForca());
        return dano;
    }

    // calcula o dano da habilidade grito atordoante do monstro
    public double calcularGritoAtordoante(int nivel, Monstro monstro) {
        pesosDano = monstro.pesosDanoGritoAtordoante();
        double dano = nivel * Math
                .ceil(monstro.forca * pesosDano.getPesoForca() + monstro.agilidade * pesosDano.getPesoAgilidade());
        return dano;
    }

    public String getNome() {
        return nome;
    }

    public int getTempo() {
        return tempo;
    }

    public boolean isAfetaTodos() {
        return afetaTodos;
    }

    public boolean isAfetaAmigos() {
        return afetaAmigos;
    }

    public void add(Habilidade habilidade) {
    }

    public int getID() {
        return ID;
    }

    public PesosDeAtributos getPesosMana() {
        return pesosMana;
    }

    public double getManaNecessaria(String nomeHabilidade, int nivel, Personagem personagem) {
        if (nomeHabilidade.equals("Flecha Encantada") && personagem.getClasse() instanceof Arqueiro) {
            return calcularManaNecessariaFlecha(nivel, (Arqueiro) personagem.getClasse());
        } else if (nomeHabilidade.equals("Espada Flamejante") && personagem.getClasse() instanceof Guerreiro) {
            return calcularManaNecessariaEspada(nivel, (Guerreiro) personagem.getClasse());
        } else if (nomeHabilidade.equals("Enfraquecer") && personagem.getClasse() instanceof Mago) {
            return calcularManaNecessariaEnfra(nivel, (Mago) personagem.getClasse());
        } else if (nomeHabilidade.equals("Cura Amigo") && personagem.getClasse() instanceof Mago) {
            return calcularManaNecessariaCura(nivel, (Mago) personagem.getClasse());
        } else {
            // Retorna um valor padrão caso a habilidade não seja reconhecida
            return 0.0; // Ou lance uma exceção informando que a habilidade não foi encontrada
        }
    }

    public void setManaNecessaria(double manaNecessaria) {
    }

}

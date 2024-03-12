package classes;

import java.util.ArrayList;

public class Mago extends Classe {
    private PesosDeAtributos pesoDano;
    private PesosDeAtributos pesoMana;
    private ArrayList<Habilidade> habilidades;

    public Mago() {
        super(1, 2, 3, new ArrayList<>());
        this.habilidades = new ArrayList<>();
        adicionarHabilidades();
    }

    private void adicionarHabilidades() {
        Habilidade socar = new Habilidade("Socar", pesoDano, pesoMana, 2, false, false, 1);
        Habilidade enfraquecer = new Habilidade("Enfraquecer", pesoDano, pesoMana, 5, false, false, 2);
        Habilidade curaAmigo = new Habilidade("Cura amigo", pesoDano, pesoMana, 4, true, false, 3);

        habilidades.add(socar);
        habilidades.add(enfraquecer);
        habilidades.add(curaAmigo);
    }

    public PesosDeAtributos pesosDanoSocar() {
        pesoDano = new PesosDeAtributos(0.1, 0.1, 0.0);
        return pesoDano;
    }

    public PesosDeAtributos pesosDanoEnfraquecer() {
        pesoDano = new PesosDeAtributos(0.3, 0.2, 0.5);
        return pesoDano;
    }

    public PesosDeAtributos pesosManaEnfraquecer() {
        pesoMana = new PesosDeAtributos(0.0, 0.0, 0.5);
        return pesoMana;
    }

    public PesosDeAtributos pesosDanoCurarAmigo() {
        pesoDano = new PesosDeAtributos(0.5, 0.2, 0.8);
        return pesoDano;
    }

    public PesosDeAtributos pesosManaCurarAmigo() {
        pesoMana = new PesosDeAtributos(0.0, 0.0, 0.7);
        return pesoMana;
    }

    public Habilidade getHabilidade(String nome) {
        for (Habilidade habilidade : habilidades) {
            if (habilidade.getNome().equals(nome)) {
                return habilidade;
            }
        }
        return null;
    }

    public ArrayList<Habilidade> getHabilidades() {
        return habilidades;
    }

}

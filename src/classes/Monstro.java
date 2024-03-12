package classes;

import java.util.ArrayList;

public class Monstro extends Classe {
    private PesosDeAtributos pesoDano;
    private PesosDeAtributos pesoMana;
    private ArrayList<Habilidade> habilidades;

    public Monstro() {
        super(4, 1, 0, new ArrayList<>());
        this.habilidades = new ArrayList<>();
        adicionarHabilidades();
    }

    private void adicionarHabilidades() {
        Habilidade socar = new Habilidade("Socar", pesoDano, pesoMana, 5, false, false, 1);
        Habilidade chutar = new Habilidade("Chutar", pesoDano, pesoMana, 8, false, false, 2);
        Habilidade gritoAtordoante = new Habilidade("Grito Atordoante", pesoDano, pesoMana, 6, false, true, 3);

        habilidades.add(socar);
        habilidades.add(chutar);
        habilidades.add(gritoAtordoante);
    }

    public PesosDeAtributos pesosDanoSocar() {
        pesoDano = new PesosDeAtributos(0.8, 0.4, 0.0);
        return pesoDano;
    }

    public PesosDeAtributos pesosDanoChutar() {
        pesoDano = new PesosDeAtributos(1.0, 0.5, 0.0);
        return pesoDano;
    }

    public PesosDeAtributos pesosDanoGritoAtordoante() {
        pesoDano = new PesosDeAtributos(0.4, 0.2, 0.0);
        return pesoDano;
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

package classes;

import java.util.ArrayList;

public class Arqueiro extends Classe {
    private PesosDeAtributos pesoDano;
    private PesosDeAtributos pesoMana;
    private ArrayList<Habilidade> habilidades;

    public Arqueiro() {
        super(1, 2, 3, new ArrayList<>());
        this.habilidades = new ArrayList<>();
        adicionarHabilidades();
    }

    private void adicionarHabilidades() {
        Habilidade socar = new Habilidade("Socar", pesoDano, pesoMana, 3, false, false, 1);
        Habilidade atirarFlecha = new Habilidade("Atirar Flecha", pesoDano, pesoMana, 4, false, false, 2);
        Habilidade flechaEncatada = new Habilidade("Flecha Encantada", pesoDano, pesoMana, 7, false, false, 3);

        habilidades.add(socar);
        habilidades.add(atirarFlecha);
        habilidades.add(flechaEncatada);
    }

    public PesosDeAtributos pesosDanoSocar() {
        pesoDano = new PesosDeAtributos(0.3, 0.1, 0.0);
        return pesoDano;
    }

    public PesosDeAtributos pesosDanoAtirarFlecha() {
        pesoDano = new PesosDeAtributos(0.3, 0.5, 0.0);
        return pesoDano;
    }

    public PesosDeAtributos pesosDanoFlechaEncantada() {
        pesoDano = new PesosDeAtributos(0.3, 0.5, 0.4);
        return pesoDano;
    }

    public PesosDeAtributos pesosManaFlechaEncantada() {
        pesoMana = new PesosDeAtributos(0.0, 0.0, 0.2);
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

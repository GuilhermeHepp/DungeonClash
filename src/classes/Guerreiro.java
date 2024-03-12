package classes;

import java.util.ArrayList;

public class Guerreiro extends Classe {
    private PesosDeAtributos pesoDano;
    private PesosDeAtributos pesoMana;
    private ArrayList<Habilidade> habilidades;

    public Guerreiro() {
        super(4, 1, 1, new ArrayList<>());
        this.habilidades = new ArrayList<>();
        adicionarHabilidades();
    }

    private void adicionarHabilidades() {
        Habilidade socar = new Habilidade("Socar", pesoDano, pesoMana, 4, false, false, 1);
        Habilidade golpeDeEspada = new Habilidade("Golpe de Espada", pesoDano, pesoMana, 5, false, false, 2);
        Habilidade espadaFlamejante = new Habilidade("Espada Flamejante", pesoDano, pesoMana, 7, false, false, 3);

        habilidades.add(socar);
        habilidades.add(golpeDeEspada);
        habilidades.add(espadaFlamejante);
    }

    public PesosDeAtributos pesosDanoSocar() {
        pesoDano = new PesosDeAtributos(0.3, 0.1, 0.0);
        return pesoDano;
    }

    public PesosDeAtributos pesosDanoGolpeDeEspada() {
        pesoDano = new PesosDeAtributos(0.7, 0.3, 0.0);
        return pesoDano;
    }

    public PesosDeAtributos pesosDanoEspadaFlamejante() {
        pesoDano = new PesosDeAtributos(0.3, 0.5, 0.4);
        return pesoDano;
    }

    public PesosDeAtributos pesosManaEspadaFlamejante() {
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

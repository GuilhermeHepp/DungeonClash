package classes;

import java.util.ArrayList;

abstract class Classe {

    protected float forca;
    protected float agilidade;
    protected float inteligencia;
    protected ArrayList<Habilidade> habilidades = new ArrayList<>();

    // Metodo contrutor de qualquer classe que extende a classe classe
    public Classe(float forca, float agilidade, float inteligencia, ArrayList<Habilidade> habilidades) {
        this.forca = forca;
        this.agilidade = agilidade;
        this.inteligencia = inteligencia;
        this.habilidades = new ArrayList<>();

    }

    // metodo para adicionar habilidade na lista de habilidades
    public void adicionarHabilidades(Habilidade habilidade) {
        habilidade.add(habilidade);
    }

    public float getForca() {
        return forca;
    }

    public float getAgilidade() {
        return agilidade;
    }

    public float getInteligencia() {
        return inteligencia;
    }

    public void setForca(float forca) {
        this.forca = forca;
    }

    public void setAgilidade(float agilidade) {
        this.agilidade = agilidade;
    }

    public void setInteligencia(float inteligencia) {
        this.inteligencia = inteligencia;
    }
}

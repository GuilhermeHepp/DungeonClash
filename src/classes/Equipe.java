package classes;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    private ArrayList<Personagem> membros;
    private int proximoID;
    private boolean suaVezDeAtacar;

    public Equipe() {
        this.membros = new ArrayList<>();
        this.proximoID = 0;
        this.suaVezDeAtacar = true;
    }

    // adiciona um personagem a equipe
    public void adicionarEquipe(Personagem personagem) {
        membros.add(personagem);
    }

    public Personagem buscarPersonagem(String nome) {
        for (Personagem personagem : membros) {
            if (personagem.getNome().equals(nome)) {
                return personagem;
            }
        }
        return null; // Retorna null se o personagem não for encontrado
    }

    public List<Personagem> getMembros() {
        return membros;
    }

    public int calcularPontosExperiencia() {
        int totalPontosExperiencia = 0;
        for (Personagem personagem : membros) {
            totalPontosExperiencia += personagem.getPE();
        }
        return totalPontosExperiencia;
    }

    // Método para determinar o próximo atacante (simplificado para retornar o
    // primeiro da lista)
    public Personagem determinarProximoAtacante() {
        if (!membros.isEmpty()) {
            for (Personagem membro : membros) {
                if (membro.getID() == proximoID) { // Procura pelo próximo ID na lista de membros
                    proximoID = (proximoID + 1) % membros.size(); // Atualiza o próximo ID para o próximo atacante
                    return membro; // Retorna o próximo atacante encontrado
                }
            }
        }
        return null; // Retorna null se a equipe estiver vazia ou se nenhum atacante for encontrado
    }

    // Método para atualizar o tempo de espera de todos os personagens na equipe
    public void atualizarTempoEspera() {
        for (Personagem personagem : membros) {
            int novoTempoEspera = personagem.getTempoEspera() - 1;
            personagem.setTempoEspera(Math.max(novoTempoEspera, 0)); // Garante que o tempo de espera não seja negativo
        }
    }

    public boolean isEmpty() {
        return membros.isEmpty();
    }

    public boolean éSuaVezDeAtacar() {
        return suaVezDeAtacar;
    }

    public void setSuaVezDeAtacar(boolean suaVezDeAtacar) {
        this.suaVezDeAtacar = suaVezDeAtacar;
    }

    public void resetarTempoEspera() {
        for (Personagem personagem : membros) {
            personagem.setTempoEspera(0);
        }
    }
}

package br.com.alunonline.api.service;

import br.com.alunonline.api.model.Disciplina;
import br.com.alunonline.api.repository.DisciplinaReposiitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaReposiitory disciplinaReposiitory;

    public void criarDisciplina(Disciplina disciplina){
        disciplinaReposiitory.save(disciplina);
    }

    public List<Disciplina> listarTodasDisciplinas() {
        return disciplinaReposiitory.findAll();
    }

    public Optional<Disciplina> disciplinaPorId(Long id) {
        return disciplinaReposiitory.findById(id);
    }

    public void deletarDisciplinaPorId(Long id){
        disciplinaReposiitory.deleteById(id);
    }

    public void atualizarDisciplinaPorId(Long id, Disciplina disciplinaEditada){
        disciplinaEditada.setId(id);
        disciplinaReposiitory.save(disciplinaEditada);
    }
}

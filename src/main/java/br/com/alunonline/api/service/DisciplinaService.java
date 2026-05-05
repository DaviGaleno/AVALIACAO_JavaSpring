package br.com.alunonline.api.service;

import br.com.alunonline.api.model.Disciplina;
import br.com.alunonline.api.repository.DisciplinaReposiitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaReposiitory disciplinaReposiitory;

    public void criarDisciplina(Disciplina disciplina){
        disciplinaReposiitory.save(disciplina);
    }
}

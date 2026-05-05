package br.com.alunonline.api.repository;

import br.com.alunonline.api.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaReposiitory extends JpaRepository<Disciplina, Long> {

}

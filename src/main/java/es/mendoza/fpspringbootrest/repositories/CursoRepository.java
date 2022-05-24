package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}

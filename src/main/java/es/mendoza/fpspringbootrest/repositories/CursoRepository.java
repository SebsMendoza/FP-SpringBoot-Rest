package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    //Buscamos los cursos por su nombre
    List<Curso> findByNombre(String nombre);

    List<Curso> findByNombreContainsIgnoreCase(String nombre);

    Page<Curso> findByNombreContainsIgnoreCase(String nombre, Pageable pageable);
}

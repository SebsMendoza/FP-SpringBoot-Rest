package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Alumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    //Buscamos los alumnos por su nombre
    List<Alumno> findByNombre(String nombre);

    List<Alumno> findByNombreContainsIgnoreCase(String nombre);

    Page<Alumno> findByNombreContainsIgnoreCase(String nombre, Pageable pageable);
}

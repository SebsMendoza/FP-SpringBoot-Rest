package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}

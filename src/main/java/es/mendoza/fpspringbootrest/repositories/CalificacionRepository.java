package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
}
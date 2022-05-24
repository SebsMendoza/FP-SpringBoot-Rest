package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {
}

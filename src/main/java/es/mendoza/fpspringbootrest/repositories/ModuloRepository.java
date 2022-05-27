package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    //Buscamos los módulos por su nombre
    List<Modulo> findByNombre(String nombre);

    List<Modulo> findByNombreContainsIgnoreCase(String nombre);
}

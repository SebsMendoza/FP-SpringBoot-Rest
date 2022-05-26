package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.models.Curso;
import es.mendoza.fpspringbootrest.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoRestController {
    @Autowired
    CursoRepository cursoRepository;

    @GetMapping("/prueba")
    public String test() {
        return "Prueba correcta";
    }

    @GetMapping("/cursos")
    public ResponseEntity<List<Curso>> findAll() {
        List<Curso> cursos = cursoRepository.findAll();
        if (cursos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay cursos encontrados");
        } else {
            return ResponseEntity.ok(cursos);
        }
    }
}

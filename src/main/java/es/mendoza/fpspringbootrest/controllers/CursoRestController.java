package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curso")
public class CursoRestController {
    @Autowired
    CursoRepository cursoRepository;

    @GetMapping("/prueba")
    public String test() {
        return "Prueba correcta";
    }
}

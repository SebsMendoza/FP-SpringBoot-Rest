package es.mendoza.fpspringbootrest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double nota;
    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;
    @ManyToOne
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;
}

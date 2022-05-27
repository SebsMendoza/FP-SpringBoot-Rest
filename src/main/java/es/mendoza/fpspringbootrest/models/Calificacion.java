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
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Alumno alumno;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Modulo modulo;
}

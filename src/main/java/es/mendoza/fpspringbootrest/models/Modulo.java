package es.mendoza.fpspringbootrest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int año;
    private String nombre;
    @Column(unique = true)
    private String siglas;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "num_curso")
    private Curso curso;
    private LocalDateTime createdAt;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "modulo", cascade = CascadeType.REMOVE)
    private Set<Calificacion> notas;
}

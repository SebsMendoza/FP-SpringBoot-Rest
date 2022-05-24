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
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String siglas;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "curso", cascade = CascadeType.PERSIST)
    private Set<Modulo> modulos;
    private LocalDateTime createdAt;
}

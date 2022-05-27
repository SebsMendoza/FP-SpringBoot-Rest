package es.mendoza.fpspringbootrest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


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
    private int anio;
    private String nombre;
    @Column(unique = true)
    private String siglas;
    private LocalDateTime createdAt;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "modulo", cascade = CascadeType.REMOVE)
    private Set<Calificacion> notas;
}

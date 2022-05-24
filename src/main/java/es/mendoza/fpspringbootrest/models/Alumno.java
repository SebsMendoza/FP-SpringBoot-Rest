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
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String correo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imagen;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "alumno", cascade = CascadeType.REMOVE)
    private Set<Calificacion> notas;
}

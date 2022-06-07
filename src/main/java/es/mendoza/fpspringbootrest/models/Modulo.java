package es.mendoza.fpspringbootrest.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int anio;
    @NotNull(message = "Nombre no puede ser nulo")
    private String nombre;
    @NotNull(message = "Siglas no puede ser nulo")
    @Column(unique = true)
    private String siglas;
    @CreatedDate
    private String createdAt = LocalDateTime.now().toString();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "modulo", cascade = CascadeType.ALL)
    private Set<Calificacion> notas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonManagedReference
    public Set<Calificacion> getNotas() {
        return notas;
    }

    public void setNotas(Set<Calificacion> notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "Modulo{" +
                "id=" + id +
                ", anio=" + anio +
                ", nombre='" + nombre + '\'' +
                ", siglas='" + siglas + '\'' +
                ", createdAt=" + createdAt +
                ", notas=" + notas +
                '}';
    }
}

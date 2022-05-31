package es.mendoza.fpspringbootrest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "modulo", cascade = CascadeType.REMOVE)
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

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

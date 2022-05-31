package es.mendoza.fpspringbootrest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Builder
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String correo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alumno", cascade = CascadeType.ALL)
    private Set<Calificacion> notas;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imagen;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Set<Calificacion> getNotas() {
        return notas;
    }

    public void setNotas(Set<Calificacion> notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", imagen='" + imagen + '\'' +
                ", notas=" + notas +
                '}';
    }
}

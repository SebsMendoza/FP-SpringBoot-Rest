package es.mendoza.fpspringbootrest.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank(message = "Nombre no puede estar vacío")
    private String nombre;
    @Email(regexp = ".*@.*\\..*", message = "Email debe ser un email valido")
    @Column(unique = true)
    private String correo;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "alumno", cascade = CascadeType.REMOVE)
    private Set<Calificacion> notas;
    @CreatedDate
    private String createdAt = LocalDateTime.now().toString();
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
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

    @JsonManagedReference(value = "alumno-nota")
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

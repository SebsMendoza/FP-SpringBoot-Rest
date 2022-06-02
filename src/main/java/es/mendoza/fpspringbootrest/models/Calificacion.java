package es.mendoza.fpspringbootrest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double nota;
    @ManyToOne
    @JoinColumn(name = "id_alumno", referencedColumnName = "id", nullable = false)
    private Alumno alumno;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_modulo", referencedColumnName = "id", nullable = false)
    private Modulo modulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @JsonBackReference
    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    @JsonBackReference
    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    @Override
    public String toString() {
        return "Calificacion{" +
                "id=" + id +
                ", nota=" + nota +
                ", alumno=" + alumno +
                ", modulo=" + modulo +
                '}';
    }
}

package model;

import exceptions.CocheException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Coche {

    @XmlAttribute
    private String matricula;

    @XmlElement
    private String marca;

    @XmlElement
    private String modelo;

    @XmlElement
    private List<Equipamiento> equipamiento;

    public Coche(String matricula, String marca, String modelo, List<Equipamiento> equipamiento) throws CocheException {
        setMatricula(matricula);
        setMarca(marca);
        setModelo(modelo);
        setEquipamiento(equipamiento);
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) throws CocheException {
        if (matricula == null || matricula.isBlank()) {
            throw new CocheException("El coche debe tener matricula");
        }

        this.matricula = matricula.toUpperCase(); //la matricula se guarda en mayusculas
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) throws CocheException {
        if (marca == null || marca.isBlank()) {
            throw new CocheException("Se debe indicar la marca del coche");
        }

        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) throws CocheException {
        if (modelo == null || modelo.isBlank()) {
            throw new CocheException("Se debe indicar el modelo del coche");
        }
        this.modelo = modelo;
    }

    public List<Equipamiento> getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(List<Equipamiento> equipamiento) {
        this.equipamiento = equipamiento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coche coche = (Coche) o;
        return Objects.equals(matricula, coche.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(matricula);
    }
}

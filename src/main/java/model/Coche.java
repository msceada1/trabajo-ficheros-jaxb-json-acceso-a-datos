package model;

import exceptions.CocheException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Coche {

    @XmlAttribute
    private String matricula;

    @XmlElement
    private String marca;

    @XmlElement
    private String modelo;

    public Coche(String matricula, String marca, String modelo) throws CocheException {
        setMatricula(matricula);
        setMarca(marca);
        setModelo(modelo);
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) throws CocheException {
        if (matricula == null || matricula.isBlank()) {
            throw new CocheException("El coche debe tener matricula");
        }

        this.matricula = matricula;
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
}

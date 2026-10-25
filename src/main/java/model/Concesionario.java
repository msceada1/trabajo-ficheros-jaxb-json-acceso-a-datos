package model;

import exceptions.MatriculaDuplicadaException;
import jakarta.xml.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@XmlRootElement(name = "concesionario-de-coches")
@XmlAccessorType(XmlAccessType.FIELD)
public class Concesionario {

    @XmlElementWrapper(name = "listado-coches")
    @XmlElement(name = "coche")
    private Set<Coche> coches;

    public Concesionario() {
        this.coches = new LinkedHashSet<>();
    }

    public Set<Coche> getCoches() {
        return coches;
    }

    public void setCoches(Set<Coche> coches) {
        this.coches = coches;
    }

    /**
     * metodo encargado de aniadir un coche si la matricula no se encontraba registrada en el
     * concesionario
     *
     * @param c el coche a aniadir
     * @throws MatriculaDuplicadaException si la matricula ya estaba registrada
     */
    public void addCoche(Coche c) throws MatriculaDuplicadaException {
        if (!this.coches.add(c)) {
            throw new MatriculaDuplicadaException("Error: La matricula ya existe en el concesionario");
        }
    }

    /**
     * metodo encargado de ordenar por matricula el los coches del concesionario
     */
    public void ordenarPorMatricula() {
        this.coches = this.coches.stream().sorted(Comparator.comparing(Coche::getMatricula)).
                collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void borrarRegistro(String matricula) {
        Coche cocheABorrar = (Coche) this.coches.stream().filter(c -> c.getMatricula().equalsIgnoreCase(matricula));
        if (!this.coches.remove(cocheABorrar)) {

        }
    }
}

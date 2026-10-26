package model;

import exceptions.CocheException;
import exceptions.MatriculaException;
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
     * @throws MatriculaException si la matricula ya estaba registrada
     */
    public void addCoche(Coche c) throws MatriculaException {
        if (!this.coches.add(c)) {
            throw new MatriculaException("Error: La matricula ya existe en el concesionario");
        }
    }

    /**
     * metodo encargado de ordenar por matricula el los coches del concesionario
     */
    public void ordenarPorMatricula() {
        this.coches = this.coches.stream().sorted(Comparator.comparing(Coche::getMatricula)).
                collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Metodo encargado de borrar un registro por la matricula de un coche
     *
     * @param matricula la matricula a borrar
     * @throws MatriculaException si la matricula no se encontraba registrada
     */
    public void borrarRegistro(String matricula) throws MatriculaException {
        Coche c = encuentraRegistroPorMatricula(matricula);
        if (c == null) {
            throw new MatriculaException("La matricula no ha sido encontrada");
        }
        this.coches.remove(c);
    }

    /**
     * metodo encargado de editar los campos de un coche excepto su matricula
     * @param matricula la matricula del coche
     * @param marca la marca a modificar del coche
     * @param modelo el modelo a modificar del coche
     * @param equipamiento el equipamiento a modificar del coche
     * @throws MatriculaException
     * @throws CocheException
     */
    public void editarRegistro(String matricula, String marca, String modelo, List<Equipamiento> equipamiento) throws MatriculaException, CocheException {
        Coche c = encuentraRegistroPorMatricula(matricula);
        if (c == null) {
            throw new MatriculaException("La matricula no ha sido encontrada");
        }

        c.setMarca(marca);
        c.setModelo(modelo);
        c.setEquipamiento(equipamiento);
    }

    /**
     * Metodo encargado de devolver buscando por su matricula
     *
     * @param matricula la matricula del coche que se desea obtener
     * @return el objeto Coche si en el registro coincide la matricula o null en caso de que no esté la matricula
     */
    private Coche encuentraRegistroPorMatricula(String matricula) {
        return this.coches.stream().filter(c -> c.getMatricula().equalsIgnoreCase(matricula.toUpperCase())).findAny()
                .orElse(null);
    }
}

import exceptions.CocheException;
import exceptions.MatriculaException;
import jakarta.xml.bind.*;
import model.Coche;
import model.Concesionario;
import model.Equipamiento;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Principal {


    static void main() throws CocheException, MatriculaException, JAXBException {
        Equipamiento e1 = new Equipamiento();
        e1.setEquipamiento(List.of("abrelata", "tienda de camapaña"));




        Coche c1 = new Coche();
        c1.setEquipamiento(e1);
        c1.setMatricula("1234abc");
        c1.setMarca("maclaren");
        c1.setModelo("formula 1");


        Concesionario concesionario = new Concesionario();

        concesionario.addCoche(c1);

        // 2. Crear contexto y Marshaller
        JAXBContext context = JAXBContext.newInstance(Concesionario.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        Path ruta = Path.of("data", "concesionario.xml");

        marshaller.marshal(concesionario, ruta.toFile());

        try {
            // 1. Crear contexto y Unmarshaller
            context = JAXBContext.newInstance(Concesionario.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();



            // 2. Leer el fichero y convertirlo a objeto
            Concesionario biblioDesdeXML = (Concesionario) unmarshaller.unmarshal(ruta.toFile());

            System.out.println("Biblioteca cargada: " + biblioDesdeXML.getCoches());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}

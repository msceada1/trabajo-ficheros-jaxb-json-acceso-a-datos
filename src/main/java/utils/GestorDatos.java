package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.AppException;
import exceptions.MatriculaException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.Coche;
import model.Concesionario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GestorDatos {

    private Path rutaBddXml;
    private Gson gson;
    private Path rutaJsonConcesionario;
    private Path rutaJsonCoche;

    public GestorDatos() throws AppException {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        cargarRutas();
    }

    private void cargarRutas() throws AppException {
        PropertiesReader pr = PropertiesReader.getInstance();
        this.rutaBddXml = Path.of(pr.get("ruta_bdd_concesionario"));
        this.rutaJsonConcesionario = Path.of(pr.get("ruta_bdd_json"));
        this.rutaJsonCoche = Path.of(pr.get("ruta_bdd_coche"));
    }

    /**
     * Metodo encargado de guardar el registro al fichero de base de datos en Xml
     *
     * @param cn el concesionario donde se guardan los registros
     */
    public void guardarRegistroAXml(Concesionario cn) {
        try {
            JAXBContext context = JAXBContext.newInstance(Concesionario.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(cn, this.rutaBddXml.toFile());
        } catch (JAXBException e) {
            System.out.println("error " + e.getMessage());
        }
    }

    /**
     * metodo encargado de pasar de xml a objeto de tipo Concesionario
     */
    public Concesionario deXmlAObjeto() {
        Concesionario concesionarioActualizado = null;

        try {
            // 1. Crear contexto y Unmarshaller
            JAXBContext context = JAXBContext.newInstance(Concesionario.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // 2. Leer el fichero y convertirlo a objeto
            concesionarioActualizado = (Concesionario) unmarshaller.unmarshal(this.rutaBddXml.toFile());

            System.out.println("Concesionario cargado:");

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return concesionarioActualizado;
    }

    /**
     * metodo encargado de exportar la base de datos a un fichero json
     *
     * @param concesionario el concesionario donde se guardan los coches
     */
    public void exportarConcesionarioJson(Concesionario concesionario) {
        try (BufferedWriter bw = Files.newBufferedWriter(this.rutaJsonConcesionario)) {
            gson.toJson(concesionario, bw);
        } catch (IOException e) {
            System.out.println("Error al exportar el concesionario a JSON: " + e.getMessage());
        }
    }

    /**
     * Busca un coche por matrícula y exporta solo ese objeto a un fichero JSON.
     *
     * @param matricula     La matrícula exacta a buscar.
     * @param concesionario El concesionario donde buscarlo.
     */
    public void exportarCocheJson(String matricula, Concesionario concesionario) throws MatriculaException {
        Coche cocheEncontrado = concesionario.getCoches().stream().filter(c -> c.getMatricula().equals(matricula))
                .findAny().orElse(null);

        if (cocheEncontrado == null) {
            throw new MatriculaException("La matricula no ha sido encontrada");
        }

        try (BufferedWriter bw = Files.newBufferedWriter(this.rutaJsonCoche)) {
            gson.toJson(cocheEncontrado, bw);
        } catch (IOException e) {
            System.out.println("Error al exportar el coche a JSON: " + e.getMessage());
        }
    }

    public Concesionario importarConcesionarioJson() {
        Concesionario concesionarioImportado = null;
        try (BufferedReader br = Files.newBufferedReader(this.rutaJsonConcesionario)) {
            concesionarioImportado = gson.fromJson(br, Concesionario.class);
            System.out.println("Concesionario importado desde JSON con éxito.");
            return concesionarioImportado;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return concesionarioImportado; //devuelvo nulo si salta la excepcion
        }
    }

    /**
     * metodo encargado de leer el fichero csv y exportarlo
     *
     * @param cn el cocnesionario donde se exporta el csv
     */
    public void cargarCSV(Concesionario cn) {
        try (BufferedReader br = Files.newBufferedReader(this.rutaCSV)) {
            String linea;
            boolean primeraLinea = true;

            //hago que no lea la primera linea para que no inserte matricula modelo marca
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                String[] campos = linea.split(";");

                try {
                    Coche cocheCSV = new Coche();

                    cocheCSV.setMatricula(campos[0]);
                    cocheCSV.setMarca(campos[1]);
                    cocheCSV.setModelo(campos[2]);

                    //si hay extras los meto en otro array
                    if (campos.length == 4) {
                        String[] extras = campos[3].split("\\|");
                        Equipamiento equipamiento = new Equipamiento();
                        equipamiento.setEquipamiento(Arrays.asList(extras));

                        cocheCSV.setEquipamiento(equipamiento);
                    }

                    cn.addCoche(cocheCSV);
                } catch (MatriculaException | CocheException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void generarInformeTxt() {

    }
}

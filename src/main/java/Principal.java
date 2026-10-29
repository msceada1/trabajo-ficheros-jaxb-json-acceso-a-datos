import exceptions.AppException;
import exceptions.CocheException;
import exceptions.MatriculaException;
import model.Coche;
import model.Concesionario;
import model.Equipamiento;
import utils.GestorDatos;
import utils.MiEntradaSalida;

import java.util.ArrayList;
import java.util.List;

public class Principal {


    static void main() throws AppException {
        Concesionario concesionario = new Concesionario();
        GestorDatos gestorDatos = new GestorDatos();
        programa(concesionario, gestorDatos);
    }

    /**
     * menu generado con gemini. Este es el menu de opciones que se mostrara al usuario
     */
    private static void menu() {
        System.out.println("\n=============================================================");
        System.out.println("                 GESTIÓN DEL CONCESIONARIO                   ");
        System.out.println("=============================================================");
        System.out.println("  1. Importar coches desde archivo CSV");
        System.out.println("  2. Insertar un nuevo coche");
        System.out.println("  3. Ordenar coches por matrícula");
        System.out.println("  4. Borrar un coche por matrícula");
        System.out.println("  5. Modificar un coche por matrícula");
        System.out.println("  6. Exportar base de datos completa a JSON");
        System.out.println("  7. Exportar un único coche a JSON (por matrícula)");
        System.out.println("  8. Importar concesionario completo desde JSON (sobrescribe XML)");
        System.out.println("  9. Importar un único coche desde JSON");
        System.out.println(" 10. Generar informe resumen (informe_concesionario.txt)");
        System.out.println(" 11. Salir del programa");
        System.out.println("=============================================================");
    }

    /**
     * metodo encargado de crear un cohe
     *
     * @return un coche creado
     * @throws CocheException si los setter no validan
     */
    private static Coche crearCoche() throws CocheException {
        Coche cocheCreado = new Coche();

        cocheCreado.setMatricula(MiEntradaSalida.leerCadena("Indica la matricula:\n"));
        cocheCreado.setMarca(MiEntradaSalida.leerCadena("Indica la marca:\n"));
        cocheCreado.setModelo(MiEntradaSalida.leerCadena("Indica el modelo:\n"));
        cocheCreado.setEquipamiento(generaEquipamiento(MiEntradaSalida.leerEntero("cuantos objetos quieres introducir?")));

        return cocheCreado;
    }

    private static Equipamiento generaEquipamiento(int cantidadEquipamiento) {
        if (cantidadEquipamiento == 0) { //si hay equipamiento se devuelve null
            return null;
        }

        List<String> objetos = new ArrayList<>();

        for (int i = 0; i < cantidadEquipamiento; i++) {
            String objeto = MiEntradaSalida.leerCadena("Introduce el nombre del equipamiento " + (i + 1));
            objetos.add(objeto);
        }

        Equipamiento e = new Equipamiento();
        e.setEquipamiento(objetos);
        return e;
    }

    private static void programa(Concesionario cn, GestorDatos gd) {
        boolean enFuncionamiento = true;

        while (enFuncionamiento) {
            menu();
            int opcion = MiEntradaSalida.leerEnteroEnRango("Que operación deseas hacer?", 1, 11);

            switch (opcion) {
                case 1:
                    gd.cargarCSV(cn);
                    break;
                case 2:
                    try {
                        cn.addCoche(crearCoche());
                        gd.marshalling(cn);
                        System.out.println(gd.unmarshalling());
                    } catch (MatriculaException | CocheException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    cn.ordenarPorMatricula();
                    gd.marshalling(cn);
                    System.out.println(gd.unmarshalling());
                    break;
                case 4:
                    try {
                        cn.borrarRegistro(MiEntradaSalida.leerCadena("Indica la matricula del coche a borrar:\n"));
                        gd.marshalling(cn);
                        System.out.println(gd.unmarshalling());
                        System.out.println("coche borrado con exito");
                    } catch (MatriculaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:

            }
        }
    }
}
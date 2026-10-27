import exceptions.AppException;
import exceptions.CocheException;
import exceptions.MatriculaException;
import model.Coche;
import model.Concesionario;
import utils.GestorDatos;
import utils.MiEntradaSalida;

public class Principal {


    static void main() throws AppException {
        Concesionario concesionario = new Concesionario();
        GestorDatos gestorDatos = new GestorDatos();
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

        return cocheCreado;
    }

    private void programa(Concesionario cn, GestorDatos gd) {
        boolean enFuncionamiento = true;

        while (enFuncionamiento) {
            menu();
            int opcion = MiEntradaSalida.leerEnteroEnRango("Que operación deseas hacer?", 1, 11);

            switch (opcion) {
                case 1:
                    gd.cargarCSV(cn);
                case 2:
                    try {
                        cn.addCoche(crearCoche());
                        gd.guardarRegistroAXml(cn);
                    } catch (MatriculaException | CocheException e) {
                        System.out.println(e.getMessage());
                    }

                case 3:
                    cn.ordenarPorMatricula();
                    gd.guardarRegistroAXml(cn);
                case 4:
                    try {
                        cn.borrarRegistro(MiEntradaSalida.leerCadena("Indica la matricula del coche a borrar:\n"));
                        gd.guardarRegistroAXml(cn);
                    } catch (MatriculaException e) {
                        System.out.println(e.getMessage());
                    }
                case 5:
            }
        }
    }
}
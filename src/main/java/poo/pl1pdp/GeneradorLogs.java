package poo.pl1pdp;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GeneradorLogs {
    private static Logger logger = Logger.getLogger("GalletasLog");                         //Instancia de Logger para almacenar eventos.

    static {
        try {// Configuramos el archivo donde se guardarán los logs.
            FileHandler fileHandler = new FileHandler("Operaciones_galletas.log", true);    //true para generar nuevos logs en vez de sobreescribirlo.
            SimpleFormatter formatter = new SimpleFormatter();                              //Para agregar un formato a los logs.
            fileHandler.setFormatter(formatter);                                            
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);                                             //Evita que imprima en consola.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String mensaje) {
        logger.info(mensaje);
    }
}

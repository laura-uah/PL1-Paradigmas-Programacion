package poo.pl1pdp;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GeneradorLogs {
    private static Logger logger = Logger.getLogger("GalletasLog");

    static {
        try {
            // Configuramos el archivo donde se guardarán los logs
            FileHandler fileHandler = new FileHandler("Operaciones_galletas.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Evita que imprima en consola
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String mensaje) {
        logger.info(mensaje);
    }
}

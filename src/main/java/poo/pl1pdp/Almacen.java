
package poo.pl1pdp;

import java.text.SimpleDateFormat;
import java.util.Date;

class Almacen {
    private static int capacidadAlmacen;
    private int galletasAlmacenadas;
    
    public Almacen(int capacidadAlmacen) {
        this.capacidadAlmacen = capacidadAlmacen;
        this.galletasAlmacenadas = 0;
    }
    
    // Método para obtener la marca de tiempo
    private String obtenerMarcaDeTiempo() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }
    
    public synchronized boolean almacenarGalletas (int paqueteGalletas, String id_empaquetador) {
        while (galletasAlmacenadas + paqueteGalletas > capacidadAlmacen) {
            try {
                System.out.println(obtenerMarcaDeTiempo() + " - " + id_empaquetador + " espera porque el almacén está lleno.");
                wait();  // El empaquetador espera hasta que haya espacio
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        galletasAlmacenadas += paqueteGalletas;
        System.out.println(obtenerMarcaDeTiempo() + " - " + id_empaquetador + " almacena " + paqueteGalletas + " galletas. Total en almacén: " + galletasAlmacenadas);
        notifyAll();  // Notifica a los empaquetadores en espera de que se pueden almacenar más galletas
        return true;
    }
    
    
    
    
    //BOTÓN COMER GALLETAS
}

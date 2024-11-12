
package poo.pl1pdp;

import java.text.SimpleDateFormat;
import java.util.Date;



class HiloEmpaquetador extends Thread {
    private String id_empaquetador;
    private Almacen almacen;
    private HiloHorno horno;
    
    //Constructor
    public HiloEmpaquetador (String id_empaquetador, Almacen almacen, HiloHorno horno) {
        this.id_empaquetador = id_empaquetador;
        this.almacen = almacen;
        this.horno = horno;
    }

    // Función para el formato de fecha
    private String obtenerMarcaDeTiempo() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }
    public void run() {
        try {
            int cantidad = 20;
            int numGalletasRecolectadas = 0;
            int numGalletasEmpaquetar = 100;
            
            while (true) {
                /*synchronized (horno) {
                    while (!horno.estaVacio()) {
                        System.out.println(id_empaquetador + " espera a que el horno esté vacio para retirar las galletas. ");
                        horno.wait();
                    }
                }*/
                
                
                synchronized (horno) {
                    //while (!horno.estaVacio()) {
                        if (horno.retirarGalletasHorno(cantidad, id_empaquetador)) {
                            numGalletasRecolectadas += cantidad;
                            int tiempoRetirarGalletas = 500 + (int)(500*Math.random());
                            Thread.sleep(tiempoRetirarGalletas);

                            //Logs
                            LogHandler.log(obtenerMarcaDeTiempo() + " - " + id_empaquetador + " retira " + cantidad + " galletas del " + horno.getId() + ". Total recolectadas: " + numGalletasRecolectadas);

                            if (numGalletasRecolectadas == numGalletasEmpaquetar) {
                                //Logs
                                LogHandler.log(obtenerMarcaDeTiempo() + " - " + id_empaquetador + " ha empaquetado un lote de " + numGalletasEmpaquetar + " galletas.");
                                synchronized (almacen) {
                                    almacen.almacenarGalletas(numGalletasEmpaquetar, id_empaquetador);
                                }
                                numGalletasRecolectadas -= numGalletasEmpaquetar;
                            }  
                        }

                        if (numGalletasRecolectadas <= numGalletasEmpaquetar) {
                            System.out.println(id_empaquetador + " sigue recolectando galletas. Recolectadas: " + numGalletasRecolectadas);
                        }
                    //}
                    
                }
                
            }                    
        } catch (InterruptedException ie) {
                ie.printStackTrace();
        }        
    } 
}

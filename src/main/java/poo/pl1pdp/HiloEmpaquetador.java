
package poo.pl1pdp;

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
    
    public void run() {
        try {
            int cantidad = 20;
            int numGalletasRecolectadas = 0;
            int numGalletasEmpaquetar = 100;
            
            while (true) {
                if (horno.retirarGalletasHorno(cantidad, id_empaquetador)) {
                    numGalletasRecolectadas += cantidad;
                    int tiempoRetirarGalletas = 500 + (int)(500*Math.random());
                    Thread.sleep(tiempoRetirarGalletas);
                    
                    if (numGalletasRecolectadas == numGalletasEmpaquetar) {
                        System.out.println(id_empaquetador + " ha empaquetado un lote de " + numGalletasEmpaquetar + " galletas.");
                        synchronized (almacen) {
                            almacen.almacenarGalletas(numGalletasEmpaquetar, id_empaquetador);
                        }
                        numGalletasRecolectadas = 0;
                    }  
                }
            }                    
        } catch (InterruptedException ie) {
                ie.printStackTrace();
        }        
    }
    
    
}

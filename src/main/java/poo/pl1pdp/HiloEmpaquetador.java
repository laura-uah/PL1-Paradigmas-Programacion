
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
                synchronized (horno) {
                    horno.retirarGalletasHorno(cantidad, id_empaquetador);
                    int tiempoRetirarGalletas = 500 + (int)(500*Math.random());
                    Thread.sleep(tiempoRetirarGalletas);
                    numGalletasRecolectadas += cantidad;
                    if (numGalletasRecolectadas == numGalletasEmpaquetar) {
                        System.out.println(id_empaquetador + " ha empaquetado un lote de " + numGalletasEmpaquetar + " galletas.");
                    }  
                }
                boolean depositadoA = false;
                                     
                    while(!depositadoA) {
                        synchronized (almacen) {
                            if (almacen.almacenarGalletas(numGalletasEmpaquetar, id_empaquetador)) {
                                depositadoA = true;
                                break;
                            }
                        }
                        if (!depositadoA) {
                            System.out.println(id_empaquetador + " está esperando a que haya sitio en el almacén. ");
                            almacen.wait();
                            //Thread.sleep(500);
                        }
                    }            
            }
            
                
        } catch (InterruptedException ie) {
                ie.printStackTrace();
        }        
    }
    
    
}

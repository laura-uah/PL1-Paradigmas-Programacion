
package poo.pl1pdp;

class HiloHorno extends Thread {
    private String id_horno;
    private int capacidad;
    private int galletasHorno = 0;
    private boolean horneando = false;
    private boolean vacio = true;
    private boolean empaquetando = false;

    
    //Constructor
    public HiloHorno (String id_horno, int capacidad) {
        this.id_horno = id_horno;
        this.capacidad = capacidad;
    }
    
    public boolean estaLleno() {
        return galletasHorno == capacidad;
    }
    
    public boolean estaVacio() {
        return galletasHorno == 0;
    }
    
    public synchronized boolean meterGalletasHorno(int cantidad, String nombreRepostero) {        
        while (!vacio || horneando || empaquetando) {
            try {
                //if (!vacio) {
                    //Logs
                    LogHandler.log(nombreRepostero + " espera a que el " + id_horno + " esté vacío para añadir " + cantidad + " galletas.");
                    wait();
                //}    
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        int espacioDisponible = capacidad - galletasHorno;
        
        if (cantidad > espacioDisponible) {
            int desperdicio = cantidad - espacioDisponible;
            galletasHorno = capacidad;
            //Logs
            LogHandler.log(nombreRepostero + " desperdicia " + desperdicio + " galletas al intentar llenar el " + id_horno);
        } else {
            galletasHorno += cantidad;
        }
        
        System.out.println(nombreRepostero + " deja " + cantidad + " galletas en " + id_horno + ". Total en horno: " + galletasHorno);
        
        if (galletasHorno == capacidad && !horneando) {
            horneando = true;
            vacio = false;
            notifyAll();
        }
        return true;
    }
    
    
    
    public void run() {
        try {
            while (true){
                synchronized (this) {
                    while (galletasHorno < capacidad) {
                        wait();
                    }
                    hornear();
                }
                
                synchronized (this) {
                    horneando = false;
                    empaquetando = true;
                    notifyAll();
                }
                
                synchronized (this) {
                    while (galletasHorno > 0) {
                        wait();
                    }
                    vacio = true;
                }
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    
    private void hornear() {
        try {
            //Logs
            LogHandler.log(id_horno + " está horneando " + capacidad + " galletas.");
            Thread.sleep(8000);
            //Logs
            LogHandler.log(id_horno + " ha terminado de hornear " + capacidad + " galletas.");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    
    public synchronized boolean retirarGalletasHorno(int cantidad, String id_empaquetador) {
        while (galletasHorno < cantidad || horneando) {
            try {
                //if (galletasHorno == 0) {
                    System.out.println(id_empaquetador + " espera para retirar " + cantidad + " galletas del " + id_horno);
                /*} else if (horneando) {
                    System.out.println(id_empaquetador + " espera a que el " + id_horno + " termine de hornear. ");
                }*/
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        galletasHorno  -= cantidad;
        System.out.println(id_empaquetador + " retira " + cantidad + " galletas. Quedan " + galletasHorno + " galletas en el " + id_horno);
        
        if (galletasHorno == 0) {
            empaquetando = false;
            notifyAll();
        }
        return true;
    }  
}

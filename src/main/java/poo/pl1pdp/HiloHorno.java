
package poo.pl1pdp;

class HiloHorno extends Thread {
    private String id_horno;
    private int capacidad;
    private String nombreRepostero;
    private int galletasHorno = 0;
    
    //Constructor
    public HiloHorno (String id_horno, int capacidad) {
        this.id_horno = id_horno;
        this.capacidad = capacidad;
    }
    
    public synchronized boolean meterGalletasHorno(int cantidad, String nombreRepostero) {
        this.nombreRepostero = nombreRepostero;
        int espacioDisponible = capacidad - galletasHorno;
        
        if (cantidad > espacioDisponible) {
            int desperdicio = cantidad - espacioDisponible;
            galletasHorno = capacidad;
            System.out.println(nombreRepostero + " desperdicia "+ desperdicio + " galletas al intentar llenar el " + id_horno);
            System.out.println(nombreRepostero + " deja " + cantidad + " galletas en " + id_horno + ". Total en horno: " + galletasHorno);
            
            notifyAll();
            return true;
        } else {
            galletasHorno += cantidad;
            System.out.println(nombreRepostero + " deja " + cantidad + " galletas en " + id_horno + ". Total en horno: " + galletasHorno);
            if (galletasHorno == capacidad) {
                notifyAll();
            }
            return true;
        }
    }
    
    public synchronized boolean retirarGalletasHorno(int cantidad, String id_empaquetador) {
        while (galletasHorno < cantidad) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        galletasHorno  -= cantidad;
        System.out.println(id_empaquetador + " retira " + cantidad + " galletas. Quedan " + galletasHorno + " galletas en el horno. ");
        notifyAll();
        return true;
    }
    
    public void run() {
        try {
            while (true){
                synchronized (this) {
                    while (galletasHorno < capacidad) {
                        wait();
                    }
                }
                hornear();
                synchronized (this) {
                    galletasHorno = 0;
                    notifyAll();
                }
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    
    private void hornear() {
        try {
            System.out.println(id_horno + " está horneando " + capacidad + " galletas.");
            Thread.sleep(8000);
            System.out.println(id_horno + " ha terminado de hornear " + capacidad + " galletas.");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    
    
    
    
}

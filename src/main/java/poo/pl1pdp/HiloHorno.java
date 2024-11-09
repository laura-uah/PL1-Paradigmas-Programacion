
package poo.pl1pdp;

class HiloHorno extends Thread {
    private String id_horno;
    private int capacidad;
    private int galletasHorno = 0;
    private boolean horneando = false;
    private boolean retirandoGalletas = false;

    
    //Constructor
    public HiloHorno (String id_horno, int capacidad) {
        this.id_horno = id_horno;
        this.capacidad = capacidad;
    }
    
    public synchronized boolean meterGalletasHorno(int cantidad, String nombreRepostero) {        
        while (galletasHorno > 0 || horneando || retirandoGalletas) {
            try {
                if (galletasHorno > 0 || horneando) {
                    System.out.println(nombreRepostero + " espera a que el " + id_horno + " esté vacío para añadir " + cantidad);
                }
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        int espacioDisponible = capacidad - galletasHorno;
        
        if (cantidad > espacioDisponible) {
            int desperdicio = cantidad - espacioDisponible;
            galletasHorno = capacidad;
            System.out.println(nombreRepostero + " desperdicia "+ desperdicio + " galletas al intentar llenar el " + id_horno);

            //notifyAll();
            //return true;
        } else {
            galletasHorno += cantidad;
        }
        
        System.out.println(nombreRepostero + " deja " + cantidad + " galletas en " + id_horno + ". Total en horno: " + galletasHorno);
        
        if (galletasHorno == capacidad) {
            horneando = true;
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
                    retirandoGalletas = true;
                    System.out.println(id_horno + " se ha vaciado despues del horneado. ");
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
    
    public synchronized boolean retirarGalletasHorno(int cantidad, String id_empaquetador) {
        while (galletasHorno < cantidad || horneando) {
            try {
                System.out.println(id_empaquetador + " espera para retirar " + cantidad + " galletas del " + id_horno);
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        System.out.println(id_empaquetador + " retira " + cantidad + " galletas. Quedan " + galletasHorno + " galletas en el " + id_horno);
        galletasHorno  -= cantidad;
        
        if (galletasHorno == 0) {
            notifyAll();
        }
        return true;
    }  
}

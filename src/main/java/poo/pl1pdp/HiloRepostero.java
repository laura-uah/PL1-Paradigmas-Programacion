
package poo.pl1pdp;

class HiloRepostero extends Thread {
    private HiloHorno horno;
    private Cafetera cafetera;
    private String id_repostero;
    
    //Constructor
    public HiloRepostero (String id_repostero, Cafetera cafetera, HiloHorno horno) {
        this.id_repostero = id_repostero;
        this.cafetera = cafetera;
        this.horno = horno;
    }
    
    //Función que cocina un número de galletas entre 37 y 45
    public int cocinarGalletas() {
        int numGalletas = 35 + (int)(9*Math.random());
        return numGalletas;
    }
    
    public void run() {
        try {            
            while (true) {
                int tandas = ((int)(3+(2*Math.random())));
                for (int i = 0; i < tandas; i++) {
                    //Cocinar galletas
                    int numGalletas = cocinarGalletas();
                    
                    //Tiempo que tardan las galletas en cocinarse
                    int tiempoHacerGalletas = ((int)(2000+(2000*Math.random())));
                    Thread.sleep(tiempoHacerGalletas);
                    System.out.println(id_repostero + " produce la tanda " + (i+1) + "/" + tandas + " con " + numGalletas + " galletas.");
                    
                    boolean depositado = false;
                    while(!depositado) {
                        synchronized (horno) {
                            if (horno.meterGalletasHorno(numGalletas, id_repostero)) {
                                depositado = true;
                                break;
                            }
                        }
                        if (!depositado) {
                            System.out.println(id_repostero + " está esperando a un horno disponible. ");
                            Thread.sleep(500);
                        }
                    }
                }
                synchronized (cafetera) {
                    cafetera.prepararCafe(id_repostero);
                }
                //Descansar
                int tiempoDescanso = (int)(3000+6000*Math.random());
                Thread.sleep(tiempoDescanso);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}

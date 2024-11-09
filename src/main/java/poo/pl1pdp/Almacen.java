
package poo.pl1pdp;

class Almacen {
    private static int capacidadAlmacen;
    private int galletasAlmacenadas;
    
    public Almacen(int capacidadAlmacen) {
        this.capacidadAlmacen = capacidadAlmacen;
    }
    
    public synchronized boolean almacenarGalletas (int paqueteGalletas, String id_empaquetador) {
        if (galletasAlmacenadas + paqueteGalletas <= capacidadAlmacen) {
            galletasAlmacenadas += paqueteGalletas;
            System.out.println(id_empaquetador + " almacena " + paqueteGalletas + " galletas. Total en almacén: " + galletasAlmacenadas);
            notifyAll();
        } return true;
    }
    
    
}


package poo.pl1pdp;

class HiloEmpaquetador extends Thread {
    private String id_empaquetador;
    private Almacen almacen;
    
    //Constructor
    public HiloEmpaquetador (String id_empaquetador, Almacen almacen) {
        this.id_empaquetador = id_empaquetador;
        this.almacen = almacen;
    }
}

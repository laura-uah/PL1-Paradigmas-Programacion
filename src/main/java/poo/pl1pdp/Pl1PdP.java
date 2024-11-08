
package poo.pl1pdp;

public class Pl1PdP {

    public static void main(String[] args) {
        // Declarar objetos compartidos
        Cafetera cafetera = new Cafetera();
        Almacen almacen = new Almacen();
        
        // Declarar hilo Horno
        HiloHorno horno = new HiloHorno("Horno1", 200);

        /*
        for (i=0; i<3; i++) {
            Repostero rep = new Repostero(i+1);
        }
        */
        
        // Declarar hilo Repostero
        HiloRepostero rep = new HiloRepostero("Repostero1", cafetera, horno);

        /*
        for (i=0; i<5; i++) {
            Repostero rep = new Repostero(i+1, cafetera);
        }
        */
        
        
        
        // Declarar hilo Empaquetador
        HiloEmpaquetador emp = new HiloEmpaquetador("Empaqutador1", almacen);

        /*
        for (i=0; i<3; i++) {
            HiloEmpaquetador emp = new HiloEmpaquetador(i+1, almacen);
        }
        */
        
        rep.start();
        horno.start();
        emp.start();

    }
}

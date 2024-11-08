
package poo.pl1pdp;

class Cafetera {
    public synchronized void prepararCafe(String id_repostero) {
        try {
            System.out.println(id_repostero + " está preparando café. ");
            Thread.sleep(2000);
            System.out.println(id_repostero + " está descansando mientras se toma un café. ");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}

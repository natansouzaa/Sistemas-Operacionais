package jantar;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Jantar {

	static int[] estado = new int[5];
	static Semaphore mutex = new Semaphore(1);
	
    static Semaphore[] semaforos = new Semaphore[5];

    static Filosofo[] filosofos = new Filosofo[5];

    public static void main(String[] args) {

        // Todos os filosofos iniciam pensando(0).
        for (int i = 0; i < estado.length; i++) {
            estado[i] = 0;
        }

        // Inicializa todos os folósofos.
        filosofos[0] = new Filosofo(0);
        filosofos[1] = new Filosofo(1);
        filosofos[2] = new Filosofo(2);
        filosofos[3] = new Filosofo(3);
        filosofos[4] = new Filosofo(4);
        
        // Informa os talheres que pertecem a cada filosofo.
        for (int i = 0; i < filosofos.length; i++) {            
            System.out.println("Garfo: " + i + " - Filósofo: " + i + " - Garfo: " + (i + 1) % 5);            
        }
        
        System.out.println("");        
        
        // Seta o semáforo de todos os filósofos para 0.
        for (int i = 0; i < semaforos.length; i++) {
            semaforos[i] = new Semaphore(0);
        }

        // Inicia a tentativa de cada filosofo  .            
        for (int i = 0; i < filosofos.length; i++) {
            filosofos[i].start();
        }

        try {
            Thread.sleep(10000);
            System.exit(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(Jantar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
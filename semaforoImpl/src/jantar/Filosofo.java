package jantar;

import jantar.Jantar;

public class Filosofo extends Thread {
    
	int id;	
    // Estados
    final int PENSANDO = 0;
    final int FAMINTO = 1;
    final int COMENDO = 2;
    
    public Filosofo(int id) {
        this.id = id;
    }

    public void comFome() {
        Jantar.estado[this.id] = 1;
        System.out.println("Filósofo: " + getId() + ", estado: Com fome");
    }

    public void come() {
        Jantar.estado[this.id] = 2;
        System.out.println("Filósofo: " + getId() + ", estado: Comendo");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void pensa() {
        Jantar.estado[this.id] = 0;
        System.out.println("Filósofo: " + getId() + ", estado: Pensando");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void soltarTalher() throws InterruptedException {
        Jantar.mutex.acquire();
        pensa();
        // Quando o filósofo soltar os talheres, seus vizinhos podem tentar pega-los.
        Jantar.filosofos[vizinhoEsquerda()].tentarPegarTalher();
        Jantar.filosofos[vizinhoDireita()].tentarPegarTalher();
        Jantar.mutex.release();
    }

    public void pegarTalher() throws InterruptedException {
        Jantar.mutex.acquire();
        comFome();
        // Se a condição for verdadeira, semaforo(1), permitindo que o filósofo obtenha os talheres.
        tentarPegarTalher();        
        Jantar.mutex.release();
        /* Se a condição não for verdadeira, o filósofo vai ficar travado em seu respectivo indice do semáforo,
           até chegar sua vez novamente para tentar pega-los. */
        Jantar.semaforos[this.id].acquire();
    }

    public void tentarPegarTalher() {
        if (Jantar.estado[this.id] == 1 && Jantar.estado[vizinhoEsquerda()] != 2 && Jantar.estado[vizinhoDireita()] != 2) {
            come();
            Jantar.semaforos[this.id].release();
        } else {
            System.out.println("Filosofo " + getId() + " não conseguiu comer");
        }
    }

    @Override
    public void run() {
        try {
            pensa();
            System.out.println("");
            do {
                pegarTalher();
                Thread.sleep(1000L);
                soltarTalher();
            } while (true);
        } catch (InterruptedException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }
    }

    public int vizinhoDireita() {
        return (this.id + 1) % 5;
    }

    public int vizinhoEsquerda() {
        if (this.id == 0) {            
            return 4;
        } else {
            return (this.id - 1) % 5;
        }
    }
}
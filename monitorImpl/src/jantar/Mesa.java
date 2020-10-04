package jantar;

public class Mesa {
	
   final static int PENSANDO = 1;
   final static int COMENDO = 2;
   final static int FOME = 3;
   final static int NR_FILOSOFOS = 5;
   final static int PRIMEIRO_FILOSOFO = 0;
   final static int ULTIMO_FILOSOFO = NR_FILOSOFOS - 1;
   boolean[] garfos = new boolean[NR_FILOSOFOS];
   int[] estados = new int[NR_FILOSOFOS];
   Filosofo[] filosofos;
   int[] tentativas = new int[NR_FILOSOFOS];

   public Mesa(Filosofo[] filosofos) {
      for (int i = 0; i < 5; i++) {
         garfos[i] = true;
         estados[i] = PENSANDO;
         tentativas[i] = 0;
      }     
      this.filosofos = filosofos;
   }

   public synchronized void pegarTalheres(int filosofo) {
      estados[filosofo] = FOME;
      while (estados[vizinhoEsquerdo(filosofo)] == COMENDO || estados[vizinhoDireito(filosofo)] == COMENDO) {
         try {
            tentativas[filosofo]++;
            wait();
         }
         catch (InterruptedException e) {
         }
      }
      System.out.println("Filósofo: " + filosofos[filosofo].getIdFilosofo() + ", estado: Com fome");
      tentativas[filosofo] = 0;
      garfos[talherEsquerdo(filosofo)] = false;
      garfos[talherDireito(filosofo)] = false;
      estados[filosofo] = COMENDO;
      imprimeTentativas();
   }

   public synchronized void returningTalheres(int filosofo) {
      garfos[talherEsquerdo(filosofo)] = true;
      garfos[talherDireito(filosofo)] = true;
      if (estados[vizinhoEsquerdo(filosofo)] == FOME || estados[vizinhoDireito(filosofo)] == FOME) {
         notifyAll();
      }
      estados[filosofo] = PENSANDO;
      imprimeTentativas();
   }

   public int vizinhoDireito(int filosofo) {
      int direito;
      if (filosofo == ULTIMO_FILOSOFO) {
         direito = PRIMEIRO_FILOSOFO;
      }
      else {
         direito = filosofo + 1;
      }
      return direito;
   }

   public int vizinhoEsquerdo(int filosofo) {
      int esquerdo;
      if (filosofo == PRIMEIRO_FILOSOFO) {
         esquerdo = ULTIMO_FILOSOFO;
      }
      else {
         esquerdo = filosofo - 1;
      }
      return esquerdo;
   }

   public int talherEsquerdo(int filosofo) {
      int talherEsquerdo = filosofo;
      return talherEsquerdo;
   }

   public int talherDireito(int filosofo) {
      int talherDireito;
      if (filosofo == ULTIMO_FILOSOFO) {
    	 talherDireito = 0;
      }
      else {
    	 talherDireito = filosofo + 1;
      }
      return talherDireito;
   }

   public void imprimeTentativas() {
      System.out.print("Tentou comer = [ ");
      for (int i = 0; i < NR_FILOSOFOS; i++) {
         System.out.print(estados[i] + " ");
      }
      System.out.println("]");
   }
}
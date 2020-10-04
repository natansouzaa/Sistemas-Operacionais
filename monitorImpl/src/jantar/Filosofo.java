package jantar;

public class Filosofo extends Thread {
   final static int TEMPO_MAXIMO = 100;
   Mesa mesa;
   int filosofo;

   public Filosofo(Mesa mesa, int filosofo) {
      this.mesa = mesa;
      this.filosofo = filosofo;
   }

   public void run() {
      while (true) {           
    	 pensar(1000);         
    	 getTalheres();         
         comer(1000);      
         returnTalheres();
      }
   }

   public void pensar(int tempo) {
      try {
    	  System.out.println("");
    	  System.out.println("Filófoso: " + getIdFilosofo() + ", estado: Pensando");
    	  sleep(tempo);
      }
      catch (InterruptedException e) {
    	  System.out.println("");
    	  System.out.println("O Filófoso" + getIdFilosofo() + " pensou demais");
      }
   }

   public void comer(int tempo) {
      try {
    	  System.out.println("");
    	  System.out.println("Filófoso: " + getIdFilosofo() + ", estado: Comendo");
         sleep(tempo);
      }
      catch (InterruptedException e) {
    	  System.out.println("");
         System.out.println("O Filófoso" + getIdFilosofo() + " comeu demais");
      }
   }
   
   public int getIdFilosofo() {
	   return this.filosofo;
   }
   
   public void getTalheres() {
      mesa.pegarTalheres(filosofo);
   }

   public void returnTalheres() {
      mesa.returningTalheres(filosofo);
   }
}

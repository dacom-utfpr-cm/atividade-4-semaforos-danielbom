package exercises;

import java.util.concurrent.Semaphore;

/**
 * Rendezvous (Encontro)
 * 
 * Enviar sinalização para um ponto de encontro entre threads.Faça um código que
 * uma thread t1 e t2 são inicializadas e t1 espera por t2 e vice-versa.
 * 
 * Exemplos:
 * t1:
 * 		trecho1.1
 * 		trecho1.2
 * t2:
 * 		trecho2.1
 * 		trecho2.2
 * 
 * thecho1.1 ocorre antes trecho2.2 e threcho2.1 ocorre antes de trecho1.2.
 * 
 * trecho 1. thread 1
 * trecho 2. thread 1
 * trecho 1. thread 2
 * trecho 2. thread 2
 * 
 * trecho 1. thread 1
 * trecho 1. thread 2
 * trecho 2. thread 1
 * trecho 2. thread 2
 *
 * @author daniel
 */
public class Exercise2 {
	public static void main(String[] args) {
		Semaphore wait1 = new Semaphore(0);
		Semaphore wait2 = new Semaphore(0);		
		
		new Thread(() -> {
			String name = Thread.currentThread().getName();
			
			try {
				System.out.println("Trecho 1 - " + name);
				wait1.release();
				wait2.acquire();

				System.out.println("Trecho 2 - " + name);
				wait1.release();
				wait2.acquire();

				System.out.println("Trecho 3 - " + name);
				wait1.release();
				wait2.acquire();
			} catch (InterruptedException e) { }
			
		}, "Thread 1").start();
		
		
		new Thread(() -> {
			String name = Thread.currentThread().getName();
			
			try {
				System.out.println("Trecho 1 - " + name);
				wait1.acquire();
				wait2.release();
				
				System.out.println("Trecho 2 - " + name);
				wait1.acquire();
				wait2.release();
				
				System.out.println("Trecho 3 - " + name);
				wait1.acquire();
				wait2.release();
			} catch (InterruptedException e) { }
			
		}, "Thread 2").start();
		
	}
}

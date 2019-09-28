package exercises;

import java.util.concurrent.Semaphore;

import core.Shared;

/**
 * Mutex (Exclusão Mútua)
 * 
 * Garantir acesso exclusivo à seção crítica.Faça um código que possibilite que
 * 2 ou mais threads realizem o incremento de um contador. Faça a exclusão mútua
 * com semáforo.
 * 
 * @author daniel
 */
public class Exercise3 {
	public static void main(String[] args) {
		Semaphore mutex = new Semaphore(1);
		Shared<Integer> count = new Shared<Integer>(0);
		
		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep((long) (Math.random() * 5000) + 500);
					
					mutex.acquire();
					System.out.println("Thread 1: Entrou na região crítica.");
					count.set(count.get() + 1);
					System.out.println("Valor: " + count.get());
					System.out.println("Thread 1: Está saindo da região crítica.");
					mutex.release();
				} catch (InterruptedException e) {
				}
			}
		}).start();
		
		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep((long) (Math.random() * 5000) + 500);
					
					mutex.acquire();
					System.out.println("Thread 2: Entrou na região crítica.");
					count.set(count.get() + 1);
					System.out.println("Valor: " + count.get());
					System.out.println("Thread 2: Está saindo da região crítica.");
					mutex.release();
				} catch (InterruptedException e) {
				}
			}
		}).start();;
		
	}
}

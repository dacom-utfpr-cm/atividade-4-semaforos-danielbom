package exercises;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Semaphore;

/**
 * Fila
 * 
 * Semáforos podem ser usadas para representar uma fila. Faça um código que
 * implemente duas filas (F1 e F2) com semáforos. As threads colocadas na F1 só
 * podem executar se tiver um par na F2. Nesse caso, ambas as threads na
 * primeira posição da fila são executadas.
 * 
 * @author daniel
 */
public class Exercise7 {
	public static void main(String[] args) {
		Semaphore queue1 = new Semaphore(0);
		Semaphore queue2 = new Semaphore(0);
		
		Semaphore mutex = new Semaphore(1);
		
		Queue<String> q1 = new LinkedList<String>();
		Queue<String> q2 = new LinkedList<String>();
		
		Random random = new Random();
		
		Runnable runnerQueue1 = () -> {
			String name = Thread.currentThread().getName();
			try {
				int time = random.nextInt(2000);
				System.out.println(name + ": Trabalhando por " + time);
				Thread.sleep(time);
				queue2.release();
				
				mutex.acquire();
				System.out.println(name + ": Entrando na fila");
				q1.add(name + " : " + time);
				mutex.release();
				
				queue1.acquire();
				System.out.println(name + ": Saindo da fila");
			} catch (InterruptedException e) {
				// ...
			}
		};
		
		
		Runnable runnerQueue2 = () -> {
			String name = Thread.currentThread().getName();
			try {
				int time = random.nextInt(2000);
				System.out.println(name + ": Trabalhando por " + time);
				Thread.sleep(time);
				queue1.release();
				
				mutex.acquire();
				System.out.println(name + ": Entrando na fila");
				q2.add(name + " : " + time);
				mutex.release();
				
				queue2.acquire();
				System.out.println(name + ": Saindo da fila");
			} catch (InterruptedException e) {
				// ...
			}
		};
		
		int k = 0;
		Stack<Thread> threads = new Stack<Thread>();
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(runnerQueue1, "Fila 1: Thread " + k++);
			t.start();
			threads.add(t);
		}
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(runnerQueue2, "Fila 2: Thread " + k++);
			t.start();
			threads.add(t);
		}
		
		while (!threads.isEmpty()) {
			try {
				threads.pop().join();
			} catch (InterruptedException e) {
				// ...
			}
		}
		
		System.out.println();
		System.out.println("Desenfileirando");
		while (!q1.isEmpty() && !q2.isEmpty()) {
			System.out.println(q1.remove());
			System.out.println(q2.remove());
			System.out.println();
		}
	}
}

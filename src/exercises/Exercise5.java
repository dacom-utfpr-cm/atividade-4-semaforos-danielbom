package exercises;

import java.util.concurrent.Semaphore;

import core.Shared;

/**
 * Após n threads sincronizarem em um ponto em comum, um trecho específico
 * (ponto crítico) pode ser executado por cada uma delas. Faça um código que
 * possibilite barrar N threads em um ponto específico de execução e, após
 * todas alcançarem o ponto, todas devem executar o trecho de código após esse
 * ponto.
 * 
 * @author daniel
 */
public class Exercise5 {
	public static void main(String[] args) {
		int nthreads = 10;
		Semaphore mutex = new Semaphore(1);
		Semaphore barrier = new Semaphore(0);
		Shared<Integer> count = new Shared<Integer>(0);
		
		Runnable waitAll = () -> {
			String name = Thread.currentThread().getName();
			System.out.println(name + " esperando...");
			
			try {
				mutex.acquire();
				count.set(count.get() + 1);
				mutex.release();
				
				if (count.get() == nthreads)
					barrier.release();
				
				barrier.acquire();
				barrier.release();
			} catch (InterruptedException e) {
				// ...
			}
			
			System.out.println(name + " liberado...");
		};
		
		for (int i = 0; i < nthreads; i++) {
			new Thread(waitAll).start();
		}
	}
}

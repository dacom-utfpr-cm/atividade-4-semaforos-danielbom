package exercises;

import java.util.concurrent.Semaphore;

import core.Shared;

/**
 * Barreira Reusável
 * Threds em um laço executam uma série de passos e sincronizam em uma barreira
 * a cada passo.Faça um código que implemente uma barreira reusável que feche a
 * si própria após todas as threads passarem.
 * 
 * @author daniel
 */
public class Exercise6 {
	public static void main(String[] args) {
		int nthreads = 3;
		Semaphore mutex = new Semaphore(1);
		Semaphore barrier1 = new Semaphore(0);
		Semaphore barrier2 = new Semaphore(0);
		Shared<Integer> count = new Shared<Integer>(0);
		
		Runnable waitAll = () -> {
			String name = Thread.currentThread().getName();
			int k = 0;
			for (int i = 0; i < 3; i++) {
				System.out.println(name + " esperando... " + i);
				try {
					mutex.acquire();
					count.set(count.get() + 1);
					mutex.release();
					
					if (count.get() == nthreads) {
						mutex.acquire();
						count.set(0);
						mutex.release();

						if (k == 0)
							barrier1.release();
						else
							barrier2.release();
					}
					if (k == 0) {
						barrier1.acquire();
						barrier1.release();
						if (barrier1.availablePermits() == 1)
							barrier1.release();
					} else {
						barrier2.acquire();
						barrier2.release();
						if (barrier2.availablePermits() == 1)
							barrier2.release();
					}
					k = (k + 1) % 2;
				} catch (InterruptedException e) {
					// ...
				}
				System.out.println(name + " liberado... " + i);
			}
		};
		
		for (int i = 0; i < nthreads; i++) {
			new Thread(waitAll).start();
		}
	}
}

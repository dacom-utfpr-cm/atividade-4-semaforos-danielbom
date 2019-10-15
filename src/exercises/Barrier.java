package exercises;

import java.util.concurrent.Semaphore;

import core.Shared;

public class Barrier {
	
	int permits;

	int k = 0, count = 0;
	Semaphore mutex = new Semaphore(1);
	Semaphore barrier1 = new Semaphore(0);
	Semaphore barrier2 = new Semaphore(0);
	
	public Barrier(int permits) {
		this.permits = permits;
	}
	
	private void block(Semaphore barrier) {
		try {
			mutex.acquire();
			count++;
			System.out.println(k + " " + count + " " + barrier.availablePermits());
			mutex.release();
			
			if (count == permits) {
				count = 0;
				k = (k + 1) % 2;
				barrier.release();
			}
			
			barrier.acquire();
			barrier.release();
			if (barrier.availablePermits() == 1)
				barrier.release();
		} catch (InterruptedException e) {
			// ...
		}
	}
	
	public void block() {
		if (k % 2 == 0) this.block(barrier1);
		else this.block(barrier2);
	}
	
}

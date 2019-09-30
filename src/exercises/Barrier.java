package exercises;

import java.util.concurrent.Semaphore;

import core.Shared;

public class Barrier {
	
	int permits;

	int k = 0;
	Semaphore mutex = new Semaphore(1);
	Semaphore barrier1 = new Semaphore(0);
	Semaphore barrier2 = new Semaphore(0);
	Shared<Integer> count = new Shared<Integer>(0);
	
	public Barrier(int permits) {
		this.permits = permits;
	}
	
	private void block(Semaphore barrier) {
		try {
			mutex.acquire();
			count.set(count.get() + 1);
			mutex.release();
			
			if (count.get() == permits) {
				count.set(0);
				barrier.release();
			}
			
			barrier.acquire();
			barrier.release();
			if (barrier.availablePermits() == 1)
				barrier.release();
		} catch (InterruptedException e) {
			// ...
		}
		k = (k + 1) % 2;
	}
	
	public void block() {
		if (k % 2 == 0) this.block(barrier1);
		else this.block(barrier2);
	}
	
}

package exercises;

import java.util.concurrent.Semaphore;

public class SimpleBarrier {
	Semaphore mutex = new Semaphore(1);
	Semaphore barrier = new Semaphore(0);
	int count = 0;
	int permits;
	
	public SimpleBarrier(int permits) {
		this.permits = permits;
	}
	
	public void block() {
		try {
			mutex.acquire();
			count++;
			mutex.release();

			if (count == permits)
				barrier.release();
			
			barrier.acquire();
			barrier.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

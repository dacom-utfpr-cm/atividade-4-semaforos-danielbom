package exercises;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Semaphore;

import core.Interval;

/**
 * Multiplex
 * 
 * Garantir acesso à seção crítica para no máximo N threads.Faça um código que
 * possibilite que N threads estejam na seção crítica simultaneamente.
 * 
 * @author daniel
 */
public class Exercise4 {
	
	static Integer[] createSampleArray(int length) {
		Random random = new Random();

		Integer[] array = new Integer[length];
		for (int i = 0; i < length; i++)
			array[i] = random.nextInt(length);
		return array;
	}
	
	public static void main(String[] args) {		
		Stack<Interval> stack = new Stack<Interval>();
		Integer[] array = createSampleArray(100);
		int intervals = 20;
		int range = (int) Math.ceil(array.length / 5.0);
		for (int i = 0; i < intervals; i++) {
			Interval val = new Interval(i*range, (i+1)*range);
			stack.add(val);
			System.out.println("Intervalo gerado: " + val);
		}

		Semaphore mutex = new Semaphore(1);
		Semaphore limit = new Semaphore(intervals / 4);
		
		Runnable bubblesort = () -> {			
			String name = Thread.currentThread().getName();
			try {
				while (true) {
					limit.acquire();
					mutex.acquire();
					if (stack.empty()) {
						limit.release();
						mutex.release();
						break;
					}
					Interval inter = stack.pop();
					System.out.println(name + " : Entrou : " + limit.availablePermits());
					System.out.println(name + " : Saiu : " + inter);
					mutex.release();
					int min = inter.min();
					int max = inter.max();
					if (array.length < max)
						max = array.length;
					
					boolean trade = false;
					do {
						trade = false;
						System.out.println(name + ": Bubble");
						for (int i = min; i < max - 1; i++) {
							if (array[i] > array[i + 1]) {
								trade = true;
								Integer aux = array[i];
								array[i] = array[i + 1];
								array[i + 1] = aux;
							}
						}
					} while (trade);
					limit.release();
				}
			} catch (InterruptedException e) {
				// ...
			}
		};
		
		Stack<Thread> threads = new Stack<Thread>();
		for (int i = 0; i < intervals / 2; i++) {
			Thread t = new Thread(bubblesort);
			threads.add(t);
			t.start();
			System.out.println("Iniciando thread: " + t);
		}
		
		try {
			while (!threads.empty()) {
				threads.pop().join();
			}
			
			for (int i = 0; i < array.length; i++) {
				System.out.println(i + ": " + array[i]);
			}
		} catch (InterruptedException e) {
			// ...
		}

	}
}

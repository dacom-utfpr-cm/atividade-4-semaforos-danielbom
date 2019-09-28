package exercises;

import java.util.concurrent.Semaphore;

/**
 * Sinalização
 * 
 * Enviar sinal para outra thread para indicar que um evento ocorreu. Faça um
 * código que uma thread t1 e t2 são inicializadas simultaneamente, mas a t2
 * pode somente continuar a execução após a sinalização de t1.
 * 
 * @author daniel
 */
public class Exercise1 {
	public static void main(String[] args) {
		Semaphore signal = new Semaphore(0);
		
		new Thread(() -> {
			String name = Thread.currentThread().getName();
			System.out.println(name + ": Realizando uma operação custosa.");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}
			System.out.println(name + ": Sinalizando final da operação.");
			signal.release();

		}, "t1 - Sinalizadora").start();

		new Thread(() -> {
			String name = Thread.currentThread().getName();
			
			System.out.println(name + ": Esperando pelo sinal");

			try {
				signal.acquire();
			} catch (InterruptedException e) { }
			System.out.println(name + ": Fim da espera");
			
		}, "t2 - Dependente").start();
		
	}
}

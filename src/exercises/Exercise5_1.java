package exercises;

/**
 * Após n threads sincronizarem em um ponto em comum, um trecho específico
 * (ponto crítico) pode ser executado por cada uma delas. Faça um código que
 * possibilite barrar N threads em um ponto específico de execução e, após
 * todas alcançarem o ponto, todas devem executar o trecho de código após esse
 * ponto.
 * 
 * @author daniel
 */
public class Exercise5_1 {
	public static void main(String[] args) {
		int nthreads = 3;
		SimpleBarrier barrier = new SimpleBarrier(nthreads);

		Runnable waitAll = () -> {
			String name = Thread.currentThread().getName();
			System.out.println(name + " esperando...");

			barrier.block();

			System.out.println(name + " liberado...");
		};
		
		for (int i = 0; i < nthreads; i++) {
			new Thread(waitAll).start();
		}
	}
}

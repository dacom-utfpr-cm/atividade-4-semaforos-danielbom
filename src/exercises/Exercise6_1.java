package exercises;

public class Exercise6_1 {
	public static void main(String[] args) {
		int nthreads = 3;
		Barrier barrier = new Barrier(nthreads);
		
		Runnable waitAll = () -> {
			String name = Thread.currentThread().getName();
			for (int i = 0; i < 3; i++) {
				System.out.println(name + " esperando... " + i);
				barrier.block();
				System.out.println(name + " liberado... " + i);
			}
		};
		
		for (int i = 0; i < nthreads; i++) {
			new Thread(waitAll).start();
		}
	}
}

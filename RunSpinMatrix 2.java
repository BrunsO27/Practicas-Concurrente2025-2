import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RunSpinMatrix {
	static int n = 10;
    static int[][] matrix = new int[n][n]; 

    public static void fillMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = i + j;
            }
        }

    }
	
	private static void task(Lock lock) {
		try {
			lock.lock();
			fillMatrix();
		}finally {
			lock.unlock();		
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Future<?>> futures = new ArrayList<Future<?>>();
		int numberThreads = 7;
		ExecutorService executor = Executors.newFixedThreadPool(numberThreads);
//		Lock lock = new TASLock();
//		Lock lock = new TTASLock();
//		Lock lock = new BackoffLock();
//		Lock lock = new MCSLock();
//		Lock lock = new ALock(numberThreads);
//		Lock lock = new CLHLock();
		Lock lock = new ReentrantLock();
		
		long startTime = System.nanoTime();//Start time
		for(int i = 0; i < 100; i++) {
			futures.add(executor.submit(() -> task(lock)));
		}
		executor.shutdown();
		
		for (int i = 0; i < futures.size(); i++) {
            while(!futures.get(i).isDone()){}; // Comprobar que todas las tareas terminen
		}
		long endTime = System.nanoTime();//Finish time
		
		
        System.out.println("Program took " +
                (endTime - startTime)*0.000001 + "ms"); //En milisegundos)
	}
	

}



import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteSnapshotQ_ObstructionFree {

	public static void main(String[] args) {
		int capacity = 4;
		String init = null;
		ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

		SimpleSnapshot<String> snapshotI = new SimpleSnapshot<>(capacity, init);
		SimpleSnapshot<String> snapshotR = new SimpleSnapshot<>(capacity, init);

		ExecutorService executor = Executors.newFixedThreadPool(4);
		Random rand = new Random();

		for (int i = 0; i < 10; i++) {
			int numRand = rand.nextInt(2);  // 0 = enq(), 1 = deq()
			final int ntask = i;
			executor.execute(new RunnableQ_ObstructionFree(ntask, queue, numRand, snapshotI, snapshotR));
		}
		executor.shutdown();

		while (!executor.isTerminated()) {}

		System.out.println("Snapshot Final --- Values");
		Object[] rawSnap = snapshotR.scan();
        String[] snap = java.util.Arrays.stream(rawSnap)
            .map(obj -> obj == null ? "null" : obj.toString())
            .toArray(String[]::new);

		for (int j = 0; j < snap.length; j++) {
			System.out.println("Thread " + j + " value: " + snap[j]);
		}
	}
}

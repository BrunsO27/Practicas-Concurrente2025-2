
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Arrays;


public class RunnableQ_ObstructionFree implements Runnable {
	private int task;
	private ConcurrentLinkedQueue<Integer> queue;
	private int operation; // 0 = enq, 1 = deq
	private SimpleSnapshot<String> snapshotI;
	private SimpleSnapshot<String> snapshotR;

	public RunnableQ_ObstructionFree(int task, ConcurrentLinkedQueue<Integer> queue, int operation,
			SimpleSnapshot<String> snapshotI, SimpleSnapshot<String> snapshotR) {
		this.task = task;
		this.queue = queue;
		this.operation = operation;
		this.snapshotI = snapshotI;
		this.snapshotR = snapshotR;
	}

	@Override
	public void run() {
		ThreadID.set(task % 4); // Simula 4 threads
		if (operation == 0) {
			queue.add(task);
			snapshotI.update("enq(" + task + ")");
		} else {
			Integer val = queue.poll();
			snapshotI.update("deq(" + (val == null ? "null" : val) + ")");
		}
		// Guardamos vista snapshot
		Object[] rawView = snapshotI.scan();
        String[] view = java.util.Arrays.copyOf(rawView, rawView.length, String[].class);

		StringBuilder response = new StringBuilder();
		for (String s : view) {
			response.append(s).append(" | ");
		}
		snapshotR.update(response.toString());
	}
}

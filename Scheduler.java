import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler {
    
    static Semaphore semaforo = new Semaphore(3);

    public static void main(String[] args){
        ExecutorService executorTarea = Executors.newFixedThreadPool(6);
        for(int i = 0; i < 26; i++) {
            executorTarea.execute(new Tarea(i, semaforo));
        }
        executorTarea.shutdown();
    }
}
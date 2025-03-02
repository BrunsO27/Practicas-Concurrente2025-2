import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tarea implements Runnable {
    private int tarea;
    private int tiempoEjecucion;
    final Semaphore semaforo;
    final Lock lock;

    public Tarea(int tarea, Semaphore semaforo) {
        this.tarea = tarea;
        this.semaforo = semaforo;
        this.lock = new ReentrantLock();
    }
    
    @Override
    public void run() {
        Thread hiloActual = Thread.currentThread();
        long id = hiloActual.getId();

        int valor = (int) (id % 6);

        System.out.println("Hilo " + id + " ejecutando tarea " + tarea + " con valor " + valor);

        switch (valor) {
            case 0:
                this.tiempoEjecucion = 500;
                break;

            case 2:
                this.tiempoEjecucion = 500;
                break;

            case 1:
                this.tiempoEjecucion = 2000;
                break;

            default:
                this.tiempoEjecucion = 3000;
                break;
        }

        try {
            semaforo.acquire();
            if (valor == 0 || valor == 2) {
                lock.lock();
            }
            
            Thread.sleep(this.tiempoEjecucion);

            semaforo.release();
            if (valor == 0 || valor == 2) {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println("Error en la tarea " + tarea);
            System.out.println(e);
            e.printStackTrace();
        }

        System.out.println("Hilo en ejecucion: " + valor + " tiempo: " + this.tiempoEjecucion);
    }
    
}
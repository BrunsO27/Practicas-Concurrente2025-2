package unam.fc.concurrent.practica1;

/*	Programa 10: Programa para obtener el determinante de una matriz de 3x3
 * 	Es más paralelizable, pero nos benefician en el tiempo de ejecucon utilizar hilos?
*/

public class DeterminanteConcurrenteDosHilos extends Thread {
    static int determinante;
    static int n_prueba = 3;
    static int matriz_prueba[][] = { { 1, 2, 2 }, { 1, 0, -2 }, { 3, -1, 1 } };
    int partial;
    int[][] valores;

    public DeterminanteConcurrenteDosHilos(int[][] valores) {
        this.valores = valores;
    }

    public static int determinanteMatriz3x3(int matriz[][]) {
        int result = 0;

        int[][] valores1 = {
            { matriz[0][0], matriz[1][1], matriz[2][2] },
            { matriz[1][0], matriz[2][1], matriz[0][2] },
            { matriz[2][0], matriz[0][1], matriz[1][2] }
        };

        int[][] valores2 = {
            { matriz[2][0], matriz[1][1], matriz[0][2] },
            { matriz[1][0], matriz[0][1], matriz[2][2] },
            { matriz[0][0], matriz[2][1], matriz[1][2] }
        };

       
        DeterminanteConcurrenteDosHilos thr1 = new DeterminanteConcurrenteDosHilos(valores1);
        DeterminanteConcurrenteDosHilos thr2 = new DeterminanteConcurrenteDosHilos(valores2);

       
        thr1.start();
        thr2.start();

        try {
            
            thr1.join();
            thr2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result = thr1.partial - thr2.partial;

        return result;
    }

    @Override
    public void run() {
        this.partial = (valores[0][0] * valores[0][1] * valores[0][2]) +
                       (valores[1][0] * valores[1][1] * valores[1][2]) +
                       (valores[2][0] * valores[2][1] * valores[2][2]);
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        determinante = determinanteMatriz3x3(matriz_prueba);
        long endTime = System.nanoTime();

        System.out.println("Program took " + 
            (endTime - startTime) + "ns, result: " + determinante);
    }
}

public class DeterminanteSecuencial {
    static int determinante;
    static int n_prueba = 3;
    static int matriz_prueba[][] = { { 1, 2, 2 }, { 1, 0, -2 }, { 3, -1, 1 }};
    int num1, num2, num3, partial;
    
    public DeterminanteSecuencial(int num1, int num2, int num3) {
		this.num1 = num1;
		this.num2 = num2;
		this.num3 = num3;
	}
    
    public static int determinanteMatriz3x3(int matriz[][], int n_prueba) {
        int term1 = matriz[0][0] * matriz[1][1] * matriz[2][2];
        int term2 = matriz[1][0] * matriz[2][1] * matriz[0][2];
        int term3 = matriz[2][0] * matriz[0][1] * matriz[1][2];
        int term4 = matriz[2][0] * matriz[1][1] * matriz[0][2];
        int term5 = matriz[1][0] * matriz[0][1] * matriz[2][2];
        int term6 = matriz[0][0] * matriz[2][1] * matriz[1][2];

        return (term1 + term2 + term3) - (term4 + term5 + term6);
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        determinante = determinanteMatriz3x3(matriz_prueba, n_prueba);
        long endTime = System.nanoTime();
        
        System.out.println("Program took " + (endTime - startTime) + "ns, result: " + determinante);
    }
}

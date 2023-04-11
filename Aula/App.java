import java.lang.Math;

public class App{

    private static long timer;

    private static void resetTimer() {
        App.timer = System.nanoTime();
        }

        private static double stopTimer() {
            App.timer = System.nanoTime() - App.timer;
            return App.timer / 1e6;
            }
    public static void main(String args[]){
        resetTimer();
        Tupla[] itens = {new Tupla(50,56), 
                        new Tupla(50,59), 
                        new Tupla(64,80), 
                        new Tupla(46,64),
                        new Tupla(50,75),
                        new Tupla(5,17)};
        int C = 190;
        int N = itens.length;
        System.out.println(backPackPd(N, C, itens));
        System.out.println("tempo = " + stopTimer());
    }

    public static int backPackPd(int N, int C, Tupla[] itens){
        int [][] maxTab = new int[N+1][C+1];

        for(int i = 0; i <= C; i++) {
            maxTab[0][i] = 0;
        }
             
        for(int i = 0; i <= N; i++) {
            maxTab[i][0] = 0;
        }

        for (int i = 1; i < N; i++) {
            for (int j = 11; j < C; j++) {
                if (itens[i].getpeso() <= j) {
                    maxTab[i][j] = Math.max(
                        maxTab[i - 1][j], 
                        itens[i].getvalor() + maxTab[i - 1][j - itens[i].getpeso()]
                    );
                } else {
                    maxTab[i][j] = maxTab[i-1][j];
                }

                // System.out.println(maxTab[i][j]);
            }
        }

        return maxTab[N][C];
    }
}

// Peso Valor
// 5 kg $2
// 2 kg $4
// 2 kg $2
// 1 kg $3


/*
 * for(int i = 0; i<maxTab.lenght; i++){
 *  maxTab[0][i] = 0;
 * }
 * 
 * for(int i = 0; i<maxTab[].lenght; i++){
 *  maxTab[i][0] = 0;
 * }
 */
// Inteiro backPackPD(Inteiro N, Inteiro C, Tupla<Inteiro, Inteiro> itens)
//    N = número de produtos;
//    C = capacidade real da mochila
//    itens[N +1];   // (O índice 0 guarda null), Tupla com peso e valor
//    maxTab[N+1][C+1];

//    Inicialize com 0 toda a linha 0 e também a coluna 0;
//    para i = 1 até N
//       para j = 1 até C
//          se item itens[i].peso <= j // se o item cabe na mochila atual
//             maxTab[i][j] = Max(maxTab[i-1][j], 
//                                itens[i].valor + 
//                                  maxTab[i-1][j – itens[i].peso]);
//          senão
//             maxTab[i][j] = maxTab[i-1][j];

//    retorne maxTab[N][C] // valor máximo para uma mochila de capacidade C e 		         
//                         //que pode conter itens que vão do item 1 até o item N.
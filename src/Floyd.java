public class Floyd {
    static int INF = (int) Double.POSITIVE_INFINITY;
    int size;
    private int[][] path;//Path
    private int[][] weight;//Distance

    public Floyd(int[][] matrix) {
        size = matrix.length;
        path = new int[size][size];
        weight = new int[size][size];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                path[i][j] = j;
                if (i != j)//Possilbe Circle
                {
                    if (matrix[i][j] == 0)
                        weight[i][j] = INF;
                    else weight[i][j] = matrix[i][j];
                } else weight[i][j] = matrix[i][j];
            }
        }

        printPath();
        printWeight();
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    //from i through k to j
                    int i_k_j = (weight[i][k] == INF || weight[k][j] == INF) ? INF : (weight[i][k] + weight[k][j]);
                    if (weight[i][j] > i_k_j) {
                        weight[i][j] = i_k_j;
                        path[i][j] = path[i][k];

                    }

                }
            }
        }
    }

    public static void main(String[] args) {

        int[][] a = {{0, 3, 4, 0, 0, 0}, {3, 0, 0, 8, 3, 6}, {4, 0, 0, 5, 0, 0}, {0, 8, 5, 0, 2, 0}, {0, 3, 0, 2, 0, 2}, {0, 6, 0, 0, 2, 0}};
        Floyd floyd = new Floyd(a);
        floyd.printPath();
        floyd.printWeight();
        floyd.printRoute(0,3);

    }

    public void printPath() {
        System.out.println("Weight");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(path[i][j] + " ");
            System.out.println();
        }


    }

    public void printWeight() {
        System.out.println("Path");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(weight[i][j] + " ");
            System.out.println();
        }


    }

    public void printRoute(int from, int to) {
        if (path[from][to] == INF) {
            System.out.println("Sorry, they are not connected");
            return;
        }
        System.out.println("Here's the shortest path");
        int i = from;
        int j = to;


//        if(i==j)
//        {
//            System.out.print("It's a circle.");
//            if(path[i][i]==i) {
//                System.out.println(i+"-"+weight[i][i]+"-"+i);
//                return;
//            }
//            else {
//                int next=path[i][i];
//                System.out.println(i+"-"+weight[i][next]+"-"+j);
//                i=next;
//                while(i!=j)
//                {
//                    int nextStep=path[i][j];
//                    System.out.println(i+"-"+weight[i][nextStep]+"-"+nextStep);
//                    i=path[i][nextStep];
//                }
//            }
//
//        }


        while(path[i][j]!=j)
        {
            int nextStep=path[i][j];
            System.out.println(i+"-"+weight[i][nextStep]+"-"+nextStep);
            i=path[i][nextStep];
        }
       // final step;
        int finalStep=path[i][j];
        System.out.println(i+"-"+weight[i][finalStep]+"-"+finalStep);




    }


}
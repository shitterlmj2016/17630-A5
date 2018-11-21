import java.util.LinkedList;

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

        printPathMatrix();
        printCostMatrix();
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
        //Test case 2
        int[][] b = {{0, 1, 3, 0, 0, 0}, {1, 0, 0, 1, 0, 0}, {3, 0, 0, 2, 0, 0}, {0, 1, 2, 12, 0, 0}, {0, 0, 0, 0, 0, 3}, {0, 0, 0, 0, 3, 7}};
        Floyd floyd = new Floyd(a);
        LinkedList list=floyd.getRoute(0,0);
        for(int i=0;i<list.size();i++)
        {
            int []route=(int [])list.get(i);

            System.out.println(route[0]+"-"+route[1]+"-"+route[2]);
        }

    }

    private void printPathMatrix() {
        System.out.println("path:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(path[i][j] + " ");
            System.out.println();
        }


    }

    private void printCostMatrix() {
        System.out.println("cost:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(weight[i][j] + " ");
            System.out.println();
        }


    }

    public void printRoute(int from, int to) {
        if (weight[from][to] == INF) {
            System.out.println("Sorry, they are not connected");
            return;
        }
        System.out.println("Here's the shortest path");
        int i = from;
        int j = to;

        while (path[i][j] != j) {
            int nextStep = path[i][j];
            System.out.println(i + "-" + weight[i][nextStep] + "-" + nextStep);
            i = path[i][nextStep];
        }
        // final step;
        int finalStep = path[i][j];
        System.out.println(i + "-" + weight[i][finalStep] + "-" + finalStep);

    }


    public int getCost(int from, int to) {
        return weight[from][to];
    }

    public LinkedList<int[]> getRoute(int from, int to) {
        LinkedList list = new LinkedList();

        //Route format from-cost-to


        int i = from;
        int j = to;

        while (path[i][j] != j) {
            int nextStep = path[i][j];
            int[] route = new int[3];
            route[0] = i;
            route[2] = nextStep;
            route[1] = weight[i][nextStep];
            list.add(route);
            i = path[i][nextStep];
        }
        // final step;
        int finalStep = path[i][j];

        int[] route = new int[3];
        route[0] = i;
        route[2] = finalStep;
        route[1] = weight[i][finalStep];
        list.add(route);

        return list;

    }


}

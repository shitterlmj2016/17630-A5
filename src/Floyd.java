//17630-A5 Graph
//Andrew ID: xinchenh
//Name: Xincheng Huang
//Floyd Class
//This class helps calculate the best path usin floyd algorithm

//FLoy ADT provides two operations
//getCost: get the cost of the best path from A to B, input two chars, return an integer
//getRoute: get the route of the best path from A to B, input two chars, return a list of vertex

import java.util.LinkedList;

public class Floyd {
    static int INF = (int) Double.POSITIVE_INFINITY;//Represent two vertexes are not connected

    int size; //The total number of all the nodes
    private int[][] path;//Path
    private int[][] weight;//Distance


    //Construction
    //Brief explanation:
    //A to B has two choices
    // 1. A to B directly
    // 2. A to somewhere else on the planet (C) to B
    // Try every nodes, after that the minimum path is found
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

    //Debug function
    private void printPathMatrix() {
        System.out.println("path:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(path[i][j] + " ");
            System.out.println();
        }


    }

    //Debug function
    private void printCostMatrix() {
        System.out.println("cost:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(weight[i][j] + " ");
            System.out.println();
        }


    }

    //Debug function
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

    //Get the total cost of two vertexes, return an integer
    public int getCost(int from, int to) {
        return weight[from][to];
    }

    //Get the best route from A to B
    //Return a list of all vertexes' name
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

//17630-A5 Graph
//Andrew ID: xinchenh
//Name: Xincheng Huang
//Main Class
//This class is the test harness

import java.io.*;
import java.util.List;
import java.util.Scanner;


public class Main {
    static int INF = (int) Double.POSITIVE_INFINITY;


    public static void main(String[] args) {


        Graph graph = new Graph();
        Boolean Done = false;
        while (!Done) {
            // Here we present the main menu

            System.out.println("\n\n");
            System.out.println("Graph Console:");
            System.out.println("Humbly presented by Xincheng Huang: \n");
            System.out.println("1: Create a graph from a file");
            System.out.println("2: Display the entire graph");
            System.out.println("3: Show the graph’s high/low degree");
            System.out.println("4: Show the minimum spanning tree");
            System.out.println("5: Show the Euler path");
            System.out.println("6: Show best path");
            System.out.println("X: Exit\n");
            System.out.print("\n>>>> ");
            BufferedReader MyReader = new BufferedReader(new InputStreamReader(System.in));

            char CharItem = ' ';


            try {

                CharItem = (char) MyReader.read();

            } catch (IOException IOError) {

                System.out.println("Read Error in Termio.KeyboardReadChar method");

            } // catch


            //Case 1 Create a graph from a file
            if (CharItem == '1') {
                if(graph.getSize()!=0)
                {
                    System.out.println("Sorry, a graph already exists!");
                    continue;
                }

                System.out.println("Please input the file path: ");
                System.out.println("Example: C:\\Users\\xchuang1995\\Desktop\\input.txt");

                //Scan the input string
                Scanner sc = new Scanner(System.in);
                System.out.print("\n>>>> ");
                String address = sc.nextLine();
                if (address.isEmpty()) {
                    System.out.println("Empty input! Please try again!");
                    continue;
                }

                FileInputStream fis = null;
                InputStreamReader isr = null;
                BufferedReader br = null; //Buffer

                try {
                    String str = "";
                    fis = new FileInputStream(address);// FileInputStream
                    isr = new InputStreamReader(fis);// InputStreamReader
                    br = new BufferedReader(isr);// BufferedReader

                    boolean legalGraph = true;
                    //Check if user's input is legal
                    while ((str = br.readLine()) != null) {
                        if (graph.addVertexByString(str) == false) {
                            legalGraph = false;
                            break;
                        }
                    }
                    if (legalGraph == false) {
                        System.out.println("Illegal format! Returning to main menu...");
                        continue;
                    }
                    System.out.println("Graph is created successfully!");
                    //Print the graph
                    graph.print();


                    //Print the adjacency matrix
                    System.out.println();
                    System.out.println("Adjacency Matrix: ");
                    int[][] matrix = graph.getMatrix();
                    int[][] weight = new int[matrix.length][matrix.length];
                    for (int i = 0; i < matrix.length; i++) {
                        for (int j = 0; j < matrix.length; j++) {
                            if (i != j)//Possilbe Circle
                            {
                                if (matrix[i][j] == 0)
                                    weight[i][j] = INF;
                                else weight[i][j] = matrix[i][j];
                            } else weight[i][j] = matrix[i][j];
                        }
                    }

                    for (int i = 0; i < matrix.length; i++) {
                        for (int j = 0; j < matrix.length; j++) {
                            if (weight[i][j] == INF)//Possilbe Circle
                            {
                                System.out.print("INF ");
                            } else System.out.print(weight[i][j] + " ");
                        }
                        System.out.println();
                    }


                } catch (FileNotFoundException e) {
                    System.out.println("File is not found!");
                    continue;
                } catch (IOException e) {
                    System.out.println("Reading Errors!");
                }
                try {
                    br.close();
                    isr.close();
                    fis.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }


            }

            //Case 2 Display the entire graph
            if (CharItem == '2') {
                //check if the graph is empty right now
                if (graph.getSize() == 0) {
                    System.out.println("Bro, the graph is still empty, please create a graph first!");
                    continue;
                } else {
                    System.out.println("Now printing the graph...");
                    graph.print();
                }
            }

            //Case 3 Print the low/high Degree
            if (CharItem == '3') {
                //check if the graph is empty right now
                if (graph.getSize() == 0) {
                    System.out.println("Homie, the graph is still empty, please create a graph first!");
                    continue;
                } else {
                    System.out.println("Warning: ");
                    System.out.println("*The self loop in the map (such as A:[2,A]) counts two degree");
                    System.out.println("*If there are multiple vertexes of the same low/high degree, one one of them is represented ");
                    System.out.println("Now printing the low/high degree...");
                    graph.getLowDegree();
                    graph.getHighDegree();
                }
            }

            //Case 4 Show the minimum spanning tree
            if (CharItem == '4') {
                if (graph.getSize() == 0) {
                    System.out.println("Pal, the graph is still empty, please create a graph first!");
                    continue;
                } else {
                    graph.miniSpan();
                }
            }

            //Case 4 Show the euler path
            if (CharItem == '5') {
                if (graph.getSize() == 0) {
                    System.out.println("Pal, the graph is still empty, please create a graph first!");
                    continue;
                } else {
                    graph.euler();
                }
            }


            if (CharItem == '6') {
                if (graph.getSize() == 0) {
                    System.out.println("Friend, the graph is still empty, please create a graph first!");
                    continue;
                }

                BufferedReader Reader = new BufferedReader(new InputStreamReader(System.in));

                //Get the name of two vertexes
                char v1 = ' ';
                char v2 = ' ';

                System.out.println("Please input the starting vertex: ");
                try {
                    v1 = (char) Reader.read();
                } catch (IOException IOError) {
                    System.out.println("Read Error in Termio.KeyboardReadChar method");
                } // catch


                //Check if the first input is legal
                List list = graph.getVertexList();
                boolean b1 = false;
                for (int i = 0; i < graph.getSize(); i++) {
                    Vertex v = (Vertex) list.get(i);
                    if (v.getName().toCharArray()[0] == v1) {
                        b1 = true;
                        break;
                    }
                }
                if (!b1) {
                    System.out.println("Wrong input!");
                    continue;
                }

                //Clean the buff
                Reader = new BufferedReader(new InputStreamReader(System.in));

                //Check if the second input is legal
                boolean b2 = false;
                System.out.println("Please input the destination vertex: ");
                try {
                    v2 = (char) Reader.read();
                } catch (IOException IOError) {
                    System.out.println("Read Error in Termio.KeyboardReadChar method");
                } // catch

                for (int i = 0; i < graph.getSize(); i++) {
                    Vertex v = (Vertex) list.get(i);
                    if (v.getName().toCharArray()[0] == v2) {
                        b2 = true;
                        break;
                    }
                }
                if (!b2) {
                    System.out.println("Wrong input!");
                    continue;
                }


                //Generate the best path
                graph.getBestPath(v1, v2);

            }

            if (CharItem == 'x' || CharItem == 'X') {
                System.out.println("Exiting");
                Done = true;
            }
        }
    }
}

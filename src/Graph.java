//17630-A5 Graph
//Andrew ID: xinchenh
//Name: Xincheng Huang
//Floyd Class
//This class helps calculate the best path usin floyd algorithm
//Too many things to included in the head file, please refer to the writeup
//Components: A list of its vertexes


import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.regex.Pattern;

public class Graph {
    static int INF = (int) Double.POSITIVE_INFINITY;

    //Comparator is used for heap for minimun spanning tree
    private static Comparator<String> weightComparator = new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            char[] c1 = s1.toCharArray();
            char[] c2 = s2.toCharArray();
            int i = (int) c1[1];
            int j = (int) c2[1];
            return i - j;
        }
    };

    //Compare the weight of two edges
    private int[][] matrix;
    private List vertexList;
    private List hashArray;

    //Construction
    public Graph() {
        vertexList = new LinkedList<Vertex>();
    }

    //Check if the input is a number
    public static boolean isNumeric(String string) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
    }

    //Check if the input is from A-Z
    private boolean islegalVname(char name) {
        return (name < 91 && name > 64);

    }

    //Get the list of vertexes
    public List getVertexList() {
        return vertexList;
    }

    //Add an vertex to the graph
    private void addVertex(Vertex v) {
        vertexList.add(v);
    }

    public int getSize() {
        return vertexList.size();
    }


    //Add an vertex to the graph by string
    public boolean addVertexByString(String a) {

        a = a.replaceAll("[]| |:|;|]", "");
        //A[B,2[C,3
        String[] temp = a.split("\\[");
        //A||B,2|| C,3

        //vertice name
        String vname = temp[0];

        //Tell if the vertex name is right
        char name = vname.toCharArray()[0];
        if (!islegalVname(name))
            return false;

        //v is a new vertice
        Vertex v = new Vertex(vname);

        for (int i = 1; i < temp.length; i++) {   //B,2
            String edge = temp[i];//The information of an edge

            //B||2
            String[] vw = edge.split(",");

            //Check if the destination vertex's name is legal
            char dname = vw[0].toCharArray()[0];

            if (!islegalVname(dname))
                return false;


            //Check if the weight is a legal number
            if (!isNumeric(vw[1]))
                return false;

            Edge e = new Edge(Integer.parseInt(vw[1]), vw[0]);
            v.addEdge(e);
        }
        vertexList.add(v);
        return true;
    }

    //Print the graph
    public void print() {
        if (getSize() == 0) {
            System.out.println("Sorry, the graph is empty.");
            return;
        }

        //Keep to the format thing
        for (int i = 0; i < getSize(); i++) {
            Vertex v = (Vertex) vertexList.get(i);
            System.out.print(v.getName() + ": ");
            int length = v.getEdgeList().size();

            for (int j = 0; j < length; j++) {
                Edge edge = (Edge) (v.getEdgeList().get(j));
                System.out.print("[" + edge.getVertexName() + "," + edge.getWeight() + "]");
                if (j != length - 1) {
                    System.out.print("; ");
                }
            }
            System.out.println();
        }

    }

    //Get the low degree
    public int getLowDegree() {
        if (getSize() == 0) {
            return 0;
        }
        Vertex v = (Vertex) getVertexList().get(0);
        int degree = v.getCircleDegree();
        Vertex low = v;
        for (int i = 1; i < getSize(); i++) {
            v = (Vertex) getVertexList().get(i);
            if (v.getCircleDegree() < degree) {
                degree = v.getCircleDegree();
                low = v;
            }
        }
        System.out.println("The low degree is " + low.getName() + ": " + degree);
        return degree;
    }

    //Get the high degree
    public int getHighDegree() {
        if (getSize() == 0) {
            return 0;
        }
        Vertex v = (Vertex) getVertexList().get(0);
        int degree = v.getCircleDegree();
        Vertex high = v;
        for (int i = 1; i < getSize(); i++) {
            v = (Vertex) getVertexList().get(i);
            if (v.getCircleDegree() > degree) {
                degree = v.getCircleDegree();
                high = v;
            }
        }

        System.out.println("The high degree is " + high.getName() + ": " + degree);
        return degree;
    }


    //Create strings in VertexWeightVertex format
    //Like A5B
    //It is used for heap to get minimun spanning tree
    private LinkedList<String> getEdgeBag() {
        LinkedList bag = new LinkedList<String>();
        for (int i = 0; i < vertexList.size(); i++) {
            Vertex v = (Vertex) vertexList.get(i);
            for (int j = 0; j < v.getEdgeList().size(); j++) {
                Edge edge = (Edge) v.getEdgeList().get(j);

                String from = v.getName();
                String to = edge.getVertexName();
                String weight = String.valueOf(edge.getWeight());

                //Check if both Strings are not in the bag
                String pathOne = from + weight + to;
                String pathTwo = to + weight + from;
                if (!bag.contains(pathOne) && !bag.contains(pathTwo))
                    bag.add(pathOne);
            }

        }

        return bag;
    }

    //KRUSKAL Algorithm
    public void miniSpan() {
        //The set of all vertexes
        //Check if the map is connected
        if (!isConnected()) {
            System.out.println("Sorry, the graph is not connected");
            return;
        }
        System.out.println("Minimum spanning tree found, here's all the edges.");
        char[] vertexRecoder = new char[vertexList.size()];
        for (int i = 0; i < vertexList.size(); i++) {
            char[] temp = ((String) ((Vertex) vertexList.get(i)).getName()).toCharArray();
            vertexRecoder[i] = temp[0];
        }

        //Get all edges in the format of VertexWeightVertex String
        LinkedList bag = getEdgeBag();
        PriorityQueue<String> queue = new PriorityQueue<String>(
                weightComparator) {
        };

        for (int i = 0; i < bag.size(); i++) {
            //Add all edge to the heap
            queue.add((String) bag.get(i));
        }

        //
        UnionFind uf = new UnionFind(getSize());
        Graph graph = new Graph();
        int count = 1;
        while (uf.count() != 1) {
            String poll = queue.poll();
            char[] temp = poll.toCharArray();
            char from = temp[0];
            char to = temp[2];
            char weigh = temp[1];

            int vertexA = hash(from);
            int vertexB = hash(to);

            //Automatically remove circle
            //If these two vertexes have already been connected, simply throw them
            //Otherwise, union them
            if (!uf.connected(vertexA, vertexB)) {
                uf.union(vertexA, vertexB);
                System.out.print("Edge " + count + ": ");
                System.out.println((char) from + "-" + (char) to + " ");
                count++;
            }

        }

        return;
    }


    //Hash from an A-Z to the corresponding index
    private int hash(char c) {
        int size = getSize();
        String s = String.valueOf(c);
        if (size == 0) {
            System.out.println("Sorry, the size is zero");
            return -1;
        }
        for (int i = 0; i < size; i++) {
            Vertex v = (Vertex) vertexList.get(i);
            if (s.equals(v.getName()))
                return i;
        }
        return -1;
    }


    //Get the euler path
    public void euler() {
        if (getSize() == 0) {
            System.out.println("Sorry, the graph is empty.");
            return;
        }
        //Check if the graph is connected
        if (!isConnected()) {
            System.out.println("Sorry, the graph is not connected.");
            return;
        }
        //Check if the graph has 0 or 2 odd nodes
        System.out.println("There are " + countOdd() + " odd node(s) is this graph.");
        if (countOdd() != 0 && countOdd() != 2) {
            System.out.println("Sorry, there is no euler path in this graph.");
            return;
        }
        matrix = getBoolMatrix();
        int start = 0;
        //Find an odd node to start
        //If none found, start from the first node
        for (int i = 0; i < getSize(); i++) {
            if (isOdd(matrix[i])) {
                start = i;
                break;
            }
        }
        System.out.println("Here's the euler path:");
        fleury(start);
    }


    private void fleury(int index) {

        //Check if all edges have been covered
        if (isEmpty(matrix[index]))
            return;

        //If not, gone throw all the vertexes this vertexes connected to
        //Find an none-bridge edge to start with
        for (int i = 0; i < getSize(); i++) {
            if (matrix[index][i] == 1) {
                //None bridge first
                if (!isOdd(matrix[i])) {

                    System.out.println("From " + hashBack(index) + " to " + hashBack(i));
                    matrix[index][i] = 0;
                    matrix[i][index] = 0;
                    fleury(i);
                    return;
                }
                //Only bridges are left

                System.out.println("From " + hashBack(index) + " to " + hashBack(i));
                matrix[index][i] = 0;
                matrix[i][index] = 0;
                fleury(i);
                return;

            }

        }


    }

    //Hash form an index to the character it corresponds to
    private String hashBack(int i) {
        Vertex v = (Vertex) vertexList.get(i);
        return v.getName();
    }

    //Check if a vertex has an odd degree
    private boolean isOdd(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++)
            sum = sum + array[i];
        return (sum % 2) != 0;
    }

    //check if an array is empty
    private boolean isEmpty(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++)
            sum = sum + array[i];
        return sum == 0;

    }


    //Check if it is even or odd
    public boolean checkDegree() {
        int odd = 0;
        for (int i = 0; i < getSize(); i++) {
            Vertex v = (Vertex) vertexList.get(i);
            if ((v.getDegree() % 2) != 0) {
                odd++;
            }
        }

        return (odd == 2 || odd == 0);
    }

    //Count how many odd vertexes are there in the graph
    public int countOdd() {
        int odd = 0;
        for (int i = 0; i < getSize(); i++) {
            Vertex v = (Vertex) vertexList.get(i);
            //loop?
            int degree = v.getCircleDegree();

            if ((degree % 2) != 0) {
                odd++;
            }
        }

        return odd;
    }

    //Check if the map is connected through union find
    private boolean isConnected() {
        UnionFind uf = new UnionFind(getSize());

        for (int i = 0; i < getSize(); i++) {
            Vertex v = (Vertex) vertexList.get(i);
            int from = hash(v.getName().toCharArray()[0]);
            for (int j = 0; j < v.getDegree(); j++) {
                Edge e = (Edge) v.getEdgeList().get(j);
                int to = hash(e.getVertexName().toCharArray()[0]);
                uf.union(from, to);

            }

        }

        //In the end, if count is one means that the map is connected
        return uf.count() == 1;

    }


    //Remove the weight of adjacency matrix
    public int[][] getBoolMatrix() {
        int[][] matrix = new int[getSize()][getSize()];
        for (int i = 0; i < getSize(); i++) {
            Vertex v = (Vertex) vertexList.get(i);
            int from = hash(v.getName().toCharArray()[0]);
            for (int j = 0; j < v.getDegree(); j++) {
                Edge e = (Edge) v.getEdgeList().get(j);
                int to = hash(e.getVertexName().toCharArray()[0]);
                matrix[from][to] = 1;
            }
        }
        this.matrix = matrix;
        return matrix;
    }

    //Get the adjacency matrix
    public int[][] getMatrix() {
        int[][] matrix = new int[getSize()][getSize()];
        for (int i = 0; i < getSize(); i++) {
            Vertex v = (Vertex) vertexList.get(i);
            int from = hash(v.getName().toCharArray()[0]);
            for (int j = 0; j < v.getDegree(); j++) {
                Edge e = (Edge) v.getEdgeList().get(j);
                int to = hash(e.getVertexName().toCharArray()[0]);
                matrix[from][to] = e.getWeight();
            }
        }
        return matrix;
    }

    //Get the best path from A to B
    //Using floyd algorithm
    public void getBestPath(char from, char to) {
        if (getSize() == 0) {
            System.out.println("Sorry, the graph is empty.");
            return;
        }
        int i = hash(from);
        int j = hash(to);
        int[][] adjacencyMatrix = getMatrix();
        Floyd floyd = new Floyd(adjacencyMatrix);

        if (floyd.getCost(i, j) == INF) {
            System.out.println("Sorry, they are not connected.");
            return;
        }

        System.out.println("Best path found! Minimum cost: " + floyd.getCost(i, j));
        System.out.println("Now printing the route: ");
        LinkedList<int[]> list = floyd.getRoute(i, j);
        for (int k = 0; k < list.size(); k++) {
            int[] route = list.get(k);
            System.out.print("Step " + (k + 1) + ": ");
            System.out.println("From " + hashBack(route[0]) + " to " + hashBack(route[2]) + ", cost: " + route[1]);

        }

    }

}

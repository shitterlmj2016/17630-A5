import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.regex.Pattern;

public class Graph {
    static int INF = (int) Double.POSITIVE_INFINITY;
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

    public Graph() {
        vertexList = new LinkedList<Vertex>();
    }


    public static boolean isNumeric(String string) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
    }

    //Tell if the input is from A-Z
    private boolean islegalVname(char name) {
        return (name < 91 && name > 64);

    }

    public List getVertexList() {
        return vertexList;
    }

    public void addVertex(Vertex v) {
        vertexList.add(v);
    }

    public int getSize() {
        return vertexList.size();
    }

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

    public void print() {
        if (getSize() == 0) {
            System.out.println("Sorry, the graph is empty.");
            return;
        }

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

    ;

    //Create strings in VertexWeightVertex format
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

    //Check if we have finished building the tree
    private boolean checkFinished(char[] vertexRecoder) {

        for (int i = 0; i < vertexRecoder.length; i++) {
            if (vertexRecoder[i] < 97)
                return false;
        }
        return true;

    }

    private void tick(char[] vertexRecoder, char a) {
        for (int i = 0; i < vertexRecoder.length; i++) {
            if (vertexRecoder[i] == a)
                vertexRecoder[i] = (char) ((int) vertexRecoder[i] + 32);
        }
    }

    private boolean checkIsLoop(char[] vertexRecoder, char a, char b) {
        boolean p = false;
        boolean q = false;
        for (int i = 0; i < vertexRecoder.length; i++) {
            if (vertexRecoder[i] == a + 32)
                p = true;
            if (vertexRecoder[i] == b + 32)
                q = true;
        }
        return p & q;
    }


    //KRUSKAL
    public void miniSpan() {
        //The set of all vertexes
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


        LinkedList bag = getEdgeBag();
        PriorityQueue<String> queue = new PriorityQueue<String>(
                weightComparator) {
        };

        for (int i = 0; i < bag.size(); i++) {
            queue.add((String) bag.get(i));
        }


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

            //Automatically remove circle and
            if (!uf.connected(vertexA, vertexB)) {
                uf.union(vertexA, vertexB);
                System.out.print("Edge " + count + ": ");
                System.out.println((char) from + "-" + (char) to + " ");
                count++;
            }

        }

        return;
    }

    public int hash(char c) {
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


    public void euler() {
        if (getSize() == 0) {
            System.out.println("Sorry, the graph is empty.");
            return;
        }
        if (!isConnected()) {
            System.out.println("Sorry, the graph is not connected.");
            return;
        }
        System.out.println("There are " + countOdd() + " odd node(s) is this graph.");
        if (countOdd() != 0 && countOdd() != 2) {
            System.out.println("Sorry, there is no euler path in this graph.");
            return;
        }
        matrix = getBoolMatrix();
        int start = 0;
        for (int i = 0; i < getSize(); i++) {
            if (isOdd(matrix[i])) {
                start = i;
                break;
            }
        }
        System.out.println("Here's the euler path:");
        fleury(start);

    }


    public void fleury(int index) {
        //int start = 0;//The index of the start vertex

        if (isEmpty(matrix[index]))
            return;

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
                //Is a bridge

                System.out.println("From " + hashBack(index) + " to " + hashBack(i));
                matrix[index][i] = 0;
                matrix[i][index] = 0;
                fleury(i);
                return;

            }

        }


    }


    public String hashBack(int i) {
        Vertex v = (Vertex) vertexList.get(i);
        return v.getName();
    }

    public boolean isOdd(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++)
            sum = sum + array[i];
        return (sum % 2) != 0;
    }


    public boolean isEmpty(int[] array) {
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


        return uf.count() == 1;

    }


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

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Map {
    //Compare the weight of two edges
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
    private List vertexList;
    private List hashArray;

    public Map() {
        vertexList = new LinkedList<Vertex>();
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

    public void addVertexByString(String a) {
        a = a.replaceAll("[]| |:|;|]", "");
        //A[B,2[C,3
        String[] temp = a.split("\\[");
        //A||B,2|| C,3

        //vertice name
        String vname = temp[0];

        //v is a new vertice
        Vertex v = new Vertex(vname);

        for (int i = 1; i < temp.length; i++) {   //B,2
            String edge = temp[i];//The information of an edge

            //B||2
            String[] vw = edge.split(",");
            Edge e = new Edge(Integer.parseInt(vw[1]), vw[0]);
            v.addEdge(e);
        }
        vertexList.add(v);
    }

    public void print() {
        if (getSize() == 0) {
            System.out.println("Sorry, the map is empty.");
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
        int degree = v.getDegree();

        for (int i = 1; i < getSize(); i++) {
            v = (Vertex) getVertexList().get(i);
            if (v.getDegree() < degree)
                degree = v.getDegree();
        }
        return degree;
    }

    public int getHighDegree() {
        if (getSize() == 0) {
            return 0;
        }
        Vertex v = (Vertex) getVertexList().get(0);
        int degree = v.getDegree();

        for (int i = 1; i < getSize(); i++) {
            v = (Vertex) getVertexList().get(i);
            if (v.getDegree() > degree)
                degree = v.getDegree();
        }
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
    public Map miniSpan() {
        //The set of all vertexes
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
        Map map = new Map();
        while (uf.count() != 1) {
            String poll = queue.poll();
            char[] temp = poll.toCharArray();
            char from = temp[0];
            char to = temp[2];
            char weigh = temp[1];

            int vertexA = hash(from);
            int vertexB = hash(to);



            if (!uf.connected(vertexA, vertexB)) {
                uf.union(vertexA,vertexB);
                System.out.println(poll);

            }


        }





        return map;
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

}

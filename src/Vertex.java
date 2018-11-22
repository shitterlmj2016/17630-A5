import java.util.LinkedList;
import java.util.List;

public class Vertex {

    private String name;
    private List edgeList;

    public Vertex(String name) {
        this.name = name;
        edgeList = new LinkedList<Edge>();

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List edgeList) {
        this.edgeList = edgeList;
    }

    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }

    public int getDegree() {
        int size = edgeList.size();

        return size;
    }

    public int getCircleDegree() {
        int size = edgeList.size();
        for (int i = 0; i < edgeList.size(); i++) {
            Edge e = (Edge) edgeList.get(i);
            if (e.getVertexName().equals(name))
                size++;
        }
        return size;

    }

    //Remove possible Circles
    public Vertex removeCircle() {
        Vertex v = new Vertex(name);
        return v;
    }

    //Remove possible Parallel
    public Vertex removeParallel() {
        Vertex v = new Vertex(name);
        return v;
    }


}

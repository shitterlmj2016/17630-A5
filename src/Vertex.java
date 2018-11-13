import java.util.LinkedList;
import java.util.List;

public class Vertex {

private String name;
private List edgeList;

    public Vertex(String name) {
        this.name = name;
        edgeList=new LinkedList<Edge>();

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

    public void addEdge(Edge edge)
    {
        edgeList.add(edge);
    }

}

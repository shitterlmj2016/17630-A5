//Andrew ID: xinchenh
//Name: Xincheng Huang
//Vertex Class
//This class represents the vertex of the graph
//Each vertex contains its name and a list of its edges

import java.util.LinkedList;
import java.util.List;

public class Vertex {

    private String name; //The vertex's name
    private List edgeList; //The list of all its edges

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

    //Get the degree of the list, in which case a self loop counts one
    public int getDegree() {
        int size = edgeList.size();

        return size;
    }

    //Get the degree of the list, in which case a self loop counts two
    public int getCircleDegree() {
        //Original size
        int size = edgeList.size();

        //Self loop counts two
        for (int i = 0; i < edgeList.size(); i++) {
            Edge e = (Edge) edgeList.get(i);
            if (e.getVertexName().equals(name))//Self loop found
                size++;
        }
        return size;

    }

}

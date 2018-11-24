//17630-A5 Graph
//Andrew ID: xinchenh
//Name: Xincheng Huang
//Edge Class
//This class represents the edge of the graph
//Each edge contains the weight and the destination vertex's name


public class Edge {
    private int weight; //The weight of the edge
    private String vertexName; //the destination vertex's name

    //Construction
    public Edge(int weight, String vt) {
        this.weight = weight;
        this.vertexName = vt;
    }

    //Setter and Getter
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getVertexName() {
        return vertexName;
    }

    public void setVertexName(String vertexName) {
        this.vertexName = vertexName;
    }
}

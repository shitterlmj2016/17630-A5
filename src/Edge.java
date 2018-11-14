

public class Edge {
    private int weight;
    private String vertexName;

    public Edge(int weight, String vt) {
        this.weight = weight;
        this.vertexName = vt;
    }

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

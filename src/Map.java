import java.util.LinkedList;
import java.util.List;

public class Map {
    private List vertexList;

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
        }

        for (int i = 0; i < getSize(); i++) {
            Vertex v = (Vertex) vertexList.get(i);
            System.out.print(v.getName()+": ");
            int length=v.getEdgeList().size();

            for(int j=0;j<length;j++){
                Edge edge=(Edge)(v.getEdgeList().get(j));
                System.out.print("["+edge.getVt()+","+edge.getWeight()+"]");
                if(j!=length-1)
                {
                    System.out.print("; ");
                }
            }
            System.out.println();
        }


    }

}

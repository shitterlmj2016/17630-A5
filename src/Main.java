import java.util.LinkedList;
import java.util.List;


public class Main {


    public static void main(String[] args) {
        Map map=new Map();
        String a=("A: [B,3]; [C,4]");
        String b=("B: [A,3]; [D,1]; [E,3]; [F,6]");
        String c=("C: [A,4]; [D,5]");
        String d=("D: [C,5]; [B,1]; [E,2]");
        String e=("E: [B,3]; [F,2]; [D,2]");
        String f=("F: [E,2]; [B,6]");


        //Append the vertex to the map
        map.addVertexByString(a);
        map.addVertexByString(b);
        map.addVertexByString(c);
        map.addVertexByString(d);
        map.addVertexByString(e);
        map.addVertexByString(f);
//        Vertex v=(Vertex)map.getVertexList().get(2);
//        System.out.println(v.getName());
//        System.out.println(v.getEdgeList().size());
//        System.out.println(map.getVertexList().size());
        map.print();

    }
}

public class Main {


    public static void main(String[] args) {
        Graph map = new Graph();
       //Test case 1
        String a = ("A: [B,3]; [C,4]");
        String b = ("B: [A,3]; [D,1]; [E,3]; [F,6]");
        String c = ("C: [A,4]; [D,5]");
        String d = ("D: [C,5]; [B,1]; [E,2]");
        String e = ("E: [B,3]; [F,2]; [D,2]");
        String f = ("F: [E,2]; [B,6]");

        map.addVertexByString(a);
        map.addVertexByString(b);
        map.addVertexByString(c);
        map.addVertexByString(d);
        map.addVertexByString(e);
        map.addVertexByString(f);

//       // Testcase 2
//        String a = ("A: [B,1]; [C,1];[D,1];[E,1]");
//        String b = ("B: [A,1]; [C,1]");
//        String c = ("C: [B,1]; [A,1]; [D,1];[E,1]");
//        String d = ("D: [C,1]; [A,1]; [E,1];");
//        String e = ("E: [C,1]; [A,1]; [D,1];");
//
//
//        map.addVertexByString(a);
//        map.addVertexByString(b);
//        map.addVertexByString(c);
//        map.addVertexByString(d);
//        map.addVertexByString(e);


        int[][] matrix = map.getMatrix();
        int start =0;
        for(int i=0;i<map.getSize();i++)
        {
            if(map.isOdd(matrix[i])) {
                start = i;
                break;
            }
        }

        System.out.println();
        map.euler();
    }
}

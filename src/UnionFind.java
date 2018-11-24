//Andrew ID: xinchenh
//Name: Xincheng Huang
//UnionFind Class
//This is a tool class that checks the connectivity of the map
//The algorithm of this class is generated from the online coursera lesson I took
//Reference: https://www.coursera.org/learn/algorithms-part1/home/welcome


//The UnionFind ADT provides 3 operations
//union: Union two nodes
//connected: Check if two nodes are connected
//count: Count how many different connected parts are there.
//       At beginning, "count" should be the number of all nodes
//       Each union operation would decrease count by one
//       When all nodes are connected, "count" should be reduced to one


//The UnionFind ADT is used for the minimum spanning tree
//After all edges have been organized in a min heap, a minimum edge is popped out each time
//Check if the two vertexes of the edge has already been connected
//Connected - ignore this edge
//Not Connected - union two vertex, output this edge
//Repeat until count is reduced to one


public class UnionFind {
    private int[] parent;   // The parent of i
    private int[] size;     // The number of the children of i's root
    private int count;      // The total number of different connected parts

    //Construction
    public UnionFind(int n) {
        count = n;//There are n different nodes (in this case is the vertexes of the map) at beginning
        parent = new int[n];//At beginning, all nodes' parents should be themselves
        size = new int[n];//All should be one at beginning
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    //Return the number of all components
    public int count() {
        return count;
    }


    //Return the root of a node P
    public int find(int p) {
        validate(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    // Check if an input is legal
    // Should be 0 ~ size-1
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    //Check if two nodes are connected, that is, share the same root node.
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    //Union two nodes:
    //Notice: For optimization, this is a weighed union
    //Make sure the small root points to the larger root
    //So that union operation tooks O(log n)

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make sure smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];

        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
}


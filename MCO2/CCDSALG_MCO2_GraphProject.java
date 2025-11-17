import java.io.*;
import java.util.*;

public class CCDSALG_MCO2_GraphProject {
    private final int n; //no of nodes
    private final int m; //no of edges 
    private final ArrayList<Integer>[] adj;

    /*khloe: graph constructor and adjacency list ini*/
    @SuppressWarnings("unchecked")
    public CCDSALG_MCO2_GraphProject(int n, int m) {
        this.n = n;
        this.m = m;
        adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
    }

    /*khloe: graph loading*/
    public void addEdge(int u, int v) {
        if (u < 0 || u >= n || v < 0 || v >= n) return; 
        adj[u].add(v);
        if (u != v) adj[v].add(u);
    }

    /*khloe: prints menu*/
    private static void printMenu() {
        System.out.println("MAIN MENU");
        System.out.println("[1] Get friend list");
        System.out.println("[2] Get connection");
        System.out.println("[3] Exit");
        System.out.print("Enter your choice: ");
    }

    /*khloe: input and interactive file prompt*/
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Input file path: ");
        String path = sc.nextLine().trim();
        try {
            CCDSALG_MCO2_GraphProject gp = loadFromFile(path);
            System.out.println("Graph loaded!");
            runMenu(gp, sc);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        sc.close();
    }


    
}

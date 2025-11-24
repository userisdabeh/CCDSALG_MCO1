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

    /*matthew: loads graph from file*/
    private static CCDSALG_MCO2_GraphProject loadFromFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String[] firstLine = br.readLine().trim().split("\\s+");
        int n = Integer.parseInt(firstLine[0]);
        int m = Integer.parseInt(firstLine[1]);
        CCDSALG_MCO2_GraphProject graph = new CCDSALG_MCO2_GraphProject(n, m);
        for (int i = 0; i < m; i++) {
            String line = br.readLine();
            if (line == null) break;
            String[] edge = line.trim().split("\\s+");
            int u = Integer.parseInt(edge[0]);
            int v = Integer.parseInt(edge[1]);
            graph.addEdge(u, v);
        }
        br.close();
        return graph;
    }

    /*matthew: returns friend list for given ID*/
    public List<Integer> getFriends(int id) {
        if (id < 0 || id >= n) {
            return null;
        }
        return adj[id];
    }

    /*matthew: menu handler*/
    private static void runMenu(CCDSALG_MCO2_GraphProject gp, Scanner sc) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            
            if (choice.equals("1")) {
                System.out.print("Enter ID of person: ");
                try {
                    int id = Integer.parseInt(sc.nextLine().trim());
                    List<Integer> friends = gp.getFriends(id);
                    if (friends == null) {
                        System.out.println("Error: Person ID " + id + " does not exist in the network!");
                    } else {
                        System.out.println("Person " + id + " has " + friends.size() + " friends!");
                        if (friends.size() > 0) {
                            System.out.print("List of friends: ");
                            for (int i = 0; i < friends.size(); i++) {
                                System.out.print(friends.get(i));
                                if (i < friends.size() - 1) {
                                    System.out.print(" ");
                                }
                            }
                            System.out.println();
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid ID format!");
                }
            } else if (choice.equals("2")) {
                System.out.print("Enter ID of first person: ");
                int source = Integer.parseInt(sc.nextLine().trim());

                System.out.print("Enter ID of second person: ");
                int target = Integer.parseInt(sc.nextLine().trim());

                List<Integer> path = gp.findConnection(source, target);
                if (path == null) {
                    System.out.println("Cannot find a connection between " + source + " and " + target + ".");
                } else {
                    System.out.println("There is a connection from " + source + " to " + target + "!");
                    for (int i = 0; i < path.size() - 1; i++) {
                        System.out.println(path.get(i) + " is friends with " + path.get(i+1));
                    }
                    System.out.println();
                }
            } else if (choice.equals("3")) {
                running = false;
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice! Please enter 1, 2, or 3.");
            }
        }
    }

    /* dave: findConnection */
    public List<Integer> findConnection(int source, int target) {
        if (source < 0 || source >= n || target < 0 || target >= n) {
            return null;
        }
        
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            if (current == target) {
                List<Integer> path = new ArrayList<>();
                for (int at = target; at != -1; at = parent[at]) {
                    path.add(at);
                }
                Collections.reverse(path);
                return path;
            }

            for (int neighbor : adj[current]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                    queue.add(neighbor);
                }
            }
        }

        if (!visited[target]) {
            return null;
        }

        List<Integer> path = new ArrayList<>();
        for (int node = target; node != -1; node = parent[node]) {
            path.add(node);
        }

        Collections.reverse(path);
        return path;
    }
    
}

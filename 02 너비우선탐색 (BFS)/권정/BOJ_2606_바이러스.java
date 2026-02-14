import java.io.*;
import java.util.*;

public class Main {
        static Map<Integer, List<Integer>> graph = new HashMap<>();
    static int computer;
    static int N;
    static boolean[] visited;
    static int result = 0;

    static void bfs(int c) {
        Queue<Integer> q = new ArrayDeque<>();
        visited[c] = true;
        q.add(c);

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v: graph.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    q.add(v);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        computer = Integer.parseInt(br.readLine());
        N = Integer.parseInt(br.readLine());
        visited = new boolean[computer + 1];
        for (int u = 1; u <= computer; u++) {
            graph.put(u, new ArrayList<>());
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        bfs(1);

        for (boolean b: visited) {
            if (b) result++;
        }

        System.out.println(result - 1);
    }
}
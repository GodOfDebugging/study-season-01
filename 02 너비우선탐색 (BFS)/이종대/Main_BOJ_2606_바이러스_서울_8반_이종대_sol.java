import java.io.*;
import java.util.*;

public class Main {
	// 입출력
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    // 입력 변수
    static int numNode;
    static int numEdge;
    static List<Node>[] G;
    static class Node{
    	int to;
    	int weight;
    	Node(int to, int weight) {
    		this.to     = to;
    		this.weight = weight;
    	}
    	Node(int to) {
    		this(to, 1);
    	}
    }
    // 파생 변수
    static boolean[] visited;
    
    // O(3V+E) = O(V+E)
    static void init() throws IOException {
    	numNode = Integer.parseInt(br.readLine().trim());
	// O(V)
    	G = new List[numNode+1];
    	for (int i = 1 ; i <= numNode ; i++) G[i] = new ArrayList<Node>();
    	numEdge = Integer.parseInt(br.readLine().trim());
	// O(E)
    	for (int i = 0 ; i < numEdge ; i++) {
    		st = new StringTokenizer(br.readLine().trim(), " ");
    		int from = Integer.parseInt(st.nextToken());
    		int to   = Integer.parseInt(st.nextToken());
    		// 무향 그래프
    		G[from].add(new Node(to  ));
    		G[to]  .add(new Node(from));
    	}
	// O(V)
    	visited = new boolean[numNode+1];
    }
    
    // O(V+2E) = O(V+E)
    static void bfs(int start) {
    	Queue<Integer> q = new ArrayDeque<Integer>();
    	visited[start] = true;
    	q.offer(start);
    	while (!q.isEmpty()) {
    		int from = q.poll();
    		for(Node e : G[from]) {
    			if (!visited[e.to]) {
	    			visited[e.to] = true;
	    			q.offer(e.to);
    			}
    		}
    	}
    }
    
    // O(V)
    static int count() {
    	int cnt = 0;
    	for (int i = 1 ; i <= numNode ; i++) {
    		if(visited[i]) cnt++;
    	}
    	return cnt;
    }
    
    public static void main(String[] args) throws Exception {
    	init();
    	bfs(1);
    	System.out.println(count()-1);
    }
}
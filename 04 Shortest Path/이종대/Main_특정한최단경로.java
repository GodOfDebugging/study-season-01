package ShortestPathProblem;

import java.io.*;
import java.util.*;

public class Main_특정한최단경로 {

	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer st;
	
	private static int N, E;
	private static List<Node>[] G;
	private static int v1, v2;
	private static final int INF = 200_000_000;
	private static class Node implements Comparable<Node> {
		int from, to, weight;
		Node(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.weight, o.weight);
		}
	}
	
	private static void init() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		N = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		G = new List[N+1];
		for (int i = 1 ; i <= N ; i++) {
			G[i] = new ArrayList<>();
		}
		for (int i = 0 ; i < E ; i++) {
			st = new StringTokenizer(br.readLine().trim());
			int from   = Integer.parseInt(st.nextToken());
			int to     = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			G[from].add(new Node(from, to, weight));
			G[to].add(new Node(to, from, weight));
		}
		st = new StringTokenizer(br.readLine().trim());
		v1 = Integer.parseInt(st.nextToken());
		v2 = Integer.parseInt(st.nextToken());
	}
	
	private static int[] dijk(int src, int[] dest) {
		int[] dist = new int[N+1];
		Arrays.fill(dist, INF);
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[src] = 0; //**
		pq.offer(new Node(0, src, dist[src]));
		while(!pq.isEmpty()) {
			Node current = pq.poll();
			if(dist[current.to] < current.weight) continue;
//			dist[current.to] = current.weight;
			for (Node next : G[current.to]) {
				if(dist[next.to] > next.weight + current.weight) {
					dist[next.to] = next.weight + current.weight; //**
					pq.offer(new Node(next.from, next.to, dist[next.to]));
				}
			}
		}
		
		return dist;
	}
	
	private static int compute() {
		int ans = INF;
		int[] distFrom1 = dijk(1, new int[] {v1, v2});
		int[] distFromV1 = dijk(v1, new int[] {v2, N});
		int[] distFromV2 = dijk(v2, new int[] {v1, N});
		
		int distV1V1 = 0;
		distV1V1 = mySum(distV1V1, distFrom1[v1]);
		distV1V1 = mySum(distV1V1, distFromV1[v2]);
		distV1V1 = mySum(distV1V1, distFromV2[v1]);
		distV1V1 = mySum(distV1V1, distFromV1[N]);
		ans = Math.min(ans, distV1V1);

		int distV1V2 = 0;
		distV1V2 = mySum(distV1V2, distFrom1[v1]);
		distV1V2 = mySum(distV1V2, distFromV1[v2]);
		distV1V2 = mySum(distV1V2, distFromV2[N]);
		ans = Math.min(ans, distV1V2);
		
		int distV2V1 = 0;
		distV2V1 = mySum(distV2V1, distFrom1[v2]);
		distV2V1 = mySum(distV2V1, distFromV2[v1]);
		distV2V1 = mySum(distV2V1, distFromV1[N]);
		ans = Math.min(ans, distV2V1);

		int distV2V2 = 0;
		distV2V2 = mySum(distV2V2, distFrom1[v2]);
		distV2V2 = mySum(distV2V2, distFromV2[v1]);
		distV2V2 = mySum(distV2V2, distFromV1[v2]);
		distV2V2 = mySum(distV2V2, distFromV2[N]);
		ans = Math.min(ans, distV2V2);
		if(ans == INF ) ans = -1;
		return ans;
	}
	
	private static int mySum (int a, int b) {
		if(a == INF || b == INF) return INF;
		return a + b;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		System.out.println(compute());
	}
	
	
}

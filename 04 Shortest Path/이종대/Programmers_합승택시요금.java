package ShortestPathProblem;

import java.util.*;

public class Programmers_합승택시요금 {
	
	/*
	 *  n : the number of vertices
	 *  s : the source of the path
	 *  a, b : the two destinations
	 *  fares : the weighted undirected graph
	 */
	public int solution_FloydWarshall (int n, int s, int a, int b, int[][] fares) {
		final int INF = 20_000_000;
		
		int[][] dist = new int[n+1][n+1];
		for (int i = 1 ; i <= n ; i++) {
			Arrays.fill(dist[i], INF);
			dist[i][i] = 0;
		}
		
		for (int[] edge : fares) {
			int u = edge[0];
			int v = edge[1];
			int w = edge[2];
			dist[u][v] = Math.min(dist[u][v], w);
			dist[v][u] = Math.min(dist[v][u], w);
		}
		
		
		for (int k = 1 ; k <= n ; k++) {
			for (int i = 1 ; i <= n ; i++) {
				for (int j = 1 ; j <= n ; j++) {
					if (dist[i][k] != INF && dist[k][j] != INF) {
						dist[i][j] = Math.min(dist[i][j], dist[i][k]+dist[k][j]);
					}
				}
			}
		}
		
		int ans = INF;
		for (int k = 1 ; k <= n ; k++) {
			ans = Math.min(ans, dist[s][k]+dist[k][a]+dist[k][b]);
		}
		
		return ans;
	}
	
}

import java.io.*;
import java.util.*;

public class Main_BOJ_19238_스타트택시_서울_8반_이종대_sol {
	// 입출력
	static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static final StringBuilder out = new StringBuilder();
	static StringTokenizer st;
	// 입력 변수
	static int N, M, fuel;
	static int[][] guest;
	static int[][][] dest;
	static int[] start;
	// 파생 변수
	static final int[] dr = {-1,0,0,1};
	static final int[] dc = {0,-1,1,0};
	
	static void init() throws IOException {
		st = new StringTokenizer(br.readLine().trim(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		fuel = Integer.parseInt(st.nextToken());
		guest = new int[N][N];
		dest = new int[M+1][N][N];
		dest[0] = null;
		for (int i = 0 ; i < N ; i++) {
			st = new StringTokenizer(br.readLine().trim(), " ");
			for (int j = 0 ; j < N ; j++) {
				int pos = Integer.parseInt(st.nextToken());
				pos = pos == 1 ? -1 : pos;
				guest[i][j] = pos;
				for (int m = 1 ; m <= M ; m++) {
					dest[m][i][j] = pos;
				}
			}
		}
		st = new StringTokenizer(br.readLine().trim(), " ");
		start = new int[] {Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1};
		for (int m = 1 ; m <= M ; m++) {
			st = new StringTokenizer(br.readLine().trim(), " ");
			int guestR = Integer.parseInt(st.nextToken()) - 1;
			int guestC = Integer.parseInt(st.nextToken()) - 1;
			guest[guestR][guestC] = m;
			int destR = Integer.parseInt(st.nextToken()) - 1;
			int destC = Integer.parseInt(st.nextToken()) - 1;
			dest[m][destR][destC] = 1;
		}
	}
	
	static int bfs(int r, int c, int[][] map) {
		int cost = 0;
		PriorityQueue<int[]> candiStart = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if (o1[0] == o2[0])
					return Integer.compare(o1[1], o2[1]);
				return Integer.compare(o1[0], o2[0]);
			}
		});
		boolean[][] visited = new boolean[N][N];
		Queue<int[]> q = new ArrayDeque<>();
		visited[r][c] = true;
		q.offer(new int[] {r, c, cost});
		while (!q.isEmpty()) {
			int[] rcc = q.poll();
			r = rcc[0];
			c = rcc[1];
			if (!candiStart.isEmpty() && rcc[2] != cost) {
				int[] nextStart = candiStart.peek();
				start[0] = nextStart[0];
				start[1] = nextStart[1];
				return cost;
			}
			cost = rcc[2];
			if (map[r][c] != 0) {
				candiStart.offer(new int[] {r, c});
			}
			for (int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				if(inRange(nr, nc) && map[nr][nc] != -1 && !visited[nr][nc]) {
					visited[nr][nc] = true;
					q.offer(new int[] {nr, nc, cost+1});
				}
			}
		}
		
		if (!candiStart.isEmpty()) {
			int[] nextStart = candiStart.peek();
			start[0] = nextStart[0];
			start[1] = nextStart[1];
			return cost;
		}
		
		return 1_000_000_000;
	}
	
	static boolean inRange(int r, int c) {
		return 0 <= r&&r < N && 0 <= c&&c < N;
	}
	
	static int compute() {
		int ans = fuel;
		for (int m = 1 ; m <= M ; m++) {
			ans -= bfs(start[0], start[1], guest);
			if (ans < 0)
				return -1;
			int nDest = guest[start[0]][start[1]];
			guest[start[0]][start[1]] = 0;
			int cost = bfs(start[0], start[1], dest[nDest]);
			ans -= cost;
			if (ans < 0)
				return -1;
			else
				ans += cost*2;
		}
		
		return ans;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		out.append(compute());
		System.out.print(out);
		br.close();
	}

}
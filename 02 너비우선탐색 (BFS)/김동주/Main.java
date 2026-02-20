import java.io.*;
import java.util.*;

class Main {
	static BufferedReader br;

	static {
		try {
			System.setIn(new FileInputStream("stdin.txt"));
		} catch (FileNotFoundException e) {
			// 채점 환경인가봄.
		} finally {
			br = new BufferedReader(new InputStreamReader(System.in));
		}
	}

	static final int MAX_N = 20;
	static final int MAX_M = MAX_N * MAX_N;
	static final int UNREACHABLE = Integer.MAX_VALUE;

	static final int[] DR = { -1, 0, 0, 1 };
	static final int[] DC = { 0, -1, 1, 0 };

	static int N;
	static int M;
	static int[][][][] dist = new int[MAX_N][MAX_N][MAX_N][MAX_N];
	static boolean[][] isWall = new boolean[MAX_N][MAX_N];
	static int[] startR = new int[MAX_M];
	static int[] startC = new int[MAX_M];
	static int[] endR = new int[MAX_M];
	static int[] endC = new int[MAX_M];
	static boolean[] isVisited = new boolean[MAX_M];

	public static void main(String args[]) throws IOException {
		String[] tokens;

		tokens = br.readLine().split(" ");

		N = Integer.parseInt(tokens[0]);
		M = Integer.parseInt(tokens[1]);
		int fuel = Integer.parseInt(tokens[2]);

		for (int r = 0; r < N; r++) {
			tokens = br.readLine().split(" ");
			for (int c = 0; c < N; c++) {
				isWall[r][c] = Integer.parseInt(tokens[c]) == 1;
			}
		}

		tokens = br.readLine().split(" ");
		int currentR = Integer.parseInt(tokens[0]) - 1;
		int currentC = Integer.parseInt(tokens[1]) - 1;

		for (int i = 0; i < M; i++) {
			tokens = br.readLine().split(" ");
			isVisited[i] = false;
			startR[i] = Integer.parseInt(tokens[0]) - 1;
			startC[i] = Integer.parseInt(tokens[1]) - 1;
			endR[i] = Integer.parseInt(tokens[2]) - 1;
			endC[i] = Integer.parseInt(tokens[3]) - 1;
		}

		// All-pairs shortest path with BFS
		updateAllPairsShortestPath();

		fuel = simulateTaxi(currentR, currentC, fuel, M);

		System.out.println(fuel);
	}

	static void updateAllPairsShortestPath() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (!isWall[r][c]) {
					updateShortestPath(r, c);
				}
			}
		}
	}

	static void updateShortestPath(int sr, int sc) {
		Queue<Integer> queueX = new ArrayDeque<>();
		Queue<Integer> queueY = new ArrayDeque<>();

		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				dist[sr][sc][r][c] = UNREACHABLE;
			}
		}

		dist[sr][sc][sr][sc] = 0;
		queueX.offer(sr);
		queueY.offer(sc);

		while (!queueX.isEmpty()) {
			int r = queueX.poll();
			int c = queueY.poll();

			for (int d = 0; d < 4; d++) {
				int nr = r + DR[d];
				int nc = c + DC[d];

				if (isOutOfBound(nr, nc) || isWall[nr][nc])
					continue;

				if (dist[sr][sc][nr][nc] > dist[sr][sc][r][c] + 1) {
					dist[sr][sc][nr][nc] = dist[sr][sc][r][c] + 1;
					queueX.offer(nr);
					queueY.offer(nc);
				}
			}
		}
	}

	static boolean isOutOfBound(int r, int c) {
		return r < 0 || c < 0 || N <= r || N <= c;
	}

	static int simulateTaxi(int currentR, int currentC, int fuel, int nPassengersLeft) {
		if (nPassengersLeft == 0) {
			return fuel;
		}

		int passenger = chooseClosestPassenger(currentR, currentC);
		if (passenger < 0)
			// 승객이 없는 경우 또는 도달 불가능한 승객이 있는 경우.
			return -1;

		fuel -= dist[currentR][currentC][startR[passenger]][startC[passenger]]; // 현위치에서 승객 위치까지 이동
		fuel -= dist[startR[passenger]][startC[passenger]][endR[passenger]][endC[passenger]]; // 승객을 목적지까지 이동

		if (fuel < 0)
			// 승객을 목적지에 대려다 주기 전에 연료가 바닥나는 경우.
			return -1;

		fuel += dist[startR[passenger]][startC[passenger]][endR[passenger]][endC[passenger]] * 2;
		isVisited[passenger] = true;

		return simulateTaxi(endR[passenger], endC[passenger], fuel, nPassengersLeft - 1);
	}

	static int chooseClosestPassenger(int currentR, int currentC) {
		int closestPassenger = -1;
		int closestDist = UNREACHABLE;

		for (int i = 0; i < M; i++) {
			if (isVisited[i])
				continue;

			int distToPassenger = dist[currentR][currentC][startR[i]][startC[i]];

			if (distToPassenger == UNREACHABLE) {
				return -2; // 도달 불가능한 승객이 있는 경우.
			}

			boolean shouldChoose = ((distToPassenger < closestDist) ||
					(distToPassenger == closestDist && startR[i] < startR[closestPassenger]) ||
					(distToPassenger == closestDist && startR[i] == startR[closestPassenger]
							&& startC[i] < startC[closestPassenger]));

			if (shouldChoose) {
				closestDist = distToPassenger;
				closestPassenger = i;
			}
		}

		return closestPassenger;
	}
}

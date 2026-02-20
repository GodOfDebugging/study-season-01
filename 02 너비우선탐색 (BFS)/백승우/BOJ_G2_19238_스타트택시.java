import java.io.*;
import java.util.*;

public class BOJ_G2_19238_스타트택시 {
	static int[] dx = {-1, 0, 1, 0}, dy = {0, -1, 0, 1};
	static int N, M, F;
    static int[][] grid;
	static int[][] customerGrid;
	static int[][] destinationGrid;
    static int pointX, pointY;

    static int[] customerBfs() {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] v = new boolean[N][N];
        int dist = 0;
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        
        queue.offer(new int[] { pointX, pointY, dist });
    	v[pointX][pointY] = true;
    	
        while(!queue.isEmpty()) {
        	int[] curPoint = queue.poll();
        	int curX = curPoint[0], curY = curPoint[1], curDist = curPoint[2];
        	
        	if (dist != 0 && curDist > dist) break;
        	if (customerGrid[curX][curY] > 0) {
        		if (dist == 0) dist = curDist;
        		
            	if (minX > curX || (minX == curX && minY > curY)) {
            		minX = curX; minY = curY;
            	}
        	}

    		for (int i = 0; i < 4; i++) {
    			int newX = curX+dx[i], newY = curY+dy[i];
				if(0<=newX && newX < N && 0<=newY && 
						newY < N && grid[newX][newY] != 1 && !v[newX][newY]) {
					queue.offer(new int[] { newX, newY, curDist+1 });
					v[newX][newY] = true;
				}
    		}
		}
        if (dist == 0) return new int[] {-1, -1};
        
        int customerIdx = customerGrid[minX][minY];
        customerGrid[minX][minY] = 0;
        pointX = minX; pointY = minY;
        return new int[] { customerIdx, dist };
    }
    
    static int destinationBfs(int idx) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] v = new boolean[N][N];
        int dist = 0;
        
        queue.offer(new int[] {pointX, pointY, dist});
    	v[pointX][pointY] = true;

        
        while(!queue.isEmpty()) {
        	int[] curPoint = queue.poll();
        	int curX = curPoint[0], curY = curPoint[1], curDist = curPoint[2];
        	if (destinationGrid[curX][curY] == idx) {
        		pointX = curX; pointY = curY;
        		return curDist;
        	}
        	
    		for (int i = 0; i < 4; i++) {
    			int newX = curX+dx[i], newY = curY+dy[i];
				if(0<=newX && newX < N && 0<=newY && 
						newY < N && grid[newX][newY] != 1 && !v[newX][newY]) {
					queue.offer(new int[] {newX, newY, curDist+1});
					v[newX][newY] = true;
				}
    		}
		}
        return -1;
    }

    public static void main(String[] args) throws Exception {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    	StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        F = Integer.parseInt(st.nextToken());
        
        grid = new int[N][N];
        customerGrid = new int[N][N];
        destinationGrid = new int[N][N];
        
        for (int i = 0; i < N; i++) {
        	st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
                
        st = new StringTokenizer(br.readLine());
        pointX = Integer.parseInt(st.nextToken()); 
        pointY = Integer.parseInt(st.nextToken());
        pointX--; pointY--;
        
        for (int i = 0; i < M; i++) {
        	st = new StringTokenizer(br.readLine());
        	int j, k, l, m;
        	j = Integer.parseInt(st.nextToken());
        	k = Integer.parseInt(st.nextToken());
        	l = Integer.parseInt(st.nextToken());
        	m = Integer.parseInt(st.nextToken());
        	customerGrid[j-1][k-1] = i + 1;
        	destinationGrid[l-1][m-1] = i + 1;
		}
        
        
        for (int i = 0; i < M; i++) {
        	int[] customerData = customerBfs();
        	F -= customerData[1];
        	if (Arrays.equals(customerData, new int[] {-1, -1}) || F < 0) { F = -1; break; }
        	
        	
        	int destinationDist = destinationBfs(customerData[0]);
        	F -= destinationDist;
        	if (destinationDist == -1 || F < 0) { F = -1; break; }
        	
        	F += destinationDist * 2;
		}
        
        System.out.println(F);
        br.close();
    }
}

package bfs;

import java.io.*;
import java.util.*;

public class startTaxi {
	static int N,M,oil;
	
	static int taxiR, taxiC;
	static int[][] map;
	
	static int[][] passengerId;
	static int[] destR, destC;
	
	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,1,0,-1};
	
	static class Passenger{
		int startR;
		int startC;
		int arriveR;
		int arriveC;
		boolean isSelected;
		
		public Passenger(int startR, int startC, int arriveR, int arriveC) {
			this.startR = startR;
			this.startC = startC;
			this.arriveR = arriveR;
			this.arriveC = arriveC;
			this.isSelected = false;
		}	
	}
	static List<Passenger> p = new ArrayList<>();
	
	//원하는 지점에서 모든 행까지의 거리
	static int[][] bfs(int sr, int sc) {
		Queue<int[]> q = new ArrayDeque<>();
		int[][] dist = new int[N][N];
		for(int i=0;i<N;i++) Arrays.fill(dist[i], -1);
		
		q.offer(new int[] {sr,sc});
		dist[sr][sc]=0;
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			int r = cur[0], c = cur[1];
			
			
			for(int d=0;d<4;d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				
				if(nr<0||nr>=N||nc<0||nc>=N) continue;
				if(map[nr][nc]==1) continue;
				if(dist[nr][nc]!=-1) continue;
				
				dist[nr][nc] = dist[r][c] + 1;
				q.offer(new int[] {nr,nc});
			}
		}
		return dist;
	}
	
	// 최단 거리 승객 뽑기
	static int pickPassenger() {
		int[][] dist = bfs(taxiR,taxiC);
		
		int minDist =Integer.MAX_VALUE;
		Passenger pick = null;
		
		for(Passenger ps: p) {
			if(ps.isSelected) continue;
			
			int d1 = dist[ps.startR][ps.startC];
			if(d1 == -1) continue;
			
			if(d1<minDist) {
				minDist = d1;
				pick = ps;
			}else if(d1==minDist){
				if(ps.startR<pick.startR) {
					pick = ps;
				}else if(ps.startR==pick.startR) {
					if(ps.startC<pick.startC) pick = ps;
				}
			}
		}
		
		if(pick == null) return -1;
		
		//승겍 -> 목적지
		int[][] arriveDist = bfs(pick.startR,pick.startC);
		int d2 = arriveDist[pick.arriveR][pick.arriveC];
		if(d2 == -1) return -1;
		
		int need = minDist + d2;
		if(oil<need) return -1;
		
		oil = oil - need + 2*d2;
		
		taxiR = pick.arriveR;
        taxiC = pick.arriveC;
        pick.isSelected = true;

        return 1;
	}
	
	
	

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		oil = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];

		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(br.readLine());
		taxiR = Integer.parseInt(st.nextToken())-1;
		taxiC = Integer.parseInt(st.nextToken())-1;
	
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int startR = Integer.parseInt(st.nextToken());
			int startC = Integer.parseInt(st.nextToken());
			int arriveR = Integer.parseInt(st.nextToken());
			int arriveC = Integer.parseInt(st.nextToken());
			
			p.add(new Passenger(startR-1, startC-1, arriveR-1, arriveC-1));
		}
		
		for(int i=0;i<M;i++) {
			if(pickPassenger() == -1) {
				System.out.println(-1);
				return;
			}
		}
		
		System.out.println(oil);
	}

}

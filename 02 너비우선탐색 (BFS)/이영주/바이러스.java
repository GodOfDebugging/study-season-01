package bfs;

import java.io.*;
import java.util.*;

public class 바이러스 {
	static Map<Integer,List<Integer>> map = new HashMap<>();
	static boolean[] virus;
	
	static void bfs(int i) {
		Queue<Integer> q = new ArrayDeque<>();
		q.offer(i);
		virus[i] = true;
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			
			for(int v: map.get(cur)) {
				if(!virus[v]) {
					virus[v] = true;
					q.offer(v);
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		int couple = Integer.parseInt(br.readLine());
		virus = new boolean[N+1];
		
		for(int i=1;i<=N;i++) {
			map.put(i, new ArrayList<>());
		}
		
		for(int i=0;i<couple;i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			map.get(u).add(v);
			map.get(v).add(u);
		}
		
		bfs(1);
		
		int ans = 0;
		
		for(boolean b:virus) {
			if(b) ans += 1;
		}
		
		System.out.println(ans-1);
	}

}

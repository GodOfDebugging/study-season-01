import java.io.*;
import java.util.*;

class Main {
	
	static int[] rank;
	static void init(int N) {
		rank = new int[N + 1];
		for (int i = 0; i <= N; i++)
			rank[i] = i;	
	}
	static int find(int a) {
		if (rank[a] != a)
			rank[a] = find(rank[a]);
		return rank[a];
	}
	//O(M) 에다가 대충 아커만짤
	static void union(int a, int b) {
		int rankA = find(a);
		int rankB = find(b);
		if (rankA != rankB)
			rank[rankA] = rankB;
	}
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st ;
		StringBuilder sb = new StringBuilder();
		
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[] in = new int[3];
		
		init(N);
		for(int t =0 ; t<M;t++) {
			st = new StringTokenizer(br.readLine());
			in[0] =Integer.parseInt(st.nextToken());
			in[1] =Integer.parseInt(st.nextToken());
			in[2] =Integer.parseInt(st.nextToken());
			
			if(in[0] == 0) {
				union(in[1],in[2]);
			}
			else {
				if(find(in[1]) !=find(in[2])) {
					sb.append("NO").append("\n");
				}
				else {
					sb.append("YES").append("\n");
				}
			}
			
		}
		System.out.println(sb);
		
	}
	
}




/*

7 8
0 1 3
1 1 7
0 7 6
1 7 1
0 3 7
0 4 2
0 1 1
1 1 1
*/

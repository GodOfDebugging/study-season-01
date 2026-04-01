import java.util.*;
import java.io.*;

// 아무튼 아커만...
public class Main {

	static Map<String, String> rank ;
	static Map<String, Integer> size ;
	static StringBuilder sb;

  // O(n) 에다가 대충 아커만짤.
	static void union(String a, String b) {
		String rankA = find(a);
		String rankB = find(b);
		if (rankA != rankB) {
			rank.put(rankA, rankB);
			size.put(rankB, size.get(rankA) + size.get(rankB));
		}
		sb.append(size.get(rankB)).append("\n");
	}
  
	static String find(String a) {
		if (!rank.containsKey(a)) {
			rank.put(a, a);
			size.put(a, 1);
		}
		if (rank.get(a) != a)
			rank.put(a, find(rank.get(a))); // 경로 압축.
		return rank.get(a);
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st ;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc =0 ; tc<T;tc++) {
			sb= new StringBuilder();
			rank = new HashMap<>();
			size = new HashMap<>();
			int n = Integer.parseInt(br.readLine());
			for(int tn = 0; tn<n; tn++) {
				String[] k = br.readLine().split(" ");
				union(k[0],k[1]);
			}
			System.out.print(sb);
		}
		

	}

}

import java.io.*;
import java.util.*;

public class bj_11659_구간합구하기4 {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[] arr = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for(int i = 0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int[] prefixSum = new int[N+1];
		for(int i = 0;i<N;i++) {
			prefixSum[i+1] = prefixSum[i] + arr[i]; 
		}
		
		for(int i = 0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			int ans = prefixSum[end] - prefixSum[start-1]; 
			sb.append(ans).append("\n");
		}
		System.out.println(sb.toString());
	}	

}

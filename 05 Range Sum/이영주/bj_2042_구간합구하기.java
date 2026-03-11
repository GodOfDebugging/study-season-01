import java.io.*;
import java.util.*;

public class bj_2042_구간합구하기 {
	static int N,M,K;
	static long[] arr;
	static long[] prefixSum;
	
	static void changePrefixSum(int index, long value) {
		
		for(int i = index; i<=N;i++) {
			prefixSum[i] -= value;
		}

	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		arr = new long[N];
		prefixSum = new long[N+1];
		for(int i = 0; i<N;i++) {
			arr[i] = Long.parseLong(br.readLine());
			prefixSum[i+1] = prefixSum[i] + arr[i];
		}
		
		for(int i = 0; i<M+K;i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			long c = Long.parseLong(st.nextToken());
			
			if(a==1) {
				long diff = arr[b-1] - c;
				changePrefixSum(b,diff);
				
			}else {
				long ans = prefixSum[(int)c] - prefixSum[b-1];
				sb.append(ans).append("\n");
			}
		}
		
		System.out.println(sb.toString());
	}
	
}

package PrefixSum;

import java.io.*;
import java.util.*;

public class Main_11659_구간합구하기4 {

	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static final StringBuilder out = new StringBuilder();
	private static StringTokenizer st;
	
	private static int N, M;
	private static int[] arr;
	
	private static int[] prefixSum;
	
	private static void init() throws IOException {
		st = new StringTokenizer(br.readLine().trim(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N+1];
		prefixSum = new int[N+1];
		st = new StringTokenizer(br.readLine().trim(), " ");
		for (int i = 1 ; i <= N ; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			prefixSum[i] = prefixSum[i-1] + arr[i];
		}
	}
	
	private static void compute() throws IOException{ 
		while(M-->0) {
			st = new StringTokenizer(br.readLine().trim(), " ");
			int i = Integer.parseInt(st.nextToken());
			int j = Integer.parseInt(st.nextToken());
			out.append(prefixSum[j] - prefixSum[i-1]).append('\n');
		}
	}
	public static void main(String[] args) throws Exception{
		init();
		compute();
		System.out.print(out);
		br.close();
	}

}

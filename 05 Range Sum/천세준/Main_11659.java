package bj;

import java.util.*;
import java.io.*;

public class Main_11659 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[] numArr = new int[N+1];
		numArr[0] = 0;

		//O(N)
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i < N+1; i++) {
			int input = Integer.parseInt(st.nextToken());
			numArr[i] = numArr[i-1] + input; //현재 input 까지의 합
		}

		//O(M)
		for(int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken());
			int j = Integer.parseInt(st.nextToken());

			// O(1)
			sb.append(numArr[j] - numArr[i-1]).append("\n");
		}
		
		System.out.println(sb.toString());
	}

}

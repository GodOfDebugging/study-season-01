import java.io.*;
import java.util.*;
public class PrefixSum {

	public static void main(String[] args) throws IOException{
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));;
		StringTokenizer st = null;
		
		StringBuilder sb = new StringBuilder(); 
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int []arr = new int[N+1];
		
		
		st= new StringTokenizer(br.readLine());
		
		
		for(int i=1;i<=N;i++) {
			arr[i] =arr[i-1]+ Integer.parseInt(st.nextToken()); //O(1)time
		}
		
		for(int iter=0;iter<M;iter++) {
		st = new StringTokenizer(br.readLine());
		 
		int i = Integer.parseInt(st.nextToken());//구간 i
		int j = Integer.parseInt(st.nextToken());//구간 j
		
		//rangeSum(i,j) = arr[j] -arr[i-1];
		sb.append(arr[j]-arr[i-1]).append("\n"); //O(1) time
		}
		System.out.println(sb);
	}	

}

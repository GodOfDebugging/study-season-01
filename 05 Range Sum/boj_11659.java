import java.io.*;
import java.util.*;

class Main {
	static int[] arr;
	static int sum(int start, int end) {
		if(start == 1) return arr[end-1];
		return arr[end-1] - arr[start-1-1];
	}
    public static void main(String args[]) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader (System.in));
        
        StringTokenizer st ;
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        

        // O(N) 시간에 누적 합 계산
        for(int i = 0; i<N;i++){
        	if(i == 0) {
        		arr[i] = Integer.parseInt(st.nextToken());
        		continue;
        	}
        	arr[i] = Integer.parseInt(st.nextToken()) + arr[i-1];
        }
        
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0 ; i<M;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            sb.append(sum(a, b)).append("\n");
        }
        System.out.println(sb);

        
    }
}

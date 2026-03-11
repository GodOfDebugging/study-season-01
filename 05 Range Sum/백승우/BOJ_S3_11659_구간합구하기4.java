import java.io.*;
import java.util.*;

public class Main {
    /* O(1) */
    static int rangeSum(int i, int j, int[] prefixSum) {
        return prefixSum[j] - (i > 0 ? prefixSum[i - 1] : 0);
    }

    /* 전체 시간복잡도 - O(N) */
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] prefixSum = new int[N]; st = new StringTokenizer(br.readLine());
        
        /* O(N) */
        for (int i = 0; i < N; i++) {
            prefixSum[i] = (i > 0 ? prefixSum[i - 1] : 0) + Integer.parseInt(st.nextToken());
        }

        /* O(N) : M번 수행 */
        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken());
            int j = Integer.parseInt(st.nextToken());
            i--; j--;
            System.out.println(rangeSum(i, j, prefixSum));
        }
    }
}

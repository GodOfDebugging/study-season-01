package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class boj_11659 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 1 <= N <= 100,000, 1 <= M <= 100,000
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        String[] temp = br.readLine().split(" ");

        int[] arr = new int[N];

        // O(N)
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(temp[i]);
        }

        int[] prefixSum = new int[N+1];

        prefixSum[0] = 0;

        // O(N)
        for (int i = 1; i < N+1; i++) {
            prefixSum[i] = prefixSum[i-1] + arr[i-1];
        }

        // O(M)
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            sb.append(prefixSum[end] - prefixSum[start - 1]).append('\n');
        }

        System.out.println(sb);

        br.close();
    }
}

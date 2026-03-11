import java.io.*;
import java.util.*;

public class Main {
    static int N, M, K;
    static int[] arr;
    static int[] prefix;

    static void makeSum() {
        prefix = new int[M + 1];
        prefix[0] = arr[0];
        for (int i = 1; i <= M; i++) {
            prefix[i] = prefix[i - 1] + arr[i];
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        int a, b, c;
        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
        }
    }
}
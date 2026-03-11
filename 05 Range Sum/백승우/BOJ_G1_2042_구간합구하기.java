import java.io.*;
import java.util.*;

public class Main {
    static long[] segmentTree;

    /* O(log N) */
    static void updateTree(int node, int nodeL, int nodeR, int idx, long val) {
        if (nodeL == nodeR) {
            segmentTree[node] = val;
            return;
        }

        int mid = (nodeL + nodeR) / 2;
        if (idx <= mid) {
            updateTree(2 * node + 1, nodeL, mid, idx, val);
        } else {
            updateTree(2 * node + 2, mid + 1, nodeR, idx, val);
        }

        segmentTree[node] = segmentTree[2 * node + 1] + segmentTree[2 * node + 2];
    }

    /* O(log N) */
    static long queryTree(int node, int nodeL, int nodeR, int queryL, int queryR) {
        if (queryR < nodeL || nodeR < queryL) {
            return 0;
        }

        if (queryL <= nodeL && nodeR <= queryR) {
            return segmentTree[node];
        }

        int mid = (nodeL + nodeR) / 2;
        long sumLeft = queryTree(2 * node + 1, nodeL, mid, queryL, queryR);
        long sumRight = queryTree(2 * node + 2, mid + 1, nodeR, queryL, queryR);

        return sumLeft + sumRight;
    }

    /* O(N log N) */
    static void initialize(long[] arr) {
        for (int i = 0; i < arr.length; i++) {
            updateTree(0, 0, arr.length - 1, i, arr[i]);
        }
    }

    /* O(log N) */
    static long rangeSum(long[] arr, int i, int j) {
        return queryTree(0, 0, arr.length - 1, i, j);
    }

    /* O(log N) */
    static void update(long[] arr, int idx, long val) {
        arr[idx] = val;
        updateTree(0, 0, arr.length - 1, idx, val);
    }

    /* 전체 시간복잡도 - O(N log N) */
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        long[] nums = new long[N];

        /* O(N) */
        for (int i = 0; i < N; i++) {
            nums[i] = Long.parseLong(br.readLine());
        }

        segmentTree = new long[4 * N];

        /* O(N log N) */
        initialize(nums);

        /* O((M + K) log N) -> O(N log N) */
        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if (a == 1) {
                update(nums, b - 1, c);
            } else if (a == 2) {
                System.out.println(rangeSum(nums, b - 1, (int) c - 1));
            }
        }
    }
}

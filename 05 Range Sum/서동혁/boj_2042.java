package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class boj_2042 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 1 ≤ N ≤ 1,000,000, 1 ≤ M ≤ 10,000, 1 ≤ K ≤ 10,000
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        long[] arr = new long[N + 1];

        // O(N)
        for (int i = 1; i < N + 1; i++) arr[i] = Long.parseLong(br.readLine());

        SegmentTree segTree = new SegmentTree(N);
        segTree.init(arr, 1, 1, N);

        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if (a == 1) {
                segTree.update(1, 1, N, b, c - arr[b]);
                arr[b] = c;
            }
            else {
                sb.append(segTree.sum(1, 1, N, b, (int)c)).append("\n");
            }
        }
        System.out.println(sb);
    }

    static class SegmentTree{
        long[] tree;
        int treeSize;

        // 노드의 갯수 -> 2^(h+1)이면 충분, 더 편하게 하려면 4*N도 가능
        public SegmentTree(int arrSize) {
            // 높이 -> log2(arrSize)
            int height = (int)Math.ceil(Math.log(arrSize)/Math.log(2));

            this.treeSize = (int) Math.pow(2, height + 1);
            tree = new long[treeSize];
        }

        // node -> 트리 node번호 / start, end -> 해당 node 번호의 트리 노드에 들어갈 arr의 시작 index와 끝 index
        // O(N) -> arr의 모든 값을 넣음
        public long init(long[] arr, int node, int start, int end) {
            if (start == end) {
                return tree[node] = arr[start];
            }

            return tree[node] =
                    init(arr, node*2, start, (start+end)/2) + init(arr, node*2+1, (start+end)/2+1, end);
        }

        // idx(바꾸는 수의 index)을 포함하는 구간만 따라 내려감
        // O(logN) -> idx를 포함하는 값들만 변경하면 됨
        public void update(int node, int start, int end, int idx, long diff) {
            // idx가 범위를 아예 벗어나면 무시
            if (idx < start || idx > end) return ;

            tree[node] += diff;

            // leaf 노드가 아니면 더 깊게 들어감
            if (start != end) {
                update(node*2, start, (start+end)/2, idx, diff);
                update(node*2+1, (start+end)/2+1, end, idx, diff);
            }
        }

        // left , right -> 더하려는 arr의 범위의 시작과 끝
        // O(logN)
        public long sum(int node, int start, int end, int left, int right) {
            // 아예 안겹치면 무시
            if (left > end || right < start) return 0;

            // 완전히 겹치면 그대로 반환
            if (left <= start && end <= right) return tree[node];

            // 애매하게 걸쳐있으면 더 깊게 들어감
            return sum(node*2, start, (start+end)/2, left, right)
                    + sum(node*2+1, (start+end)/2+1, end, left, right);
        }
    }
}
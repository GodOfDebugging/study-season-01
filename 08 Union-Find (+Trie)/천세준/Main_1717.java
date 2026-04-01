package bj;

import java.io.*;
import java.util.*;

public class Main_1717 {

    static int[] parent;
    static int n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        // 0부터 n까지니까 n+1 사이즈로 배열 선언
        parent = new int[n + 1];

        // O(N) : 자기 자신을 부모로 초기화
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }

        // O(M * inverse_ackermann(N)) : M번 연산 수행
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (type == 0) {
                // O(inverse_ackermann(N)) : 대충 O(1)이라고 보면 됨
                union(a, b);
            } else {
                // O(inverse_ackermann(N))
                if (find(a) == find(b)) {
                    sb.append("YES\n");
                } else {
                    sb.append("NO\n");
                }
            }
        }
        System.out.println(sb);
        br.close();
    }

    // O(inverse_ackermann(N)) : 트리 높이에 비례하지만 경로 압축해서 거의 O(1)
    static int find(int x) {
        if (parent[x] == x) { // 루트 노드라면
            return x;
        }
        // 경로 압축 (Path Compression) - 찾으면서 부모를 다 루트로 바꿔버림
        return parent[x] = find(parent[x]);
    }

    // O(inverse_ackermann(N))
    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB) {
            parent[rootB] = rootA; // A 쪽으로 B를 붙임
        }
    }
}

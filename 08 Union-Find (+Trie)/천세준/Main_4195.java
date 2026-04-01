package bj;

import java.io.*;
import java.util.*;

public class Main_4195 {

    static int[] parent; 
    static int[] size;
    static Map<String, Integer> map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            int F = Integer.parseInt(br.readLine());
            
            // F개의 관계면 사람은 최대 2*F명
            parent = new int[F * 2];
            size = new int[F * 2];
            map = new HashMap<>();

            for (int i = 0; i < F; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                String p1 = st.nextToken();
                String p2 = st.nextToken();

                // O(1) : 문자열을 숫자로 매핑 (없으면 생성)
                int u = getIndex(p1);
                int v = getIndex(p2);

                // O(inverse_ackermann(N)) : 두 집단 합치기
                union(u, v);

                sb.append(size[find(u)]).append("\n");
            }
        }
        System.out.println(sb);
        br.close();
    }

    static int getIndex(String s) {
        if (!map.containsKey(s)) {
            // 새로운 친구 등장. 맵 사이즈를 인덱스로 활용
            int i = map.size(); 
            parent[i] = i;
            size[i] = 1;
            map.put(s, i);
        }
        return map.get(s);
    }

    // O(inverse_ackermann(N)) : 경로 압축
    static int find(int a) {
        if (parent[a] == a) {
            return a;
        }
        return parent[a] = find(parent[a]); // 경로 압축
    }

    // O(inverse_ackermann(N))
    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB) {
            parent[rootB] = rootA; // b의 루트를 a의 루트에 붙임
            size[rootA] += size[rootB]; // 사이즈 갱신
        }
    }
}

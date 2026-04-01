import java.io.*;
import java.util.*;

public class Main {
    static int[] parent;
    static int find(int v1) {
        if(parent[v1]==v1) return v1;
        return parent[v1]=find(parent[v1]);
    }
    static void union(int v1,int v2) {
        int p1 = find(v1);
        int p2 = find(v2);

        if(p1!=p2)
            parent[p1] = p2;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        for (int i = 0; i <= n; i++)
            parent[i] = i; // 초기값은 자기 자신

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if(cmd==0) union(a,b);
            else {
                if(find(a)==find(b)) sb.append("YES\n");
                else sb.append("NO\n");
            }
        }
        System.out.print(sb);
    }
}

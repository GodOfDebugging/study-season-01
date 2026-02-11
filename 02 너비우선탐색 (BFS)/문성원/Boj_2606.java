import java.util.*;
import java.io.*;
public class Boj_2606 {
    static Map<Integer,List<Integer>> adjList;
    static Map<Integer,Boolean> visited;
    static int cnt=0;
    static void bfs(int vid){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        visited = new HashMap<>();
        visited.put(vid,true);
        q.offer(vid); // O(1)
        while(!q.isEmpty()){ // O(V+E)
            int src = q.poll(); //O(1)
            for(int dst : adjList.get(src)){
                if(!visited.getOrDefault(dst,false)){ //O(1 X E) [ 비교연산 횟수 X 간선들의 개수 ]
                    visited.put(dst,true);//O(1)
                    q.offer(dst); //O(1)
                    cnt+=1;
                }
            }
        }
    //Method Total : O(V+E)
    }
    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int v = Integer.parseInt(br.readLine());
        int e = Integer.parseInt(br.readLine());

        adjList = new HashMap<>();
        for(int i=1;i<=v;i++) adjList.put(i,new ArrayList<>()); //O(V)
        visited = new HashMap<>();

        for(int i=0;i<e;i++){ // O(V)
            StringTokenizer st =new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());

            adjList.get(src).add(dst);
            adjList.get(dst).add(src);
        }
        bfs(1); //Method : O(V+E)
        System.out.println(cnt);
        //Total : O(V+E)
    }
}

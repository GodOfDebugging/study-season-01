package bj;

import java.util.*;

public class Main_2606_바이러스 {

    // 그래프 및 방문 처리를 위한 전역 변수 (HashMap 사용)
    static Map<Integer, List<Integer>> graph = new HashMap<>();
    static Map<Integer, Boolean> v = new HashMap<>();

    // 전체 시간 복잡도: O(N + M) (N: 컴퓨터 수, M: 연결 수)
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 컴퓨터의 수(V)
        int M = sc.nextInt(); // 연결된 쌍의 수(E)
        int ans = 0;

        // O(N): 그래프 인접 리스트 초기화
        for (int u = 1; u <= N; u++) {
            graph.put(u, new ArrayList<>());
        }

        // O(N): 방문 여부 맵 초기화
        for (int u = 1; u <= N; u++) {
            v.put(u, false);
        }

        // O(M): 양방향 간선 정보 입력 및 저장
        for(int i = 0; i < M; i++){
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        // BFS를 위한 큐 생성 및 시작점(1번 컴퓨터) 설정
        Queue<Integer> queue = new ArrayDeque<>();
        v.put(1, true);
        queue.add(1);

        // O(N + M): BFS 탐색으로 바이러스 이동 확인 O(V+E)
        while(!queue.isEmpty()){
            int u = queue.poll();

            // 현재 노드와 연결된 인접 노드 탐색
            for(int i : graph.get(u)){
                // 아직 방문하지 않은 컴퓨터라면 방문 처리 후 큐에 삽입
                if(!v.get(i)){
                    v.put(i, true);
                    queue.offer(i);
                    ans++; // 감염된 컴퓨터 수 증가
                }
            }
        }

        System.out.println(ans);
    }
}

import java.io.*;
import java.util.*;

class Container {
    int priority; // 우선순위
    int weight; // 무게

    Container(int p, int w) {
        this.priority = p;
        this.weight = w;
    }
}

public class Main {
    static int N, M;
    static Deque<Container> q = new LinkedList<>();
    static int[] priorityCount;
    static int result = 0;
    static Deque<Container> stack = new LinkedList<>();
    static Deque<Container> tmp = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 컨테이너 개수
        M = Integer.parseInt(st.nextToken()); // 우선순위 수
        priorityCount = new int[M + 1]; // 우선순위 컨테이너 개수 저장 배열

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            priorityCount[p]++; // 순위 인덱스에 컨테이너 개수 저장

            Container c = new Container(p, w);
            q.offer(c);
        }

				// 우선순위 낮은 순으로 적재하기 위해 for 문을 통해 현재 적재해야 할 우선순위 target 일 시에만 적재
        for (int target = M; target >= 1; target--) {
            int pCount = priorityCount[target];
            int count = 0;
						
            while(pCount > count) { // 현재 순위 컨테이너 모두 적재하기 전까지 반복
                Container c = q.peek();
                if (c.priority == target) { // 우선순위 판별
                    c = q.poll();
                    while (!stack.isEmpty()) {
                        Container s = stack.peek();
                        if (s.priority == target && s.weight < c.weight) { // 적재 컨테이너 가벼움
                            Container top = stack.pop(); // 현재 적재되어 있는거 다 빼기
                            result += top.weight;
                            tmp.push(top);
                        } else break; // 아닐 경우 반복 종료
                    }

                    stack.push(c); 
                    result += c.weight;

                    while (!tmp.isEmpty()) { // 임시 공간에 컨테이너 있을 시 모두 적
                        Container pop = tmp.pop();
                        stack.push(pop);
                        result += pop.weight;
                    }
                    count++; // 컨테이너 적재 완료
                } else { // 우선순위 높기에 뒤로 이동
                    c = q.poll();
                    q.offer(c);
                    result += c.weight;
                }
            }
        }
        System.out.println(result);
    }
}
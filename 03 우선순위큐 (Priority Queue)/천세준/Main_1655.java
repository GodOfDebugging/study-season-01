package bj;

import java.io.*;
import java.util.*;

//전체 정수 입력 N <= 100,000
public class Main_1655 {

    //O(N log N) : 10^5 기준 대략(1,661,000)
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        Queue<Integer> maxHeap = new PriorityQueue<>((a,b) -> -Integer.compare(a,b));
        Queue<Integer> minHeap = new PriorityQueue<>();

        int N = Integer.parseInt(br.readLine());

        //O(N)
        //max -> min -> max ... 순서로 넣어야 함.
        while(N-- > 0){
            int input  = Integer.parseInt(br.readLine());

            if(maxHeap.isEmpty()) { // 둘 다 비어있을 때
                maxHeap.offer(input);
                sb.append(maxHeap.peek()).append('\n');
                continue;
            }

            //O(log N) : offer 에 사용
            if(minHeap.size() == maxHeap.size()) { // maxHeap 차례
                int check = minHeap.peek();

                //현재의 입력이 minHeap 최소보다 크면 minHeap에 넣어야함.
                if(check < input) { //minHeap에 넣어야 함.
                    int temp = minHeap.poll();
                    minHeap.offer(input);
                    maxHeap.offer(temp);
                } else {
                    maxHeap.offer(input);
                }
            }

            //O(log N) : offer 에 사용
            else { // minHeap 차례
                int check = maxHeap.peek();

                //현재의 입력이 maxHeap 최대보다 작으면 maxHeap에 넣어야함.
                if(check > input) { //maxHeap에 넣어야 함.
                    int temp = maxHeap.poll();
                    maxHeap.offer(input);
                    minHeap.offer(temp);
                } else {
                    minHeap.offer(input);
                }
            }

            sb.append(maxHeap.peek()).append('\n');
        }
        System.out.println(sb);
        br.close();
    }
}

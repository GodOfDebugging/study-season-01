import java.io.*;
import java.util.*;

class Main {
    static int N; // 배열의 크기
    static int M; // 질의의 수
    static long[] A ;

    static long[] segmentTree;

    // O(log N) 시간에 트리 업데이트
    static void updateTree(int node, int nodeL, int nodeR, int idx, long val) {
        if (nodeL == nodeR) {
            // 리프 노드 업데이트
            segmentTree[node] = val;
            return;
        }

        // 자식 노드로 내려가며 업데이트
        int mid = (nodeL + nodeR) / 2;
        if (idx <= mid) {
            updateTree(2 * node + 1, nodeL, mid, idx, val);
        } else {
            updateTree(2 * node + 2, mid + 1, nodeR, idx, val);
        }

        // 자식 노드들의 합으로 현재 노드 업데이트
        segmentTree[node] = segmentTree[2 * node + 1] + segmentTree[2 * node + 2];
    }

    // O(log N) 시간에 구간 합 계산
    static long queryTree(int node, int nodeL, int nodeR, int queryL, int queryR) {
        if (queryR < nodeL || nodeR < queryL) {
            // 범위를 벗어난 경우
            return 0; // 합에 영향을 주지 않는 값 반환
        }
        if (queryL <= nodeL && nodeR <= queryR) {
            // 범위에 완전히 포함된 경우
            return segmentTree[node];
        }

        // 자식 노드로 내려가며 합 계산
        int mid = (nodeL + nodeR) / 2;

        long sumLeft = queryTree(2 * node + 1, nodeL, mid, queryL, queryR);
        long sumRight = queryTree(2 * node + 2, mid + 1, nodeR, queryL, queryR);
        
        return sumLeft + sumRight;
    }

    // 초기 트리 구축
    // O(N log N) 시간에 트리 초기화
    static void initialize(long[] arr) {
        for (int i = 0; i < arr.length; i++) {
            updateTree(0, 0, arr.length - 1, i, arr[i]);
        }
    }


    // 외부에서 호출할 수 있는 구간 합 계산 함수
    static long rangeSum(long[] arr, int i, int j) {
        return queryTree(0, 0, arr.length - 1, i, j);
    }


    // 외부에서 호출할 수 있는 업데이트 함수
    static void update(long[] arr, int idx, Long val) {
        arr[idx] = val;
        updateTree(0, 0, arr.length - 1, idx, val);
    }

    public static void main(String args[]) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader (System.in));
        
        StringTokenizer st ;
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        A = new long[N];
        segmentTree = new long[4*N];
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        for(int i = 0; i<N;i++){
            long val = Long.parseLong(br.readLine());
            update(A,i, val);
        }
        for(int i = 0 ; i<M+K;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());
            
            if(a == 1){
                update(A, b-1, c);
            }
            else{
            	int c_int =  (int) ((c)%(Integer.MAX_VALUE));
                long result = rangeSum(A,b-1,c_int-1);
                StringBuilder sb = new StringBuilder();
                sb.append(result);
                System.out.println( sb);
            }
        }


        
    }
}

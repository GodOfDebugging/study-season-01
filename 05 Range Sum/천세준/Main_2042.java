package bj;

import java.io.*;
import java.util.*;

public class Main_2042 {

    static long[] tree;
    static int N, M, K;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        // 배운 대로 4*N 정도 사이즈로 배열 선언
        tree = new long[4*N];

        //O(N log N) : Called N times and log N for each time
        for(int i = 0; i < N; i++) {
        	long input = Long.parseLong(br.readLine());
            updateTree(0,0, N-1,i,input);
        }

        // O((M+K) log N) : M times update and K times query * log N for each
        for (int i=0; i < M+K; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            
            if(type == 1){
            	int a = Integer.parseInt(st.nextToken())-1;
                long b = Long.parseLong(st.nextToken());
                // O(log N)
                updateTree(0, 0, N-1, a,b);
            } else {
            	int a = Integer.parseInt(st.nextToken())-1;
                int b = Integer.parseInt(st.nextToken())-1;
                // O(log N)
                sb.append(queryTree(0,0,N-1, a,b)).append("\n");
            }
        }
        System.out.println(sb);
        br.close();
    }

    // O(logN) : 트리 높이 만큼
    static void updateTree(int node, int nodeL, int nodeR, int idx, long val) {
        if(nodeL == nodeR){ //리프노드라면
            tree[node] = val;
            return;
        }

        int mid = (nodeL+nodeR)/2;
        if(idx <= mid) {
            updateTree(2*node+1, nodeL, mid, idx, val);
        } else {
            updateTree(2*node+2, mid+1, nodeR, idx, val);
        }
        
        tree[node] = tree[2*node+1] + tree[2*node+2];
    }

    // O(logN) : 트리 높이에 비례?
    static long queryTree(int node, int nodeL, int nodeR, int queryL, int queryR) {
        if(queryR < nodeL || nodeR < queryL ) {
            return 0;
        }
        if (queryL <= nodeL && nodeR <= queryR) {
            return tree[node];
        }

        int mid = (nodeL+nodeR)/2;
        long sumLeft = queryTree(2*node+1, nodeL, mid, queryL, queryR);
        long sumRight = queryTree(2*node+2, mid+1, nodeR, queryL, queryR);
        return sumLeft + sumRight;
    }
    
    // 이 방식으로는 O(N)으로 초기화가 가능하다.
    static long initTree(int node, int nodeL, int nodeR) {
    	if(nodeL == nodeR) {
//    		return tree[node] = arr[nodeL]; //arr을 따로 입력 받아야 함.
    		return 0;
    	}
    	
    	int mid = (nodeL+nodeR)/2;
    	long leftSum = initTree(2*node+1, nodeL, mid);
    	long rightSum = initTree(2*node+2, mid+1, nodeR);
    	
    	return tree[node] = leftSum + rightSum;
    }
}

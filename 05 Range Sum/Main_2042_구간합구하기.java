package SegmentTree;

import java.io.*;
import java.util.*;

public class Main_2042_구간합구하기 {
	
	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static final StringBuilder out = new StringBuilder();
	private static StringTokenizer st;
	
	private static int N, M, K;
	private static long[] arr;
	
	private static void init() throws IOException {
		st = new StringTokenizer(br.readLine().trim(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new long[N];
		for (int i = 0 ;  i < N ; i++) {
			arr[i] = Long.parseLong(br.readLine().trim());
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		SegmentTree segTree = new SegmentTree(N);
		segTree.initialize(arr);
		for (int i = 0 ; i < M + K ; i++) {
			st = new StringTokenizer(br.readLine().trim(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken()) - 1;
			long c = Long.parseLong(st.nextToken());
			c = a == 2 ? c - 1 : c;
			if (a == 1) {
				segTree.update(arr, b, c);
			} else if (a == 2) {
				out.append(segTree.rangeSum(arr, b, (int)c)).append('\n');
			}
		}
		System.out.print(out);
		br.close();
	}

}

class SegmentTree {
	private long[] tree;
	public SegmentTree(int n) {
		tree = new long[4 * n];
	}
	
	/*
	 * node : 이진분할정복을 하면서 거쳐갈 트리의 경로. 기저 조건에 도달하면 리프 노드에 도착한다.
	 * start, end : 분할정복 과정에서 입력값 arr의 인덱스가 포함되는 범위의 왼쪽 끝과 오른쪽 끝.
	 * idx, val : 입력값 arr에서 값을 갱신하고 싶은 곳의 인덱스와 그 값.
	 */
	private void updateTree(int node, int start, int end, int idx, long val) {
		if (start == end) {
			tree[node] = val;
			return;
		}
		
		int mid = start + (end - start) / 2;
		if (idx <= mid) {
			updateTree(2 * node, start, mid, idx, val);
		} else {
			updateTree(2 * node + 1, mid + 1, end, idx, val);
		}
		
		tree[node] = tree[2 * node] + tree[2*node+1];
	}
	
	private long queryTree(int node, int start, int end, int queryL, int queryR) {
		if (end < queryL || queryR < start) {
			return 0;
		}
		if (queryL <= start && end <= queryR) {
			return tree[node];
		}
		
		
		int mid = start + (end - start) / 2;
		
		long sumL = queryTree(2 * node, start, mid, queryL, queryR);
		long sumR = queryTree(2 * node + 1, mid + 1, end, queryL, queryR);
		
		return sumL + sumR;
	}
	
	public void update(long[] arr, int idx, long val) {
		arr[idx] = val;
		updateTree(1, 0, arr.length - 1, idx, val);
	}
	
	public long rangeSum(long[] arr, int i, int j) {
		return queryTree(1, 0, arr.length - 1, i, j);
	}
	
	public void initialize(long[] arr) {
		for (int i = 0 ; i < arr.length ; i++) {
			updateTree(1, 0, arr.length-1, i, arr[i]);
		}
	}
	
}
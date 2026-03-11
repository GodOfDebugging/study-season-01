import java.io.*;
import java.util.*;
public class SegmentTree {
	static long[] arr; //원본 데이터를 저장하는 배열
	static long[] segmentTree;
	static int N;

	static void update(int index,long value) {
		arr[index]=value; //원본 배열 update
		updateTree(1,1,N,index,value);
	}

	static void updateTree(int node, int nodeL, int nodeR,int index,long value) {
		if(nodeL==nodeR) { //Leaf node 
			segmentTree[node] = value;
			return;
		}

		int middle = (nodeL+nodeR)/2;

		if(index<=middle) updateTree(2*node,nodeL,middle,index,value);  //왼쪽 트리를 탄다. O(logN)
		else updateTree(2*node+1,middle+1,nodeR,index,value);           //오른쪽 트리를 탄다.O(logN)
		
    //재귀에서 빠져나온 직후, 갱신이 완료된 자식들을 이용해 자신 또한 갱신
		segmentTree[node]  = segmentTree[2*node] + segmentTree[2*node+1]; 
    // Total: O(2*logN) = O(logN)
	}

	static long rangeSum(int a,int b) {
		return queryTree(1, 1, N, a, b);
	}
	static long queryTree(int node,int nodeL,int nodeR, int queryL,int queryR) {
		if(queryL>nodeR||nodeL>queryR) return 0; //범위를 범어나는 경우 결과에 영향을 미치지 않는 0을 리턴
		
		if(queryL<=nodeL && nodeR<=queryR) { // 완전하게 범위에 들어올때 segmentTree에 저장된 값을 리턴
			return segmentTree[node];
		}

		int middle = (nodeL + nodeR)/2;
		long sumLeft = queryTree(2*node, nodeL, middle, queryL, queryR);       //왼쪽 트리를 탄다. O(logN)
		long sumRight = queryTree(2*node+1, middle+1, nodeR, queryL, queryR);  //오른쪽 트리를 탄다. O(logN)

		return sumLeft + sumRight;
     // Total: O(2*logN) = O(logN)
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
	
		arr =new long[N+1];
		segmentTree = new long[4*N];

		for(int i=1;i<=N;i++) {
			long input = Long.parseLong(br.readLine());
			update(i,input);
		}

		for(int i=0;i<M+K;i++) {
			st =new StringTokenizer(br.readLine());

			int opt = Integer.parseInt(st.nextToken());
			if(opt==1) {//update
				int index = Integer.parseInt(st.nextToken());
				long value = Long.parseLong(st.nextToken());
				update(index,value);
			}
			else { //print rangeSum
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				System.out.println(rangeSum(a,b));
			}
		}
	}
}

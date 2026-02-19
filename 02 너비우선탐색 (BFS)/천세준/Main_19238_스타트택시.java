package bj;

import java.io.*;
import java.util.*;

public class Main_19238_스타트택시 {
	
	static int N,M, cusNum=1;
	static int[][] map;
	static int[] di={-1,1,0,0}, dj={0,0,-1,1};

	// 전체 시간 복잡도: O(M * N^2)
	// - 최대 M명의 손님에 대해 매번 BFS(O(N^2))를 수행
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); //격자 크기 (N)
		M = Integer.parseInt(st.nextToken()); //손님 수 (M)
		int gas = Integer.parseInt(st.nextToken());

		// O(N^2): 격자 지도 정보 입력 (0: 빈칸, 1: 벽)
		map = new int[N][N];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 택시 초기 위치 설정
		st = new StringTokenizer(br.readLine());
		int startI = Integer.parseInt(st.nextToken())-1;
		int startJ = Integer.parseInt(st.nextToken())-1;
		Taxi taxi = new Taxi(startI, startJ, gas);

		// O(M): 손님 정보 입력 및 위치를 Key로 하는 맵 구성
		Map<Integer,Customer> customers = new HashMap<>();
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int ci = Integer.parseInt(st.nextToken())-1;
			int cj = Integer.parseInt(st.nextToken())-1;
			int gi = Integer.parseInt(st.nextToken())-1;
			int gj = Integer.parseInt(st.nextToken())-1;
			// 손님 객체를 찾기 편하게 key:좌표값(행*100+열), value:객체 로 map으로 구현
			customers.put(ci*100+cj, new Customer(ci, cj, gi, gj));
			map[ci][cj] = 2; //맵에는 손님 위치를 2로 표현
		}

		int customerCnt = 0;
		boolean flag = true;

		// O(M * N^2): 모든 손님을 처리할 때까지 반복
		while(customerCnt < M) {
			// 1. 현재 위치에서 가장 가까운 손님 찾기
			Customer customer = findNearestCustomer(taxi, customers);

			if(customer == null) { // 손님에게 갈 수 없거나 연료가 부족한 경우
				flag = false;
				break;
			}

			// 2. 찾아낸 손님의 목적지로 이동
			if(!moveToDestination(taxi, customer)) { // 목적지 도달 불가 또는 연료 부족
				flag = false;
				break;
			}
			customerCnt++;
		}
		
		System.out.println(flag?taxi.gas:-1);
		br.close();
	}

	// O(N^2): BFS를 이용한 최단 거리 손님 탐색 (최악 맵 전체를 보기에)
	// 조건: 1. 가장 가까운 거리, 2. 행 번호 최소, 3. 열 번호 최소
	// 생각할 것들 1. 가까운 손님 찾음(1명), 2. 가까운 손님이 여러명, 3. 가스 없음, 4. 손님에게 길 연결 X
	static Customer findNearestCustomer(Taxi taxi, Map<Integer,Customer> customers) {
		ArrayDeque<int[]> q = new ArrayDeque<>(); //BFS 큐
		List<Customer> l = new ArrayList<>(); //손님 후보 리스트
		boolean[][] v = new boolean[N][N]; //BFS 방문 처리

		// 택시 시작점 넣기
		int startI = taxi.i, startJ = taxi.j;
		v[startI][startJ] = true;
		q.offer(new int[] {startI, startJ, 0});
		
		int usedGas = 0;
		boolean found = false;

		while(!q.isEmpty() && !found) {
			int size = q.size();
			// 동일 거리(depth)에 있는 손님들을 한꺼번에 확인
			for(int s=0; s<size; s++) {
				int[] ij = q.poll();
				int i = ij[0];
				int j = ij[1];
				int depth = ij[2];
				
				// 3. 가스 다씀
				if(taxi.gas<depth) return null;

				// 1,2. 손님 발견 시 후보 리스트에 추가
				if(map[i][j] == 2) {
					l.add(customers.get(i*100+j));
					found = true;
					usedGas = depth;
				}
				
				for(int d=0; d<4; d++) {
					int ni = i+di[d];
					int nj = j+dj[d];
					if(check(ni,nj) && !v[ni][nj] && map[ni][nj]!=1) {
						v[ni][nj] = true;
						q.offer(new int[] {ni,nj, depth+1});
					}
				}
			}
			
		}

		// 4. 손님에게 닿을 수 없음
		if(l.isEmpty()) return null;

		// O(M log M): 후보 손님들 중 우선순위에 따라 정렬
		Collections.sort(l);
		Customer c = l.get(0);

		//택시 현재 위치를 손님 위치로 옮기기
		taxi.i = c.ci;
		taxi.j = c.cj;
		taxi.gas -= usedGas;
		map[c.ci][c.cj] = 0; //태운 손님 map 에서 없애기
		return c;
	}

	// O(N^2): 손님을 태우고 목적지까지 이동 (최악 맵 전체를 보기에)
	// 1. 위치 도착, 2. 가스 없어서 도착 X 3.길이 없어서 도착 X
	static boolean moveToDestination(Taxi taxi, Customer customer) {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		boolean[][] v = new boolean[N][N];
		
		int startI = taxi.i, startJ = taxi.j;
		int goalI = customer.gi, goalJ = customer.gj;
		v[startI][startJ] = true;
		q.offer(new int[] {startI, startJ, 0});
		
		int usedGas = 0;
		boolean isArrived = false;

		while(!q.isEmpty() && !isArrived) {
			int[] ij = q.poll();
			int i = ij[0];
			int j = ij[1];
			int depth = ij[2];
			
			// 2. 가스 다 씀
			if(taxi.gas<depth) {
				return false;
			}
			
			// 1. 위치 도착
			if(i==goalI && j==goalJ) {
				usedGas = depth;
				isArrived = true;
				break;
			}

			for(int d=0; d<4; d++) {
				int ni = i+di[d];
				int nj = j+dj[d];
				if(check(ni,nj) && !v[ni][nj] && map[ni][nj]!=1) {
					v[ni][nj] = true;
					q.offer(new int[] {ni,nj, depth+1});
				}
			}
		}
		
		if(isArrived) {
			taxi.i = customer.gi; taxi.j = customer.gj; //택시 위치 이동
			taxi.gas += usedGas; //가스 충전
			return true;
		} else {
			//3. 길 없음
			return false;
		}
		
	}
	
	static boolean check(int i, int j) {
		return 0<=i && i<N && 0<=j && j<N;
	}
	
	static class Taxi {
		int i;
		int j;
		int gas;
		int len;
		
		public Taxi(int i, int j, int gas) {
			this.i = i;
			this.j = j;
			this.gas = gas;
			len = 0;
		}
	}
	
	static class Customer implements Comparable<Customer> {
		int ci;
		int cj;
		int gi;
		int gj;
		int cNum;
		
		public Customer(int ci, int cj, int gi, int gj) {
			this.ci = ci;
			this.cj = cj;
			this.gi = gi;
			this.gj = gj;
			cNum = cusNum++;
		}

		// 행,열 기준 정렬(문제 조건 따라)
		@Override
		public int compareTo(Customer o) {
			if(Integer.compare(ci, o.ci) == 0) {
				return Integer.compare(cj, o.cj);
			}
			return Integer.compare(ci, o.ci);
		}
		
	}
}

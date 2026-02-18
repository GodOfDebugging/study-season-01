package baekjoon.month02.week02.day0215.problem19238;
import java.io.*;
import java.util.*;
/*
 * 1. BFS
 * 2. 최단거리로 움직여야함, 현재 위치에서 가장 거리가 짧은 승객을 태움, 만약 거리가 같으면? y,x 순으로
 *
 * 2.1 거리 최신화
 * 2.2 해당 정점의 목표 고객이 존재하는가?
 *
 * 3.1 한 칸 움직일 떄마다 1씩 까임,
 * 3.2한 승객을 목적지로 이동시키면, 이동한 연료의 두 배가 충전됌.
 * 3.3 이동 중 연료가 바닥나면 종료됌(실패), 단 목적지와 동시에 연료가 바닥나면 성공
 */
class Passenger{
    int sy,sx; //고객의 시작 좌표
    int ey,ex; //고객의 목표 좌표
    boolean isArrived;//도착 여부
    public Passenger(int sy,int sx,int ey,int ex){
        this.sy = sy;this.sx = sx;this.ey = ey;this.ex = ex;
        this.isArrived=false;
    }
}
public class Main {
    static StringBuilder sb = new StringBuilder();
    static int[] dy={-1,0,1,0};
    static int[] dx={0,-1,0,1};
    static int N,M,fuel;
    static int[][] board;
    static int[] taxi;
    static Passenger[] passenger;
    static void solve() {
        for(int i=0;i<M;i++){
            int[][] dict = bfs(taxi[0], taxi[1]);
            int targetIndex = findClosestPassengerIndex(dict);

            if(targetIndex==-1){ //현재 위치에서 도달 불가능한 경우
                sb.append(-1);
                return;
            }
            int sy = passenger[targetIndex].sy; //가장 가까운 승객의 y 좌표
            int sx = passenger[targetIndex].sx; //가장 가까운 승객의 x 좌표
            if (dict[sy][sx]==Integer.MAX_VALUE||fuel < dict[sy][sx]) { //도달 불가능하거나 연료가 부족한지 체크
                sb.append(-1);
                return;
            }
            fuel -= dict[sy][sx]; //연료 감소 시키기
            //택시 이동시키기
            taxi[0] = sy;
            taxi[1] = sx;

            dict = bfs(taxi[0], taxi[1]); //승객을 태운 시점에서 bfs 수행
            int ey = passenger[targetIndex].ey;//승객의 목표 y 좌표
            int ex = passenger[targetIndex].ex;//승객의 목표 x 좌표
            if (dict[ey][ex]==Integer.MAX_VALUE||fuel < dict[ey][ex]) { //도달 불가능하거나 연료가 부족한지 체크
                sb.append(-1);
                return;
            }
            passenger[targetIndex].isArrived=true; //고객의 상태 update
            fuel += dict[ey][ex]; // fule - dict[ey][ex] + 2*diect[ey][ex], 연료 update
            //택시 이동시기키
            taxi[0] = ey;
            taxi[1] = ex;
        }
        //정답 출력
        sb.append(fuel);
    }
    static int[][] bfs(int y,int x){
        int[][] dict = new int[N][N]; //거리 저장 배열
        for(int i=0;i<N;i++) //@O(N^2)
            Arrays.fill(dict[i],Integer.MAX_VALUE);
        dict[y][x]=0;
        //BFS 진행
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{y,x});
        while(!q.isEmpty()){// @최악의 경우 O(N^2)
            int[] info = q.poll();
            int cy = info[0];
            int cx = info[1];
            for(int i=0;i<4;i++){
                int ny = cy + dy[i];
                int nx = cx + dx[i];
                if(ny>=0 && ny<N && nx>=0 && nx<N && board[ny][nx]==0){ //범위 + 도달 가능한지 체크
                    if(dict[ny][nx]>dict[cy][cx]+1){
                        dict[ny][nx] = dict[cy][cx]+1;
                        q.offer(new int[]{ny,nx});
                    }
                }
            }
        }
        //거리 배열 리턴
        return dict;
    }
    static int findClosestPassengerIndex(int[][]dict){
        int minIndex=-1;
        int minDistance=Integer.MAX_VALUE;
        for(int i=0;i<M;i++){//@O(M)
            //도착 상태가 아닌 승객들 중에서 가장 가까운 승객의 인덱스를 찾음
            if(!passenger[i].isArrived&&minDistance>dict[passenger[i].sy][passenger[i].sx]){
                if(minDistance==dict[passenger[i].sy][passenger[i].sx]) continue; //거리가 같으면 행번호와 열번호가 작은것을 선택하게끔

                minDistance = dict[passenger[i].sy][passenger[i].sx];
                minIndex = i;
            }
        }
        return minIndex;
    }
    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        fuel = Integer.parseInt(st.nextToken());
        //지도 정보 입력받기
        board = new int[N][N];
        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;j++){
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        //택시 시작 위치 정보 입력받기
        st =new StringTokenizer(br.readLine());
        taxi = new int[2];
        taxi[0] = Integer.parseInt(st.nextToken())-1;
        taxi[1] = Integer.parseInt(st.nextToken())-1;
        //승객 정보 입력받기
        passenger = new Passenger[M];
        for(int i=0;i<M;i++){
            st =new StringTokenizer(br.readLine());
            int sy = Integer.parseInt(st.nextToken())-1;
            int sx = Integer.parseInt(st.nextToken())-1;
            int ey = Integer.parseInt(st.nextToken())-1;
            int ex = Integer.parseInt(st.nextToken())-1;
            passenger[i] = new Passenger(sy,sx,ey,ex);
        }
        // 좌표 값을 기준으로 오름차순 정렬
        Arrays.sort(passenger,(o1,o2)->{
            if(o1.sy==o2.sy) return Integer.compare(o1.sx,o2.sx); //행번호가 같으면 열 값으로 비교
            return Integer.compare(o1.sy,o2.sy); //행번호로 비교
        });
        solve();
        System.out.print(sb);

    }
}

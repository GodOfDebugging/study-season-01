import java.io.*;
import java.util.*;

public class Main {
    static class Passenger {
        int startRow;
        int startCol;
        int endRow;
        int endCol;

        Passenger(int startCol, int startRow, int endCol, int endRow) {
            this.startRow = startRow;
            this.startCol = startCol;
            this.endRow = endRow;
            this.endCol = endCol;
        }
    }
    static int N, M, fuel;
    static int[][] map;
    static int row, col;
    static Passenger[] passengers;
    static boolean[] passengerVisited;
    static int passengersCount;
    static int[] dr = {0, 0, -1, 1};
    static int[] dc = {1, -1, 0, 0};

    static int moveDistance(int sC, int sR, int eC, int eR, int count) {
        // 최소 거리 구하기  s -> e
        // 이걸로 내가 현재 어떤 승객 태우러 가는지 판별 count대로 연료가 소비되고 count * 2 만큼 충전
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] v = new boolean[N][N];
        q.offer(new int[]{sC, sR, count});
        v[sC][sR] = true;

        if (sC == eC && sR == eR) {
            return 0;
        }

        while (!q.isEmpty()) {
            int[] p = q.poll();
            int c = p[0]; int r = p[1]; int recentCount = p[2];
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (0 <= nr && nr < N && 0 <= nc && nc < N) {
                    if (nr == eR && nc == eC) { // 도착완료
                        return recentCount;
                    }
                    if (!v[nc][nr] && map[nc][nr] == 0) {
                        q.offer(new int[]{nc, nr, recentCount + 1});
                        v[nc][nr] = true;
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        fuel = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        col = Integer.parseInt(st.nextToken()) - 1;
        row = Integer.parseInt(st.nextToken()) - 1;

        passengers = new Passenger[M];
        passengerVisited = new boolean[M];
        passengersCount = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int startCol = Integer.parseInt(st.nextToken()) - 1;
            int startRow = Integer.parseInt(st.nextToken()) - 1;
            int endCol = Integer.parseInt(st.nextToken()) - 1;
            int endRow = Integer.parseInt(st.nextToken()) - 1;
            passengers[i] = new Passenger(startCol, startRow, endCol, endRow);
            passengersCount++;
        }

        // 1. 현재 passengers 안에서 최단거리 찾기
        // 2. 찾으면 그 passengers[i]의 endRow, endCol로 현재 위치 바꾸기
        // 3. 연료 - 최단거리 + (최단거리 * 2)
        // 4. 연료값이 음수가 되었다면 종료
        // 5. 모든 승객 다 태우면 끝
        int pCount = 0;
        while (true) {
            if (passengersCount == pCount) break;
            int minIdx = 0, minMove = Integer.MAX_VALUE;
            for (int i = M - 1; i >= 0; i--) {
                if (!passengerVisited[i]) {
                    int recentMove = moveDistance(col, row, passengers[i].startCol, passengers[i].startRow, 1);
                    if (recentMove == -1) {
                        System.out.println(-1);
                        return;
                    }
                    if (recentMove < minMove) {
                        minMove = recentMove;
                        minIdx = i;
                    } else if (recentMove == minMove) {
                        if (passengers[minIdx].startCol > passengers[i].startCol) {
//                            System.out.println(passengers[minIdx].startRow + " " + passengers[i].startRow + " " + minIdx + " " + i);
                            minIdx = i;
                        } else if (passengers[minIdx].startCol == passengers[i].startCol) {
                            if (passengers[minIdx].startRow > passengers[i].startRow) {
                                minIdx = i;
                            }
                        }
                    }
                }
            }

//            System.out.println(minIdx);
            row = passengers[minIdx].startRow; col = passengers[minIdx].startCol;
            fuel -= minMove;
            if (fuel < 0) {
                System.out.println(-1);
                return;
            }
            int count = moveDistance(col, row, passengers[minIdx].endCol, passengers[minIdx].endRow, 1);
            if (count == -1) {
                System.out.println(-1);
                return;
            }
            fuel -= count;
            if (fuel < 0) {
                System.out.println(-1);
                return;
            }
            fuel += (count * 2);
            row = passengers[minIdx].endRow; col = passengers[minIdx].endCol;
            passengerVisited[minIdx] = true;
            pCount++;
        }
        System.out.println(fuel);
    }
}

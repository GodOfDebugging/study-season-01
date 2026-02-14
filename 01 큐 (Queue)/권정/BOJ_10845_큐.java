import java.io.*;
import java.util.*;

public class Main {
    static final int MAX_N = 10000;
    static int[] arr = new int[MAX_N];
    static int front = 0;
    static int rear = 0;

    static int size() {
        return rear - front;
    }

    static void push(int n) {
        arr[rear++] = n;
    }

    static int pop() {
        return arr[front++];
    }

    static boolean empty() {
        if (size() <= 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            if (s.equals("push")) {
                int n = Integer.parseInt(st.nextToken());
                push(n);
            } else if (s.equals("pop")) {
                if ((empty())) System.out.println(-1);
                else System.out.println(pop());
            } else if (s.equals("size")) System.out.println(size());
            else if (s.equals("empty")) {
                if ((empty())) System.out.println(1);
                else System.out.println(0);
            } else if (s.equals("front")) {
                if ((empty())) System.out.println(-1);
                else System.out.println(arr[front]);
            } else if (s.equals("back")) {
                if ((empty())) System.out.println(-1);
                else System.out.println(arr[rear - 1]);
            }
        }
    }
}
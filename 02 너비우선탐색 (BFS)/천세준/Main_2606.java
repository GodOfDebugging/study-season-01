package bj;

import java.util.*;
import java.io.*;

public class Main_2606 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int computerNumber = Integer.parseInt(br.readLine());
        Node[] nodes = new Node[computerNumber + 1];

        for(int i = 1; i <= computerNumber; i++){
            nodes[i] = new Node(i);
        }

        int edgeNumber = Integer.parseInt(br.readLine());

        for(int i = 0; i < edgeNumber; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            nodes[a].connectedNodeNumber.add(b);
            nodes[b].connectedNodeNumber.add(a);
        }

        ArrayDeque<Node> queue = new ArrayDeque<>();
        HashSet<Integer> visited = new HashSet<>();
        queue.offer(nodes[1]);

        while(!queue.isEmpty()){
            Node node = queue.poll();
            visited.add(node.nodeNumber);
            for(int nextNode : node.connectedNodeNumber) {
                if(!visited.contains(nextNode)){
                    queue.offer(nodes[nextNode]);
                }
            }
        }

        System.out.println(visited.size()-1);
    }

    static class Node {
        int nodeNumber;
        HashSet<Integer> connectedNodeNumber = new HashSet<>();

        public Node(int nodeNumber) {
            this.nodeNumber = nodeNumber;
        }
    }

}


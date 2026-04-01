import java.io.*;
import java.util.*;

class Node {
    char ch;//해당 노드가 저장하고 있는 알파벳입니다.
    List<Node> child;//해당 노드가 가지고있는 자식 노드들의 List입니다.

    //초기화
    public Node(char ch) {
        this.ch = ch;
        this.child = new ArrayList<>();
    }
    /**
     * List를 순회하며 찾고자 하는 알파벳이 존재하면 해당 노드를 리턴합니다.
     * 그렇지 않으면 null을 리턴합니다.
     * @param ch : 찾고자하는 알파벳
     * @return : targetNode or null
     **/
    public Node find(char ch) {
        for (Node node : child) {
            if (node.ch == ch) return node;
        }
        return null;
    }

    /**
     * 현재 순회중인 노드의 List에 node를 추가합니다.
     * @param node : 현재 순회중인 노드의 List에 추가할 노드
     */
    public void addNode(Node node) {
        child.add(node);
    }
}

class Tree {
    Node root;//최상위 노드
    int cnt; //정답을 출력하기 위한 cnt

    public Tree() {
        this.root = new Node('#'); //더미 노드.
        this.cnt = 0;
    }

    /**
     * 순회를 하면서 Tree를 구축합니다.
     * @param node : 현재 순회중인 노드
     * @param str : 입력으로 들어온 문자열
     * @param index :현재 순회중인 str(문자열)의 index
     */
    public void construct(Node node, String str, int index) {
        if (index == str.length())//더이상 검사할 알파벳이 존재하지 않을 때, Tree 구축을 멈춤
            return;

        Node childNode = node.find(str.charAt(index)); //현재 노드의 자식 중에 해당 알파벳이 존재하는지 검사

        if (childNode != null) { //존재하는 경우
            construct(childNode, str, index + 1);//재귀호출
        }
        else {//존자하지 않는 경우
            Node newNode = new Node(str.charAt(index));//노드 생성
            node.addNode(newNode);//추가
            construct(newNode, str, index + 1);//추가 후 재귀호출
        }
    }

    /**
     * 입력으로 들어온 문자열이 prefix인지 검사합니다.
     * @param node : 현재 순회중인 노드
     * @param str : 입력으로 들어온 문자열
     * @param index : 현재 순회중인 str(문자열)의 index
     */
    public void isPrefix(Node node, String str, int index) {
        if (index == str.length()) {////끝까지 검사를 마친 경우, 즉 str이 prefix인 경우
            cnt += 1;
            return;
        }
        Node childNode = node.find(str.charAt(index));
        if (childNode != null) {//List에 존재할때만 순회를 진행
            isPrefix(childNode, str, index + 1);
        }

    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        //트리 구축
        Tree tree = new Tree();
        for (int i = 0; i < N; i++) {
            String word = br.readLine();
            tree.construct(tree.root, word, 0);

        }
        //트리 탐색
        for (int i = 0; i < M; i++) {
            String word = br.readLine();
            tree.isPrefix(tree.root, word, 0);
        }
        System.out.println(tree.cnt);
    }
}

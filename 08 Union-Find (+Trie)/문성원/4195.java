import java.io.*;
import java.util.*;
public class Main {
    static Map<String,Integer>size;
    static Map<String,String> parent;
    static String find(String name) {
        //자기 자신밖에 없을때
        if(!parent.containsKey(name)){
            parent.put(name, name); // 자기 자신
            size.put(name,1);// size 초기화
        }

        //자신이 해당 집합의 대표값일때,
        if(parent.get(name)==name)
            return name;

        String p = find(parent.get(name));
        parent.put(name, p);//경로 압축
        return p;
    }

    static int union(String name1, String name2) {
        String p1  = find(name1);
        String p2 = find(name2);

        if(p1!=p2) {//p2의 대표값을 p1으로 update
            parent.put(p2, p1);
            size.put(p1, size.get(p1)+size.get(p2)); //p1을 대표로 하는 집합의 size update
        }

        return size.get(p1);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb =new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        while(T-->0) {
            int N = Integer.parseInt(br.readLine());
            size = new HashMap<>();
            parent = new HashMap<>();

            for(int i=0;i<N;i++) {
                StringTokenizer st =new StringTokenizer(br.readLine());
                String name1 = st.nextToken();
                String name2 = st.nextToken();
                sb.append(union(name1,name2)).append("\n");
            }
        }
        System.out.println(sb);
    }
}

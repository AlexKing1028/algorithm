import com.sun.tools.javac.jvm.Gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wesley on 2017/4/4.
 */
public class Main {
    List<String> generate(int num, int size){
        List<String> ls = new ArrayList<>();
        Random random = new Random();
        char next;
        for (int i=0; i<num; i++){
            StringBuilder sb = new StringBuilder();
            for (int j=0; j< size; j++) {
                switch (random.nextInt(4)) {
                    case 0:
                        next = 'A';
                        break;
                    case 1:
                        next = 'G';
                        break;
                    case 2:
                        next = 'T';
                        break;
                    default:
                        next = 'C';
                        break;
                }
                sb.append(next);
            }
            ls.add(sb.toString());
        }
        return ls;
    }

    public static void main(String[] args) {
        int num = 50;
        int size = 100;
        int table_size = 100;
        List<String> ls = new Main().generate(num, size);
        MyHashTable ht = new MyHashTable(table_size, 5);
        int collision = 0;
        for (String s: ls){
            if (!ht.put(s)){
                collision++;
            };
        }
        System.out.println("collision ratio: "+ (collision*1.0/num));
    }
}





















import java.util.List;

/**
 * Created by wesley on 2017/3/4.
 */
public class Main {
    public static void main(String[] args){
        List<String> ls = Reader.read("HW1FILE1.txt");

        int size = ls.size();
        for (int i=0;i<size;i++) {
            for (int j = i + 1; j < size; j++) {
                System.out.println(i + " compared with " + j);
                // ------  global -------
                Alignment alignment = GlobalAlign.search(ls.get(i), ls.get(j));
                System.out.println("global");
                System.out.println(alignment);
//                System.out.println(alignment.a.length());

                // ------ local ------
                System.out.println("local");
                alignment = LocalAlign.search(ls.get(i), ls.get(j));
                System.out.println(alignment);
//                System.out.println(alignment.a.length());
            }
        }
    }
}

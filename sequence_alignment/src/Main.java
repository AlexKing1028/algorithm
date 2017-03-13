import java.util.List;

/**
 * Created by wesley on 2017/3/4.
 */
public class Main {
    public static void main(String[] args){
        List<String> ls = Reader.read("HW1FILE1.txt");

        int size = ls.size();
        long time1;
        for (int i=0;i<size;i++) {
            for (int j = i + 1; j < size; j++) {
                System.out.println(i + " compared with " + j);
                // ------  global -------
                time1 = System.currentTimeMillis();
                Alignment alignment = BandedGlobalAlign.search(ls.get(i), ls.get(j));
                time1 -= System.currentTimeMillis();
                System.out.println("global -- " + -time1);
                System.out.println(alignment);
//                System.out.println(alignment.a.length());

            }
        }
    }
}

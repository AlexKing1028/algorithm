import java.util.List;

/**
 * Created by wesley on 2017/3/4.
 */
public class Main {
    public static void main(String[] args){
        // ------  global -------
        List<String> ls = Reader.read("HW1FILE1.txt");
        Alignment alignment = GlobalAlign.search(ls.get(0), ls.get(2));
        System.out.println(alignment);
        System.out.println(alignment.a.length());

        // ------ local ------
        alignment = LocalAlign.search(ls.get(0), ls.get(2));
        System.out.println(alignment);
        System.out.println(alignment.a.length());
    }
}

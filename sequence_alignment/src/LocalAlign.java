import java.util.List;

/**
 * Created by wesley shi on 2017/3/3.
 */
public class LocalAlign {

    final static int MATCH = 3;
    final static int MISMATCH = -1;
    final static int INDEL = -3;

    public static Alignment search(String a, String b){
        int al = a.length();
        int bl = b.length();
        int[][] matrix = new int[al + 1][bl + 1];
        int[][] state = new int[al + 1][bl + 1];
        /*
        for (int i = 0; i <= al; i++){
            matrix[i][0] = 0;
        }
        for (int i = 0; i <= bl; i++){
            matrix[0][i] = 0;
        }
        */
        int max = 0, at = 0, bt = 0;
        for (int i = 1; i <= al; i++){
            for (int j = 1; j <= bl; j++){
                int t1 = matrix[i-1][j-1] + (match(a.charAt(i-1), b.charAt(j-1))?MATCH:MISMATCH);
                int t2 = matrix[i-1][j] + INDEL;
                int t3 = matrix[i][j-1] + INDEL;
                if (t1 >= t2 && t1 >= t3){
                    matrix[i][j] = t1;
                    state[i][j] = 0;
                } else if (t2 >= t1 && t2 >= t3) {
                    matrix[i][j] = t2;
                    state[i][j] = 1;
                } else {
                    matrix[i][j] = t3;
                    state[i][j] = 2;
                }
                if (matrix[i][j] < 0){
                    matrix[i][j] = 0;
                    state[i][j] = -1;
                }
                if (matrix[i][j] > max){
                    at = i;
                    bt = j;
                    max = matrix[i][j];
                }
            }
        }
        StringBuilder sba = new StringBuilder();
        StringBuilder sbb = new StringBuilder();
        int s;
        while ((s = state[at][bt]) != -1 && at != 0 && bt != 0){
            if (s == 0){
                sba.insert(0, a.charAt(at-1));
                sbb.insert(0, b.charAt(bt-1));
                at --; bt --;
            } else if (s == 1){
                sba.insert(0, a.charAt(at-1));
                sbb.insert(0, '-');
                at --;
            } else {
                sba.insert(0, '-');
                sbb.insert(0, b.charAt(bt-1));
                bt --;
            }
        }
        return new Alignment(sba.toString(), sbb.toString(), max);
    }

    static char gap = 'a' - 'A';
    public static boolean match(char a, char b){
        return a == b || a - b == gap || b - a == gap;
    }

    public static void main(String[] args){
        /*
        List<String> ls = Reader.read("HW1FILE1.txt");
        Alignment alignment = LocalAlign.search(ls.get(0), ls.get(2));
        System.out.println(alignment);
        */
        String a = "xxxxabecxxxxxxxxxxxcabikkkk";
        String b = "ccabcxewxcabibcd";
        Alignment alignment = LocalAlign.search(a, b);
        System.out.println(alignment);
    }
}

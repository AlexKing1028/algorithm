/**
 * Created by wesley shi on 2017/3/3.
 */
public class GlobalAlign {

    final static int MATCH = 0;
    final static int MISMATCH = 1;
    final static int INDEL = 1;

    public static Alignment search(String a, String b){
        int al = a.length();
        int bl = b.length();
        int[][] matrix = new int[al + 1][bl + 1];
        int[][] state = new int[al + 1][bl + 1];
        for (int i = 0; i <= al; i++){
            state[i][0] = 1;
            matrix[i][0] = i*INDEL;
        }
        for (int i = 0; i <= bl; i++){
            state[0][i] = 2;
            matrix[0][i] = i*INDEL;
        }
        for (int i = 1; i <= al; i++){
            for (int j = 1; j <= bl; j++){
                int t1 = matrix[i-1][j-1] + (LocalAlign.match(a.charAt(i-1), b.charAt(j-1))? MATCH: MISMATCH);
                int t2 = matrix[i-1][j] + INDEL;
                int t3 = matrix[i][j-1] + INDEL;
                if (t1 <= t2 && t1 <= t3){
                    matrix[i][j] = t1;
                    state[i][j] = 0;
                } else if (t2 <= t1 && t2 <= t3){
                    matrix[i][j] = t2;
                    state[i][j] = 1;
                } else {
                    matrix[i][j] = t3;
                    state[i][j] = 2;
                }
            }
        }
        StringBuilder sba = new StringBuilder();
        StringBuilder sbb = new StringBuilder();
        int max = matrix[al][bl];
        while (al != 0 || bl != 0){
            int s = state[al][bl];
            if (s == 0) {
                sba.insert(0, a.charAt(al-1));
                sbb.insert(0, b.charAt(bl-1));
                al --; bl --;
            } else if (s == 1){
                sba.insert(0, a.charAt(al-1));
                sbb.insert(0, '-');
                al --;
            } else {
                sba.insert(0, '-');
                sbb.insert(0, b.charAt(bl-1));
                bl --;
            }
        }
        return new Alignment(sba.toString(), sbb.toString(), max);
    }

    public static void main(String[] args){
        String a = "xxabgexetqtqtqrewqworuoqjige";
        String b = "xxcabgeeiworuoqjige";
        Alignment alignment = GlobalAlign.search(a, b);
        System.out.println(alignment);
    }
}

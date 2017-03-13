import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 2017/3/12.
 */
public class BandedGlobalAlign {

    final static int MATCH = 0;
    final static int MISMATCH = 1;
    final static int INDEL = 1;

    public static Alignment search(String a, String b){
        String m = a, n = b;
        if (a.length() > b.length()){
            m = b;
            n = a;
        }
        int mlen = m.length();
        int nlen = n.length();
        int k = 1;
        BandMatrix path;
        BandMatrix score;
        BandMatrix dist;
        while (true) {
            if (nlen - mlen > k) {
                k <<= 2;
                continue;
            }

            path = new BandMatrix(mlen + 1, nlen + 1, k);
            score = new BandMatrix(mlen + 1, nlen + 1, k);
            dist = new BandMatrix(mlen + 1, nlen + 1, k);

            for (int j = 1; j <= score.upperband; j++) {
                path.set(j, 0, 2);
                score.set(j, 0, j * INDEL);
                dist.set(j, 0, j);
            }
            for (int i = 1; i <= score.lowerband; i++) {
                path.set(0, i, 1);
                score.set(0, i, i * INDEL);
                dist.set(0, i, i);
            }

            //score.print();

            for (int i = 1; i < mlen + 1; i++) {
                int j = Math.max(1, i - score.lowerband);
                int jmax = Math.min(nlen, i + score.upperband);
                for (; j <= jmax; j++) {
                    boolean same_char = match(m.charAt(i - 1), n.charAt(j - 1));
//                int match = score.get(j-1, i-1) + (m.charAt(i-1) == n.charAt(j-1)?MATCH: MISMATCH);
                    int match = score.get(j - 1, i - 1) + (same_char ? MATCH : MISMATCH);
                    int indel_m = score.get(j, i - 1) + INDEL;
                    int indel_n = score.get(j - 1, i) + INDEL;
                    if (match <= indel_m && match <= indel_n) {
                        score.set(j, i, match);
                        path.set(j, i, 0);
                        int pre_dist = dist.get(j - 1, i - 1);
                        if (same_char) {
                            dist.set(j, i, pre_dist);
                        } else {
                            dist.set(j, i, pre_dist);
                        }
                    } else if (indel_m <= match && indel_m <= indel_n) {
                        score.set(j, i, indel_m);
                        path.set(j, i, 1);
                        dist.set(j, i, dist.get(j, i - 1) + 1);
                    } else {
                        score.set(j, i, indel_n);
                        path.set(j, i, 2);
                        dist.set(j, i, dist.get(j - 1, i) + 1);
                    }
                    //score.print();
                }
            }

            if (dist.get(nlen, mlen) <= k) {
                break;
            }else{
                k <<= 2;
            }
        }

        StringBuilder mb = new StringBuilder();
        StringBuilder nb = new StringBuilder();
        int endx = nlen, endy = mlen;
        while (endx != 0 || endy != 0){
            int state = path.get(endx, endy);
            switch (state){
                case 0:
                    mb.insert(0, m.charAt(endy-1));
                    nb.insert(0, n.charAt(endx-1));
                    endx--; endy--;
                    break;
                case 1:
                    mb.insert(0, m.charAt(endy-1));
                    nb.insert(0, '_');
                    endy--;
                    break;
                case 2:
                    mb.insert(0, '_');
                    nb.insert(0, n.charAt(endx-1));
                    endx--;
                    break;
            }
        }

        return new Alignment(mb.toString(), nb.toString(), score.get(nlen, mlen));
    }

    static char gap = 'a' - 'A';
    public static boolean match(char a, char b){
        return a == b || a - b == gap || b - a == gap;
    }

    public static void main(String[] args){
        List<String> ls = Reader.read("HW1FILE1.txt");
        Alignment alignment = search(ls.get(0), ls.get(1));
        System.out.println(alignment);
    }

}

class BandMatrix{
    int[][] mat;
    int upperband;
    int lowerband;
    int m;
    int n;

    /**
     *
     * @param m the height of the matrix
     * @param n the width of the matrix, and n >= m
     * @param k the gap width, k >= n - m
     */
    BandMatrix(int m, int n, int k){
        int tmp = (k - n + m)>>1;
        upperband = n - m + tmp;
        lowerband = tmp;
        mat = new int[2*tmp + n - m + 1][m];
        this.m = m;
        this.n = n;
    }

    int get(int x, int y){
        if (x - y > upperband || x - y < - lowerband){
//            return Integer.MAX_VALUE;
            return 1000000;
        }
        return mat[x - y + lowerband][y];
    }

    int set(int x, int y, int value){
        if (x - y > upperband || x - y < - lowerband){
            return -1;
        }
        mat[x - y + lowerband][y] = value;
        return value;
    }

    void print(){
        for (int i=0; i< m; i++){
            for (int j=0; j<n; j++){
                int tmp = get(j, i);
                System.out.print((tmp == 1000000? -1:tmp) + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------");
    }
}

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wesley on 2017/4/4.
 */
public class MyHashTable {
    private long Z = 5;
    private BigInteger n;
    private BigInteger P;
    private List<String>[] table;

    public MyHashTable(int n, int z){
        this.n = new BigInteger(n+"");
        table = new List[n];
        Z = z;
        initP();
    }

    private long h1(String s){
        int len = s.length();
        long pl = 0;
        for (int i = len-1; i>=0; i--){
            char c = s.charAt(i);
            long ic = 0;
            switch (c){
                case 'A':
                case 'a':
                    ic = 1;
                    break;
                case 'G':
                case 'g':
                    ic = 2;
                    break;
                case 'T':
                case 't':
                    ic = 3;
                    break;
                case 'C':
                case 'c':
                    ic = 4;
                    break;
                default:
                    break;
            }
            pl = pl*Z + ic;
        }
        return pl;
    }

    private final BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
    private BigInteger initP(){
        P = new BigInteger(56, new Random());
        if (P.mod(TWO).equals(BigInteger.ZERO)){
            P = P.add(BigInteger.ONE);
        }
        while (!P.isProbablePrime(1024)){
            P = P.add(TWO);
        }
        return P;
    }

    private int h2(long h1){
        byte[] h1b = new byte[8];
        for (int i=7; i>=0;i--){
            h1b[i] = (byte) (h1 & 0xff);
            h1 >>>=8;
        }
        BigInteger bi = new BigInteger(h1b);
        return Integer.parseInt(bi.mod(P).mod(n).toString());
    }

    public boolean put(String s){
        int index = h2(h1(s));
        boolean result = false;
        if (table[index] == null){
            table[index] = new ArrayList<>();
            result = true;
        }
        table[index].add(s);
        return result;
    }

    public int get(String s){
        int index = h2(h1(s));
        if (table[index] == null || table[index].size() == 0){
            return -1;
        }
        for (String i:table[index]){
            if (s.equals(i)){
                return index;
            }
        }
        return -1;
    }

    public static void main(String[] args){

    }
}

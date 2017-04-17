import java.math.BigInteger;
import java.util.Random;

/**
 * Created by wesley on 2017/4/12.
 */
public class BloomFilter {

    private static int BYTE_SIZE = 8;
    private static double Ln_2 = 0.69314718055995;

    byte[] filter;
    int n, s, k;
    BigInteger P[];

    public BloomFilter(int n, int s){
        filter = new byte[(n-1)/BYTE_SIZE+1];
        this.n = n;
        this.s = s;
        k = (int) ((n / s)*Ln_2);
        P = new BigInteger[k];
        for (int i =0; i<k; i++){
            P[i] = generateP();
        }
    }

    private BloomFilter(int n, int s, int k, byte[] filter, BigInteger[] P){
        this.n = n;
        this.s = s;
        this.k = k;
        this.filter = filter;
        this.P = P;
    }

    private final static byte[] ONES = new byte[]{
      0x1, 0x2, 0x4, 0x8, 0x10, 0x20, 0x40, -0x80
    };
    public void insertion(String S){
        for (int i=0; i < k; i++){
            int index = h2(h1(S), P[i]);
            int outer_sit = (index - 1)/BYTE_SIZE;
            int inner_sit = index%BYTE_SIZE;
            filter[outer_sit] |= ONES[inner_sit];
        }
    }

    public boolean pass(String S){
        for (int i=0; i<k; i++){
            int index = h2(h1(S), P[i]);
            int outer_sit = (index - 1)/BYTE_SIZE;
            int inner_sit = index%BYTE_SIZE;
            if ((filter[outer_sit] & ONES[inner_sit]) == 0){
                return false;
            }
        }
        return true;
    }

    public BloomFilter OR(BloomFilter bf){
        if (n != bf.n){
            return null;
        }
        byte[] filter_result = new byte[filter.length];
        BloomFilter result = new BloomFilter(n, s, k, filter_result, P);
        for (int i=0; i< filter.length; i++){
            filter_result[i] = (byte) (filter[i] | bf.filter[i]);
        }
        return result;
    }

    private final static BigInteger TWO = new BigInteger("2");
    final static int PLen = 128;
    private BigInteger generateP(){
        while (true){
            BigInteger tmp = new BigInteger(PLen, new Random());
            if (tmp.mod(TWO).equals(BigInteger.ZERO)){
                tmp = tmp.add(BigInteger.ONE);
            }
            while (!tmp.isProbablePrime(1024)){
                tmp = tmp.add(TWO);
            }
            return tmp;
        }
    }

    private final static BigInteger Z = new BigInteger("5");
    private final static BigInteger[] TMP = {BigInteger.ZERO, BigInteger.ONE, TWO, new BigInteger("3")
            , new BigInteger("4")};
    private BigInteger h1(String S){
        int len = S.length();
        BigInteger ac = BigInteger.ZERO;
        for (int i=0; i< len; i++){
            int tmp;
            switch (S.charAt(i)){
                case 'A':
                    tmp = 0; break;
                case 'G':
                    tmp = 1; break;
                case 'T':
                    tmp = 2; break;
                case 'C':
                    tmp = 3; break;
                default:
                    tmp = 4;

            }
            ac = ac.multiply(Z).add(TMP[tmp]);
        }
        return ac;
    }

    private int h2(BigInteger h1, BigInteger P){
        return Integer.parseInt(h1.mod(P).mod(new BigInteger(""+n)).toString());
    }

}

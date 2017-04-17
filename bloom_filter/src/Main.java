import java.util.List;
import java.util.Random;

/**
 * Created by wesley on 2017/4/12.
 */
public class Main {

    static String[] generateString(int s, int mer_len){
        String[] ss = new String[s];
        Random r = new Random();
        for (int i=0; i < s; i++){
            char[] inst = new char[mer_len];
            for (int j=0; j < mer_len; j++){
                int t = r.nextInt(4);
                switch (t){
                    case 0: inst[j] = 'A';break;
                    case 1: inst[j] = 'G';break;
                    case 2: inst[j] = 'T';break;
                    default: inst[j] = 'C';break;
                }
            }
            ss[i] = new String(inst);
            if (i % 100000 == 0){
                System.out.println(i+" th string generated.");
            }
        }
        return ss;
    }

    public static void main(String[] args){
        int n= 30000000; // 300 million
        int s = 1000000;
        int mer_len = 100;
        BloomFilter bf = new BloomFilter(n, s);
        String[] ss = generateString(s, mer_len);
        int tmp =0;
        for (String mer: ss){
            bf.insertion(mer);
            if (tmp %100000 == 0){
                System.out.println(tmp+" th string generated.");
            }
            tmp++;
        }
        double false_positive_ratio = 0, false_negative_ratio = 0;
        for (int i=0; i< 1000; i++){
            if (!bf.pass(ss[i])){
                false_negative_ratio +=1;
            };
        }
        false_negative_ratio /= 1000;
        String[] new_ss = generateString(1000, mer_len);
        for (int i=0; i < 1000; i++){
            if (bf.pass(new_ss[i])){
                false_positive_ratio += 1;
            }
        }
        false_positive_ratio /= 1000;
        System.out.println("false negative ratio: "+ false_negative_ratio);
        System.out.println("false positive ratio: "+ false_positive_ratio);
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wesley on 2017/3/19.
 */
public class SuffixArray {

    public static List<String> suffix(Map<String, String> sequence){

        return null;
    }

    static int[] suffix(String seq){

        return null;
    }

    static int[] suffix_sort(int[] seq){
        if (seq.length == 0){
            return new int[0];
        } else if (seq.length == 1){
            int[] result = new int[1];
            result[0] = 0;
            return result;
        } else if (seq.length == 3){
            int[] result = new int[3];
            result[0] = 1;
            if (seq[0] < seq[2]){
                result[1] = 0;
                result[2] = 2;
            } else {
                result[1] = 2;
                result[2] = 0;
            }
            return result;
        }
        int len = seq.length;
        int[] R = new int[(len+1)/3 + len/3];
        int anchor_index = (len+1)/3;
        int k =0;
        // 3*k + 1
        for (int i=1; i< len; i+=3, k++){
            R[k] = i;
        }
        // 3*k + 2
        for (int i=2; i<len; i+=3, k++){
            R[k] = i;
        }
        List<Integer>[] radix_R = radix_sort(R, seq);
        int[] encoded_R = encode(radix_R, R, seq, R.length, anchor_index);
        // sort S'
        int[] suffix_R = suffix_sort(encoded_R);
        int[] sorted_S = new int[R.length];
        int[] mirror_sorted_S = new int[seq.length];
        for (int i=0,j=0; i<suffix_R.length; i++){
            int sr = suffix_R[i];
            if (sr < anchor_index){
                sorted_S[j] = sr*3+1;
                mirror_sorted_S[sr*3+1] = j;
                j++;
            } else if(sr == anchor_index){
                continue;
            } else {
                sorted_S[j] = (sr-anchor_index-1)*3+2;
                mirror_sorted_S[sorted_S[j]] = j;
                j++;
            }
        }

        // generate S'
        int[] sorted_S1 = new int[(len+2)/3];
        int max = -1;
        for (int i=0; i<seq.length; i+=3){
            if (seq[i] > max){
                max = seq[i];
            }
        }
        List<Integer>[] buckets = new List[max+1];
        for (int i=0; i<seq.length; i+=3){
            if (buckets[seq[i]] == null){
                buckets[seq[i]] = new ArrayList<>();
            }
            buckets[seq[i]].add(i);
        }

        int k1 = 0;
        for (int i=0; i<sorted_S.length; i++){
            if (sorted_S[i]%3 == 1){
                sorted_S1[k1] = sorted_S[i]-1;
                k1++;
            }
        }

        // merge S and S'
        int si=0, s1i=0, ri=0;
        int[] result = new int[seq.length];
        while (si<sorted_S.length && s1i < sorted_S1.length){
            boolean choose = false;
            int type = sorted_S[si]%3;
            if (type == 1) {
                if (seq[sorted_S[si]] == seq[sorted_S1[s1i]]){
                    choose = mirror_sorted_S[sorted_S[si]] < mirror_sorted_S[sorted_S1[s1i]];
                } else {
                    choose = seq[sorted_S[si]] < seq[sorted_S1[s1i]];
                }
            } else {
                if (seq[sorted_S[si]] == seq[sorted_S1[s1i]]){
                    if (sorted_S[si]+1 >= seq.length) {
                        choose = true;
                    } else if (sorted_S1[s1i]+1 >= seq.length){
                        choose = false;
                    } else if (seq[sorted_S[si]+1] == seq[sorted_S1[s1i]+1]){
                        choose = mirror_sorted_S[sorted_S[si]] < mirror_sorted_S[sorted_S1[s1i]];
                    } else {
                        choose = seq[sorted_S[si]+1] < seq[sorted_S1[s1i]+1];
                    }
                } else {
                    choose = seq[sorted_S[si]] < seq[sorted_S1[s1i]];
                }
            }
            if (choose){
                result[ri++] = sorted_S[si++];
            } else {
                result[ri++] = sorted_S1[s1i++];
            }
        }

        if (si < sorted_S.length){
            while (ri < result.length){
                result[ri++] = sorted_S[si++];
            }
        } else if (s1i < sorted_S1.length){
            while (ri < result.length){
                result[ri++] = sorted_S1[s1i++];
            }
        }

        return result;
    }

    static List<Integer>[] radix_sort(int[] table, int[] tseq){
        int max = -1;
        int[] seq = new int[tseq.length+3];
        for (int i=0;i<tseq.length; i++){
            if (tseq[i]> max){
                max = tseq[i];
            }
            seq[i] = tseq[i];
        }
        seq[tseq.length] = 0;
        seq[tseq.length+1] = 0;
        max ++;
        List<Integer>[] li = new List[max];
        li[0] = new ArrayList<>();
        for (int i=0; i< table.length; i++){
            li[0].add(i);
        }
        for (int i=2; i>=0; i--){
            List<Integer>[] li_new = new List[max];
            for (List<Integer> l: li){
                if (l== null || l.size() == 0){
                    continue;
                }
                for (int j :l){
                    int r = seq[table[j]+i];
                    if (li_new[r] == null){
                        li_new[r] = new ArrayList<>();
                    }
                    li_new[r].add(j);
                }
            }
            li = li_new;
        }
        return li;
    }

    static int[] encode(List<Integer>[] sort, int[] table, int[] seq, int size, int anchor){
        int code = 0;
        int[] result = new int[size+1];
        for (List<Integer> li: sort){
            if (li == null|| li.size() == 0){
                continue;
            }
            anchor_set(result, li.get(0), ++code, anchor);
            for (int i=1; i<li.size(); i++){
                int lg = li.get(i);
                if (!isEqual(table[lg], table[li.get(i-1)], seq)){
                    code++;
                }
                anchor_set(result, lg, code, anchor);
            }
        }
        result[anchor] = 0; // insert $
        return result;
    }

    private static void anchor_set(int[] array, int index, int val, int anchor){
        if (index < anchor){
            array[index] = val;
        } else {
            array[index+1] = val;
        }
    }

    private static boolean isEqual(int i, int j, int[] seq){
        if (i+1 < seq.length && j+1 < seq.length){
            if (seq[i+1] == seq[j+1]){
                if (i+2 < seq.length && j+2 < seq.length){
                    return seq[i+2] == seq[j+2];
                }
                return (i+2 >= seq.length && j+2 >= seq.length);
            } else {
                return false;
            }
        } else {
            return i+1 >= seq.length && j+1 >= seq.length;
        }
    }

    public static void main(String[] args){
        int[] seq = {'m','i','s','s', 'i','s','s', 'i','p','p','i'};
        int[] suffix = suffix_sort(seq);
        print(suffix);
    }

    static void print(int[] array){
        System.out.print('[');
        for (int i: array){
            System.out.print(i);
            System.out.print(", ");
        }
        System.out.println(']');
    }
}

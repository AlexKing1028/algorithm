import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 2017/3/20.
 */
public class SuffixArrayNlogN {
    List<String> seqs;
    String seq;

    public SuffixArrayNlogN(String s){
        seq = s;
    }

    public int[] suffix(){
        seq = seq + '$';
        int len = seq.length();
        List<Integer>[] buckets1 = sort_first();
        int H = 1;
        int[] pre_buckets = new int[len];
        int[] pre_ranks = new int[len];
        int cur_bn = 0, cur_index=0;
        for (List<Integer> l: buckets1){
            if (l == null || l.size() == 0){
                continue;
            }
            for (int i : l){
                pre_buckets[cur_index++] = i;
                pre_ranks[i] = cur_bn;
            }
            cur_bn++;
        }
        List<Integer> pre_index = preindex(pre_ranks, pre_buckets);

        int[] post_buckets = new int[len];
        int[] post_ranks = new int[len];

        while (H < seq.length()){
            List<Integer> index_copy = new ArrayList<>();
            index_copy.addAll(pre_index);
            for (int i=0; i<len; i++){
                int post = pre_buckets[i] - H;
                if (post < 0){
                    post += len;
                }
                int bucket_num = pre_ranks[post];
                int post_index = index_copy.get(bucket_num);
                post_buckets[post_index] = post;
                index_copy.set(bucket_num, post_index+1);
            }
            int prei = 0, pre_anchor = pre_index.get(prei);
            List<Integer> post_index = new ArrayList<>();
            post_index.add(0);
            for (int i=0; i<len-1; i++){
                /*
                if (i == pre_anchor){
                    post_index.add(i);
                    prei++;
                    if (prei<pre_index.size()){
                        pre_anchor = pre_index.get(prei);
                    } else {
                        pre_anchor = -1;
                    }
                }
                */
                if (pre_ranks[post_buckets[i]] < pre_ranks[post_buckets[(i+1)%len]]){
                    post_index.add(i+1);
                    continue;
                }
                int post = (post_buckets[i] + H)%len;
                int post1 = (post_buckets[i+1] + H)%len;
                if (pre_ranks[post] < pre_ranks[post1]){
                    post_index.add(i+1);
                }
            }

            int anchor_index = 0;
            for (int i=0; i< len; i++){
                if (anchor_index < post_index.size()-1){
                    if (i >= post_index.get(anchor_index+1)){
                        anchor_index++;
                    }
                    post_ranks[post_buckets[i]] = anchor_index;
                } else {
                    post_ranks[post_buckets[i]] = anchor_index;
                }
            }
            H <<= 1;
            int[] tmp1 = pre_buckets, tmp2 = pre_ranks;
            pre_buckets = post_buckets;
            pre_ranks = post_ranks;
            pre_index = preindex(pre_ranks, pre_buckets);
            post_buckets = tmp1;
            post_ranks = tmp2;
        }
        return pre_buckets;
    }

    private List<Integer> preindex(int[] ranks, int[] buckets){
        List<Integer> pre_index = new ArrayList<>();
        int cur_bn = 0;
        pre_index.add(0);
        for (int i=0; i<seq.length(); i++){
            if (ranks[buckets[i]] != cur_bn){
                pre_index.add(i);
                cur_bn++;
            }
        }
        return pre_index;
    }

    private List<Integer>[] sort_first(){
        List<Integer>[] buckets = new List[27];
        for (int i=0; i<seq.length(); i++){
            int index = format(seq.charAt(i));
            if (buckets[index] == null){
                buckets[index] = new ArrayList<>();
            }
            buckets[index].add(i);
        }
        return buckets;
    }

    final static int interval = 'A' - 'a';
    private int format(char c){
        if (c == '$'){
            return 0;
        }
        if (c <= 'Z' && c >= 'A'){
            return c - 'A'+1;
        }
        return c + interval - 'A' + 1;
    }


    public static void main(String[] args){
        SuffixArrayNlogN sann = new SuffixArrayNlogN("mississippi");
        int[] result = sann.suffix();
        print(result);
    }

    static void print(int[] array){
        System.out.print("[ ");
        for (int i: array){
            System.out.print(i+", ");
        }
        System.out.println(']');
    }

}

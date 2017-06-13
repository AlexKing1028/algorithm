import java.util.*;

/**
 * Created by wesley on 2017/5/11.
 */
public class FileSelection {
    public List<String>[] grouping(List<String> total){
        int len = total.size();
        int split_len = len/3;
        List<String>[] results = new ArrayList[3];
        for (int i=0;i<3;i++){
            results[i] = new ArrayList<>();
        }
        boolean[] state = new boolean[len];
        int i = len;
        Random r = new Random();
        while (i != 0){
            int index = r.nextInt(len);
            if (state[index]){
                // 被选中
                continue;
            }
            i--;
            if (i >= 2*split_len){
                results[2].add(total.get(index));
            } else if (i >= split_len){
                results[1].add(total.get(index));
            } else {
                results[0].add(total.get(index));
            }
        }
        return results;
    }
}

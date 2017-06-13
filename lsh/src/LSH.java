import java.util.List;

/**
 * Created by wesley on 2017/5/21.
 */
public class LSH {
    int k;
    int l;
    LSHashFunction[] funcs;
    public LSH(int d, int k, int l, String chromosome){
        this.k = k;
        this.l = l;
        funcs = new LSHashFunction[l];
        for (int i=0; i< l; i++){
            funcs[i] = LSHashFamily.generate(k, d);
            funcs[i].setChromosome(chromosome);
        }
    }

    public boolean add(int offset){
        for (int i=0; i< l; i++){
            if (!funcs[i].add(offset)){
                return false;
            }
        }
        return true;
    }

    public int search(String s){
        List<Integer>[] set = new List[l];
        return 0;
    }

}

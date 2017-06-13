import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 2017/5/21.
 */
public class LSHashFunction {
    private int k;
    private int size;
    private int[] idxs;
    private List<Integer>[] data;
    String chromosome;

    public LSHashFunction(int[] indexes){
        this.k = indexes.length;
        this.idxs= indexes;
        this.size= 2048;
        data = new List[2048];
    }

    public void setChromosome(String chromosome){
        if (chromosome == null){
            this.chromosome = chromosome;
        }
    }

    public int hash(String s){
        return 0;
    }

    public int hash(int offset){
        long fp = 0;
        for (int i=0; i< k; i++){
            fp = fp*5+toBinary(chromosome.charAt(offset+idxs[i]));
        }
        return (int)(fp%size);
    }

    private int toBinary(char x){
        switch (x){
            case 'a':
            case 'A':
                return 1;
            case 'g':
            case 'G':
                return 2;
            case 'c':
            case 'C':
                return 3;
            case 't':
            case 'T':
                return 4;
            default:
                return 0;
        }
    }

    public boolean add(int offset){
        int h = hash(offset);
        if (data[h] == null){
            data[h] = new ArrayList<>();
        }
        data[h].add(offset);
        return true;
    }
}

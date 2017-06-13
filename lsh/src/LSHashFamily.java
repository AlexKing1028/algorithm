import java.util.Random;

/**
 * Created by wesley on 2017/5/21.
 */
public class LSHashFamily {
    private static Random random = new Random();

    public static LSHashFunction generate(int k, int d){
        int[] indexes = new int[k];
        for (int i=0; i< k; i++){
            indexes[i] = random.nextInt(d);
        }
        return new LSHashFunction(indexes);
    }

}

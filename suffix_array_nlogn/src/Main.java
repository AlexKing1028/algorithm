import java.util.List;

/**
 * Created by wesley on 2017/3/20.
 */
public class Main {
    public static void main(String[] args){

        List<String> seqs = Reader.read("HW1FILE1.txt");
        StringBuilder sb = new StringBuilder();
        sb.append(seqs.get(0));
        for (int i=1; i<seqs.size(); i++){
            sb.append('$');
            sb.append(seqs.get(i));
        }
        SuffixArrayNlogN sann = new SuffixArrayNlogN(sb.toString());
        long time = System.currentTimeMillis();
        SuffixArrayNlogN.print(sann.suffix());
        time -= System.currentTimeMillis();
        System.out.println(-time);

        /*
        String seq = Reader.readChrX("chrX.fna");
        SuffixArrayNlogN sann2 = new SuffixArrayNlogN(seq);
        SuffixArrayNlogN.print(sann2.suffix());
        */
    }
}

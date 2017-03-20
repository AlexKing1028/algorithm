import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 2017/3/20.
 */
public class Reader {
    public static List<String> read(String filename){
        List<String> ls = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            String line ;
            int line_num = 1;
            StringBuilder sb;
            while ((line = br.readLine()) != null){
                // System.out.println(line_num);
                // System.out.println(line);
                if (line.length() != 0 && line.charAt(0) == '>'){
                    sb = new StringBuilder();
                    while ((line = br.readLine()) != null && line.length() != 0){
                        sb.append(line);
                    }
                    ls.add(sb.toString());
                }
            };
        } catch (Exception e){
            e.printStackTrace();
        }
        return ls;
    }

    public static String readChrX(String filename){
        StringBuilder sb=new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

}
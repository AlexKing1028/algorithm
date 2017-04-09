import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 2017/4/4.
 */
public class Reader {
    public static List<String> read(String filename){
        List<String> ls = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            String line;
            StringBuffer sb = null;
            String key = null;
            while ((line = br.readLine())!= null){
                if (line.charAt(0) == '>'){
                    if (sb != null){

                    }
                    String[] tmp = line.split(" ");
                    if (tmp.length == 0){
                        key = tmp[0];
                    }
                    sb = new StringBuffer();
                } else{
                    if (sb == null){
                        sb = new StringBuffer();
                    }
                    sb.append(line);
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ls;

    }
}

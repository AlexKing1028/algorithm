/**
 * Created by wesley on 2017/3/4.
 */
public class Alignment {
    String a;
    String b;
    int score;

    public Alignment(String a, String b, int score) {
        this.a = a;
        this.b = b;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Alignment{" + '\n' +
                "a='" + a + "\',\n" +
                "b='" + b + "\',\n" +
                "score=" + score +
                '}';
    }
}

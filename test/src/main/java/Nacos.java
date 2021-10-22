import java.util.Random;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/27
 */
public class Nacos {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        Random random = new Random();
        int num = 0;
        for (int j = 0; j < 10000; j++) {
            int i = random.nextInt(10);
            num = i + num;
        }
        System.out.println(num);
    }
}

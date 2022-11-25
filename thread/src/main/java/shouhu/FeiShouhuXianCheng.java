package shouhu;

/**
 * 非守护线程
 * 如果主线程销毁，用户线程继续运行且互不影响。（用户线程：用户线程即我们手动创建的线程）
 *
 * @author: LiaoMingtao
 * @date: 2022/11/25
 */
@SuppressWarnings("all")
public class FeiShouhuXianCheng {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000L);
                        System.out.println("我是子线程(用户线程)");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.setName("thread-非守护线程");
        thread.start();
        //相当与主线程
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("main:i:" + i);
        }
        System.out.println("主线程执行完毕...");
    }
}

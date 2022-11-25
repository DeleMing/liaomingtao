package shouhu;

/**
 * 1.守护线程 例如gc
 *
 * 有一个特征，例如当主线程运行的时候，垃圾回收线程一起运行。
 * 当主线程销毁，会和主线程一起销毁。
 *
 * @author: LiaoMingtao
 * @date: 2022/11/25
 */
@SuppressWarnings("all")
public class ShouhuXiancheng {

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
        // 守护线程
        thread.setDaemon(true);
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

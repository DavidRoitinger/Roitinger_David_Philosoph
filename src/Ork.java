import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Ork implements Runnable{
    private String name;
    private int leftDaggerId;
    private int rightDaggerId;
    private Table table;
    private boolean rightDagger = false;
    private boolean leftDagger = false;
    private ReentrantLock rightLock;
    private ReentrantLock leftLock;

    public Ork(String name, int leftDaggerId, int rightDaggerId, Table table, ReentrantLock rightLock, ReentrantLock leftLock) {
        this.name = name;
        this.leftDaggerId = leftDaggerId;
        this.rightDaggerId = rightDaggerId;
        this.table = table;
        this.rightLock = rightLock;
        this.leftLock = leftLock;
    }

    @Override
    public void run() {
        while(true) {
            drinking();
            rightDagger = grabRightDagger();
            leftDagger = grabLeftDagger();
            if (leftDagger){
                if (rightDagger){
                    feasting();
                }else if (grabRightDagger()){
                    feasting();
                }

            }else if (rightDagger&&grabLeftDagger()){
                    feasting();
                }
            releaseLeftDagger();
            releaseRightDagger();
            }

        }
    private boolean grabLeftDagger() {
        try {
            leftLock.tryLock(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return leftLock.isHeldByCurrentThread();
    }

    private boolean grabRightDagger() {
        try {
            rightLock.tryLock(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return rightLock.isHeldByCurrentThread();
    }

    private void releaseRightDagger() {
        rightLock.unlock();
    }

    private void releaseLeftDagger() {
        leftLock.unlock();

    }

    private void feasting() {
        if (rightDagger&&leftDagger){
            System.out.println(name+": Has feasted");
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(1000)+100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void drinking() {
        System.out.println(name+": Has drunk");
        Random rand = new Random();
        try {
            Thread.sleep(rand.nextInt(1000)+100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

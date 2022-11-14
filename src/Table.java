import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    public ArrayList<Dagger> daggers = new ArrayList<>();

    public Table() {
        for (int i = 0; i < 5; i++) {
            this.daggers.add(new Dagger());
        }
        Thread ork1 = new Thread(new Ork("Ork1",4,0, this, daggers.get(4).rLock, daggers.get(0).rLock));
        Thread ork2 = new Thread(new Ork("Ork2",0,1, this, daggers.get(0).rLock, daggers.get(1).rLock));
        Thread ork3 = new Thread(new Ork("Ork3",1,2, this, daggers.get(1).rLock, daggers.get(2).rLock));
        Thread ork4 = new Thread(new Ork("Ork4",2,3, this, daggers.get(2).rLock, daggers.get(3).rLock));
        Thread ork5 = new Thread(new Ork("Ork5",3,4, this, daggers.get(3).rLock, daggers.get(4).rLock));
        ork1.start();
        ork2.start();
        ork3.start();
        ork4.start();
        ork5.start();
    }
}

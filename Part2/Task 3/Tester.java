public class Tester { 
  public static void main(String[] args)
                 throws java.lang.InterruptedException {
    doTask1();
    doTask2();

  } 

  public static void doTask1()
                 throws java.lang.InterruptedException {
    CounterClass c=new CounterClass();
    Thread t1 = new Thread(c);
    Thread t2 = new Thread(c);
    t1.start();
    t1.join();
    t2.start();
    t2.join();
    System.out.println("Result of doTask1: " + c.getResult());
  } 

  public static void doTask2()
                 throws java.lang.InterruptedException {
    CounterClass c=new CounterClass();
    Thread t1 = new Thread(c);
    Thread t2 = new Thread(c);
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println("Result of doTask2: " + c.getResult());
  } 
}

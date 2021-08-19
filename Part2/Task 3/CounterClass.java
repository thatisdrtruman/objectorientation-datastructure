public class CounterClass implements Runnable{
  private int value = 0;
  public synchronized int getNext(){
    return value++;
  }
  public int getResult(){
    return value;
  }
  public void run() {
    for (int i=0;i<5000;i++){
      this.getNext();
    }
  }
}
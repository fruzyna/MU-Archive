/**
 * Created by mail929 on 9/23/16.
 */
public class Driver {

    public static void main(String args[])
    {
        CircLinkedUnbndQueue<String> q = new CircLinkedUnbndQueue<>();
        q.enqueue("One");
        q.enqueue("Two");
        q.enqueue("Three");
        System.out.println("isEmpty: " + q.isEmpty());
        System.out.println("deQueue: " + q.dequeue());
        System.out.println("deQueue: " + q.dequeue());
        System.out.println("deQueue: " + q.dequeue());
        System.out.println("isEmpty: " + q.isEmpty());
        System.out.println("deQueue: " + q.dequeue());
    }
}

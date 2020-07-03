public class CircLinkedUnbndQueue<T> implements UnboundedQueueInterface<T> {

    protected LLNode<T> rear;


    public CircLinkedUnbndQueue() {
    }

    public T dequeue() throws QueueUnderflowException {
    	if(rear != null)
    	{
            if(!isEmpty() && rear.getLink() != rear)
            {
            	T old = rear.getInfo();
                LLNode current = rear;
                while(current.getLink() != rear)
                {
                    current = current.getLink();
                }
                current.setLink(rear.getLink());
                rear = current;
                return old;
            }
            else if(rear.getLink() == rear)
            {
            	rear = null;
            }
    	}
        return null;
    }

    public void enqueue(T element) {
    	System.out.println("Adding " + element.toString());
        if(!isEmpty())
        {
        	LLNode<T> next = new LLNode<T>(element);
        	next.setLink(rear.getLink());
        	rear.setLink(next);
        	rear = next;
        }
        else
        {
            rear = new LLNode<T>(element);
            rear.setLink(rear);
        }
        System.out.println("Rear is now " + rear.getInfo() + " with a link to " + rear.getLink().getInfo());
    }

    public boolean isEmpty() {
        return rear == null;
    }
}
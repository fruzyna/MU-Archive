
public interface UnboundedQueueInterface<T> extends QueueInterface<T>

{
  void enqueue(T element);
  // Adds element to the rear of this queue.
}
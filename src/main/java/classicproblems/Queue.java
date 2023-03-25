package classicproblems;

public interface Queue<E> {
     void put(E e) throws InterruptedException;
     E get() throws InterruptedException;
     boolean isEmpty();
}

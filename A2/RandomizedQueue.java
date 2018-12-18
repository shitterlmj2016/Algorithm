import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int n;               // number of elements on queue

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }// is the randomized queue empty?

    public int size() {
        return n;
    }// return the number of items on the randomized queue

    public void enqueue(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();
        RandomizedQueue.Node<Item> oldlast = last;
        last = new RandomizedQueue.Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        n++;
    }// add the item

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        int step = getN();
        if (step == 0) {
            return olddequeue();
        }

        Node curr = first;
        for (int i = 0; i < step - 1; i++) {
            curr = curr.next;
        }

        Item item = (Item) curr.next.item;
        curr.next = curr.next.next;
        n--;
        if (curr.next == null)
            last = curr;//这句话忘记写了
        return item;

    }// remove and return a random item


    private Item olddequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        int step = getN();
        if (step == 0) {
            return first.item;
        }

        Node curr = first;
        for (int i = 0; i < step - 1; i++) {
            curr = curr.next;
        }

        return (Item)curr.next.item;
    }// return a random item (but do not remove it)

    public Iterator<Item> iterator() {
        return new RandomizedQueueListIterator<Item>(first);
    }// return an independent iterator over items in random order

    public static void main(String[] args) {
        RandomizedQueue rq = new RandomizedQueue();
        rq.enqueue(1);
        rq.enqueue(0);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
//        for(int i=0;i<100;i++)
//        {
//           rq.enqueue(i);
//        }
        for (int i = 0; i < 100; i++) {
            System.out.println(rq.sample());
        }
    }// unit testing (optional)

    // an iterator, doesn't implement remove() since it's optional
    private class RandomizedQueueListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        private int size;
        private Node[] array;

        public RandomizedQueueListIterator(Node<Item> first) {
            current = first;
            Node<Item> temp = current;
            size = n;
            array = new Node[size];

            for (int i = 0; i < size; i++) {
                array[i] = temp;
                temp = temp.next;
            }
            StdRandom.shuffle(array);
        }


        public boolean hasNext() {
            return size != 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = (Item) array[size - 1].item;
            size--;
            return item;
        }
    }

    private int getN() {
        return StdRandom.uniform(n);

    }


}

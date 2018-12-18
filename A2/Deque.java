import java.util.Iterator;
import java.util.NoSuchElementException;
import  edu.princeton.cs.algs4.StdIn;
import  edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;//number of elements

    public Deque() {

        first = null;
        last = null;
        n = 0;
    }                           // construct an empty deque

    public boolean isEmpty() {
        return first==null;
    }// is the deque empty?

    public int size() {
        return n;
    }                        // return the number of items on the deque

    public void addFirst(Item item)          // add the item to the front
    {
        if (item==null) throw new java.lang.IllegalArgumentException();
        Node <Item> oldfirst = first;
        first = new Node<Item>();
        first.item=item;
        first.prev=null;
        first.next=oldfirst;
        if(isEmpty()) last = first;
        else oldfirst.prev=first;
        n++;
    }

    public void addLast(Item item)           // add the item to the end
    {   if (item==null) throw new java.lang.IllegalArgumentException();
        Node <Item> oldlast = last;
        last = new Node<Item>();
        last.item=item;
        last.next=null;
        last.prev=oldlast;
        if(isEmpty()) first = last;
        else oldlast.next=last;
        n++;

    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        if(size()>1) {
            first.next.prev = null;
        }
        first = first.next;
        n--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        Item item = last.item;
        if(size()>1) {
            last.prev.next = null;
        }
        last = last.prev;
        n--;
        if (n==0) first = null;   // to avoid loitering//Conflict with first = null
        return item;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new ListIterator<Item>(first);
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<String> deque = new Deque<String>();
        deque.addLast("A");
//        deque.addFirst("B");
//       deque.addLast("C");

                while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                deque.addLast(item);
            else if (!deque.isEmpty())
                StdOut.print(deque.removeLast() + " ");
        }
        StdOut.println("(" + deque.size() + " left on deque)");
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }



    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }
}

package hashsetwithchaining;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class HashSetWithChaining<E> implements Set<E> {
    public static final float loadFactor = 0.75f;
    public Node[] allNodes;
    public int numElements;
    public int capacity;
    
    public HashSetWithChaining() {
        capacity = 10;
        allNodes = new Node[capacity];
        numElements = 0;
    }
    
    public HashSetWithChaining(int capacity) {
        this.capacity = capacity;
        allNodes = new Node[capacity];
        numElements = 0;
    }

    @Override
    public int size() {
        return capacity;
    }

    @Override
    public boolean isEmpty() {
        return (numElements == 0);
    }
    
    public int hashCode(E element) {
        return (element.hashCode() % capacity);
    }
    
    public void expandCapacity() {
        Node[] newNodeArray;
        newNodeArray = new Node[capacity * 2];
        
        for (int x = 0; x < capacity; x++) {
            if (allNodes[x] != null) {
                newNodeArray[x] = allNodes[x];
            }
        }
        
        allNodes = newNodeArray;
    }

    @Override
    public boolean contains(Object o) {
        boolean status = false;
        
        Node newNode = allNodes[hashCode((E) o)];
        while (newNode != null) {
            if (newNode.element.equals(o)) {
                status = true;
            }
            else {
                status = false;
            }
            
            newNode = newNode.next;
        }
        
        return status;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator newIterator = new Iterator() {
            public int index = 0;
            public Node current = null;
            
            @Override
            public boolean hasNext() {
                boolean state = false;
                
                for (int i = index; i < numElements; i++) {
                    if (allNodes[i] != null) {
                        state = true;
                    }
                }
                
                return state;
            }

            @Override
            public Object next() {
                
            }
        };
        
        return newIterator;
    }

    @Override
    public Object[] toArray() {
        return allNodes;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method allows adding elements to the hash set. It checks
     * to see if it can be added in the appropriate index, if not it
     * uses chaining for resolution.
     * 
     * @param e
     * @return 
     */
    @Override
    public boolean add(E e) {
        int hashCode = hashCode(e);
        boolean added;
        
        // If the load factor exceeds 0.75...
        if (numElements / capacity > loadFactor) {
            System.out.println("LOAD FACTOR EXCEEDED, EXPANDING CAPACITY");
            expandCapacity();
        }
        
        if (e != null) {
            // If the node array contains this element...
            if (contains(e)) {
                Node currentNode = allNodes[hashCode];
            
                if (allNodes[hashCode].next != null) {
                    while (allNodes[hashCode].next != null) {
                        currentNode = currentNode.next;
                    }
                }
            
                currentNode.next = new Node(e);
                added = true;
            }
            // If the element to be added is not in the array...
            else {
                Node newNode = new Node(e);
                newNode.next = allNodes[hashCode];
                allNodes[hashCode] = newNode;
                numElements++;
                added = true;
            }
        }
        else {
            added = false;
        }
        
        return added;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        
    }
    
    @Override
    public void clear() {
        for (int clear = 0; clear < numElements; clear++) {
            allNodes[clear] = null;
        }
        
        numElements = 0;
    }
    
    @Override
    public String toString() {
        String output = "";
        
        for (int x = 0; x < capacity; x++) {
            output += "Row " + x + ": ";
            
            if (allNodes[x] != null) {
                Node current = allNodes[x];
                int count = 0;
                while (current != null) {
                    if (count != 0) {
                        output += " --> ";
                    }
                    
                    output += current.element;
                    current = current.next;
                    count++;
                }
            }
            
            output += "\n";
        }
        
        return output;
    }
    
    /**
     * 
     * @param <E> 
     */
    public class Node<E> {
        public E element;
        public Node next;
        
        public Node() {
            element = null;
            next = null;
        }
    
        public Node(E element) {
            this.element = element;
            next = null;
        }
    }
    
    public static void main(String[] args) {
        Random random = new Random();
        int randomSize = random.nextInt(10) + 4;
        
        HashSetWithChaining hashSet;
        hashSet = new HashSetWithChaining(randomSize);
        
        System.out.println("Creating Set, Initial Capacity = " + randomSize + "... " +
                "Adding Seth, Bob, Adam, Ian");
        
        hashSet.add("Seth");
        hashSet.add("Bob");
        hashSet.add("Adam");
        hashSet.add("Ian");
        
        System.out.println(hashSet.toString());
        
        System.out.println("Size is " + hashSet.numElements);
        System.out.println("Adding Jill, Amy, Nat, Seth, Bob, Simon");
        
        hashSet.add("Jill");
        hashSet.add("Amy");
        hashSet.add("Nat");
        hashSet.add("Simon");

        System.out.println(hashSet.toString());
        
        System.out.println("Contains Seth? " + hashSet.contains("Seth") + ", Contains Nat? " +
                hashSet.contains("Nat") + ", Contains Gary? " + hashSet.contains("Gary"));
    }
}
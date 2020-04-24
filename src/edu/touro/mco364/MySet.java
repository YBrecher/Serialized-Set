package edu.touro.mco364;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MySet <T> implements Set<T>, Serializable {

    private Object[] backingStore = new Object[10];
    private int insertionPoint = 0;

    private MySet(Object[] transferredBackingStore) {
        backingStore = transferredBackingStore;
        this.backingStore =  new Object[]{};

    }

    MySet() {

    }

    void myWriteObject(ObjectOutputStream out) throws IOException
    {
         Object[] backingStoreCopy = new Object[size()];
         System.arraycopy(backingStore,0,backingStoreCopy,0,size());
         //we serialize this copy instead of the original to only get the elements and not nulls. (size vs length)
         out.writeObject(backingStoreCopy);

    }

     MySet myReadObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {

       backingStore = (Object[]) in.readObject();

       MySet newSet = new MySet(backingStore);

        return newSet;
    }

    @Override
    public int size() {
        return insertionPoint;
    }

    @Override
    public boolean isEmpty() {
        return insertionPoint <= 0;
    }

    @Override
    public boolean contains(Object o)  {
        for (Object t : backingStore) {
            if ( t != null && t.equals(o)) {
                return true;
            }
        }
        return false;
    }

    Object get(int index){
        return backingStore[index];
    }

    @Override
    public boolean add(T t) {

        if (insertionPoint >= backingStore.length){ //if list is full we call ensure capacity to make it bigger
            ensureCapacity();
        }
        if (!contains(t)){ //if set already has element we don't add it again
            backingStore[insertionPoint] = t;
            insertionPoint++;
            return true;
        }
        return false;
    }

    private void ensureCapacity(){
        if (insertionPoint >= backingStore.length){
            Object[] temp = new Object[backingStore.length * 2 + 1];
            System.arraycopy(backingStore,0,temp,0,backingStore.length);
            backingStore = temp;
        }

    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size(); i ++) {
            if ( backingStore[i].equals(o)) {
                if (i == size() -1){
                    backingStore[i] = null;
                } else{
                    System.arraycopy(backingStore, i + 1, backingStore, i, size() - i);
                }
                insertionPoint--;
                return true;
            }
        }
        return  false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object i : c){
            if (!contains(i)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T j : c){
            add(j);
        }
        return true;
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object i : c) {
            remove(i);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) { //for retainAll we can just create a new backingStore with the correct elements
        Object[] temp = new Object[backingStore.length];
        int tempInsertionPoint = 0;
        for(Object i : c){
            if(contains(i)){
                temp[tempInsertionPoint] = i;
                tempInsertionPoint++;
            }
        }
        backingStore = temp;
        return true;
    }

    @Override
    public void clear() {
        insertionPoint = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new MySetIterator();
    }

    public class MySetIterator implements Iterator<T> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < insertionPoint;
        }

        @Override
        public T next() {
            return (T) backingStore[index++];
        }
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size()];
        System.arraycopy(backingStore,0,newArray,0,size());
        return newArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        System.arraycopy(backingStore,0, a,0,size());
        return a;
    }
}

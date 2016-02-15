package Maze;

import java.util.*;

/**
 * An array implementation of binary heap.
 */
public class BinaryHeap {

    private Cell[] array;
    private int size;

    public BinaryHeap(){
        array = new Cell[20];
        size = 0;
    }

    public void add(Cell cell){
        if(size >= array.length - 1){
            array = resize();
        }
        size++;
        array[size] = cell;
        reorderUp();
    }

    public Cell remove(){
        if(isEmpty()){
            return null;
        }
        Cell toReturn = array[1];
        array[1] = array[size];
        array[size] = null;
        size--;
        reorderDown();
        return toReturn;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private void reorderUp(){
        int curr = size;
        while(curr > 1 && parentFValue(curr) > array[curr].getFValue()){
            int parentIndex = (int)Math.ceil(curr/2);
            swap(curr, parentIndex);
            curr = parentIndex;
        }
    }

    private void reorderDown(){
        int curr = 1;
        while(findSmallChild(curr) > 0){
            int smallChild = findSmallChild(curr);
            swap(curr, smallChild);
            curr = smallChild;
        }
    }

    private int findSmallChild(int i){
        if(i * 2 <= size - 1 && array[i * 2].getFValue() > array[i * 2 + 1].getFValue()){
            return i * 2 + 1;
        } else if (i * 2 <= size) {
            return i * 2;
        }
        return 0;
    }

    private int parentFValue(int i){
        return array[(int)Math.ceil(i/2)].getFValue();
    }

    private void swap(int i, int j){
        Cell temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private Cell[] resize(){
        return Arrays.copyOf(array, array.length * 2);
    }


}

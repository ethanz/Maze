package Maze;

import java.util.*;

/**
 * An array implementation of binary heap.
 */
public class BinaryHeap {

    private Cell[] array;
    private int size;
    private boolean preferLarge;

    public BinaryHeap(boolean preferLarge){
        array = new Cell[20];
        size = 0;
        this.preferLarge = preferLarge;
    }

    public Cell peek(){
        if(!isEmpty()){
            return array[1];
        }
        return null;
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
        reorderDown(1);
        return toReturn;
    }

    public void remove(int index){
        array[index] = array[size];
        array[size] = null;
        size--;
        reorderDown(index);
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int contain(Cell cell){
        if(peek() != null){
            for(int i = 1; i < size + 1; i++){
                if(array[i].equals(cell)){
                    return i;
                }
            }
        }
        return 0;
    }

    private void reorderUp(){
        int curr = size;
        while(curr > 1 && compareCell((int)Math.ceil(curr/2), curr) > 0){
            int parentIndex = (int)Math.ceil(curr/2);
            swap(curr, parentIndex);
            curr = parentIndex;
        }
    }

    private void reorderDown(int i){
        int curr = i;
        while(findSmallChild(curr) > 0){
            int smallChild = findSmallChild(curr);
            swap(curr, smallChild);
            curr = smallChild;
        }
    }

    //return 1 when the f value of i is larger than j, -1 otherwise, break tie depend on preferLarge variable
    private int compareCell(int i, int j){
        if(i > size || j > size || i < 1 || j < 1){
            return 0;
        }
        if(array[i].getFValue() == array[j].getFValue()){
            if(array[i].getGValue() > array[j].getGValue() && preferLarge){
                return -1;
            }
            //if(array[i].getGValue() == array[j].getGValue()){
                //Random rand = new Random();
                //int toReturn = rand.nextInt(3);
                //while(toReturn -1 == 0){
                    //toReturn = rand.nextInt(3);
                //}
                //return toReturn - 1;
            //}
            return 1;
        } else if(array[i].getFValue() > array[j].getFValue()){
            return 1;
        }
        return -1;
    }

    private int findSmallChild(int i){
        int smallerChild = 0;
        if(i * 2 <= size - 1 && compareCell(i * 2, i * 2 + 1) > 0){
            smallerChild =  i * 2 + 1;
        } else if (i * 2 <= size) {
            smallerChild =  i * 2;
        }
        if(compareCell(i, smallerChild) < 0) {
            return 0;
        }
        return smallerChild;
    }

    //private int parentFValue(int i){
        //return array[(int)Math.ceil(i/2)].getFValue();
    //}

    private void swap(int i, int j){
        Cell temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private Cell[] resize(){
        return Arrays.copyOf(array, array.length * 2);
    }
}

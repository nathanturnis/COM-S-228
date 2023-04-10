package edu.iastate.cs228.hw3;

import javax.annotation.processing.Completion;
import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail;
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  @Override
  public int size()
  {
    return size;
  }

    /**
     * Helper method to link 2 nodes.
     * @param current
     * @param newNode
     */
  private void link(Node current, Node newNode) {
      newNode.previous = current;
      newNode.next = current.next;
      current.next.previous = newNode;
      current.next = newNode;
  }

    /**
     * Checks to see if item of type E is in the list.
     * @param item
     * @return true if item is in list, false if not
     */
  public boolean contains(E item) {
      if(size < 1)
          return false;
      Node node = head.next;
      while(node != tail) {
          for(int i = 0;i < node.count;i++) {
              if(node.data[i].equals(item))
                  return true;
          }
          node = node.next;
      }
      return false;
  }

    /**
     * Add an item type E at the end of the list.
     * @param item element whose presence in this collection is to be ensured
     * @return
     */
  @Override
  public boolean add(E item)
  {
      if(item == null){
          throw new NullPointerException("Cannot add a null item!");
      }

      if(size == 0) {
          Node newNode = new Node();
          newNode.addItem(item);
          newNode.next = tail;
          newNode.previous = head;
          head.next = newNode;
          tail.previous = newNode;
      } else {

          if(tail.previous.count < nodeSize) {
                tail.previous.addItem(item);
          } else {
              Node newNode = new Node();
              newNode.addItem(item);
              link(tail.previous, newNode);
          }

      }
     size++;
    return true;
  }

    /**
     * Add an item of type E at logical index.
     * @param pos index at which the specified element is to be inserted
     * @param item element to be inserted
     */
  @Override
  public void add(int pos, E item)
  {

      if(pos > size || pos < 0) {
          throw new IndexOutOfBoundsException();
      }

      if(size == 0) {
          add(item);
      }

      NodeInfo nodeInfo = find(pos);

      if(nodeInfo.offset == 0 && nodeInfo.node.previous != head) {

          if(nodeInfo.node.previous.count < nodeSize) {
              nodeInfo.node.previous.addItem(item);
              size++;
              return;
          } else if(nodeInfo.node == tail && nodeInfo.node.previous.count == nodeSize) {
              Node newNode = new Node();
              newNode.addItem(item);
              tail.previous = newNode;
              newNode.previous = nodeInfo.node;
              newNode.next = tail;
              nodeInfo.node.next = newNode;
              size++;
              return;
          }

      }

      if(nodeInfo.node.count < nodeSize) {
          nodeInfo.node.addItem(nodeInfo.offset, item);
          size++;
      } else {
          Node newNode = new Node();
          int mid = nodeSize / 2;
          int nodeInfoSize = nodeInfo.node.count;
          for(int i = nodeInfo.node.count - mid; i < nodeInfoSize; i++) {
              newNode.addItem(nodeInfo.node.data[nodeInfoSize - mid]);
              nodeInfo.node.removeItem( nodeInfoSize - mid);
          }

          Node temp = nodeInfo.node.next;
          nodeInfo.node.next.previous = newNode;
          newNode.next = temp;
          newNode.previous = nodeInfo.node;
          nodeInfo.node.next = newNode;

          if(nodeInfo.offset <= mid) {
              nodeInfo.node.addItem(nodeInfo.offset, item);
          } else if(nodeInfo.offset > mid) {
              newNode.addItem((nodeInfo.offset - mid), item);
          }
          size++;
      }

  }

    /**
     * Remove an item at logical index.
     * @param pos the index of the element to be removed
     * @return E data of item removed
     */
  @Override
  public E remove(int pos)
  {
      if(pos > size || pos < 0) {
          throw new IndexOutOfBoundsException();
      }

      NodeInfo nodeInfo = find(pos);
      E returnData = nodeInfo.node.data[nodeInfo.offset];

      if(nodeInfo.node.next == tail && nodeInfo.node.count == 1) {
         // nodeInfo.node.removeItem(nodeInfo.offset);
          Node prevNode = nodeInfo.node.previous;
          prevNode.next = nodeInfo.node.next;
          nodeInfo.node.next.previous = prevNode;
          size--;
      } else if(nodeInfo.node.next == tail || nodeInfo.node.count > (nodeSize / 2)) {
          nodeInfo.node.removeItem(nodeInfo.offset);
          size--;
      } else {
          if(nodeInfo.node.next.count > (nodeSize / 2)) {
              nodeInfo.node.removeItem(nodeInfo.offset);
              E firstElement = nodeInfo.node.next.data[0];
              nodeInfo.node.next.removeItem(0);
              nodeInfo.node.addItem(firstElement);
              size--;
          } else {
              nodeInfo.node.removeItem(nodeInfo.offset);
              int nextNodeCount = nodeInfo.node.next.count;

              for(int i = 0; i < nextNodeCount; i++) {
                  nodeInfo.node.addItem(nodeInfo.node.next.data[i]);
              }

              Node successor = nodeInfo.node.next;
              nodeInfo.node.next = successor.next;
              successor.next.previous = nodeInfo.node;
              successor = null;
              size--;
          }
      }

    return returnData;
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
      E[] dataArray = (E[]) new Comparable[size];

      Node node = head.next;
      int j = 0;
      while(node != tail) {
          for(int i = 0; i < node.count; i++) {
              dataArray[j] = node.data[i];
              j++;
          }
          node = node.next;
      }

      head.next = tail;
      tail.previous = head;
      size = 0;
      insertionSort(dataArray, new ElementComparator());
      for(int i = 0; i < dataArray.length; i++) {
          StoutList.this.add(dataArray[i]);
      }
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
      E[] dataArray = (E[]) new Comparable[size];

      Node node = head.next;
      int j = 0;
      while(node != tail) {
          for(int i = 0; i < node.count; i++) {
              dataArray[j] = node.data[i];
              j++;
          }
          node = node.next;
      }

      head.next = tail;
      tail.previous = head;
      size = 0;
      bubbleSort(dataArray);
      for(int i = 0; i < dataArray.length; i++) {
          StoutList.this.add(dataArray[i]);
      }

  }
  
  @Override
  public Iterator<E> iterator()
  {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
 //     System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
 //    System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
 
  private class StoutListIterator implements ListIterator<E>
  {
	// constants you possibly use ...   
	  
	// instance variables ...

      //BEHIND when last operation was next(), AHEAD if previous(), and NONE otherwise
      private static final int BEHIND = -1;
      private static final int AHEAD = 1;
      private static final int NONE = 0;

      //Indicates the cursor we are currently in
      private Node nodeCursor;
      //Indicates the element to the right of cursor
      private int offsetCursor;
      //The index of the item in the array
      private int logicalIndex;
      //BEHIND if -1, AHEAD if -1, NONE if 0. Mainly used by remove and set methods
      private int direction;
	  
    /**
     * Default constructor 
     */
    public StoutListIterator()
    {
    	this(0);
    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
        if(pos > size || pos < 0) {
            throw new IndexOutOfBoundsException();
        }

        NodeInfo temp = find(pos);
        nodeCursor = temp.node;
        offsetCursor = temp.offset;
        logicalIndex = pos;
        direction = NONE;
    }

      /**
       * Return true if there is another element in the list up next.
       * @return
       */
    @Override
    public boolean hasNext()
    {
        return logicalIndex < size;
    }

      /**
       * Go to the next element.
       * @return
       */
    @Override
    public E next()
    {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }

        E returnData = nodeCursor.data[offsetCursor];

        //if we are the end of the node, go to the next node
        if(offsetCursor >= nodeCursor.count - 1) {
            nodeCursor = nodeCursor.next;
            if(nodeCursor == tail){
                offsetCursor = 0;
                logicalIndex++;
                direction = BEHIND;
                return returnData;
            }
            offsetCursor = 0;
        } else {
            offsetCursor++;
        }

        logicalIndex++;
        direction = BEHIND;
        return returnData;
    }

      /**
       * Return true if there is another element before in the list.
       * @return
       */
      @Override
      public boolean hasPrevious() {
          return logicalIndex > 0;
      }

      /**
       * Go to the previous element.
       * @return
       */
      @Override
      public E previous() {
          if (!hasPrevious()) {
              throw new NoSuchElementException();
          }

          //if we are at the beginning of the node, go to previous node
          if(offsetCursor <= 0) {
              nodeCursor = nodeCursor.previous;
              offsetCursor = nodeCursor.count - 1;
          } else {
              offsetCursor--;
          }

          logicalIndex--;
          direction = AHEAD;
          return nodeCursor.data[offsetCursor];
      }

      /**
       * The value of the next index to be returned.
       * @return
       */
      @Override
      public int nextIndex() {
          return logicalIndex;
      }

      /**
       * The value of the previous index.
       * @return
       */
      @Override
      public int previousIndex() {
          return logicalIndex - 1;
      }

      /**
       * Remove an element from the list, depending on direction.
       */
      @Override
      public void remove() {
          if(direction == NONE) {
              throw new IllegalStateException();
          } else if(direction == AHEAD) {
              NodeInfo nodeInfo = find(logicalIndex);
              if(nodeInfo.node.next == tail && nodeInfo.node.count == 1) {
                  updateCursors();
                  StoutList.this.remove(logicalIndex + 1);
                  direction = NONE;
                  return;
              }

              StoutList.this.remove(logicalIndex);
              updateCursors();
              direction = NONE;
          } else {
              NodeInfo nodeInfo = find(logicalIndex - 1);
              if(nodeInfo.node.next == tail && nodeInfo.node.count == 1) {
                  StoutList.this.remove(logicalIndex - 1);
                  logicalIndex--;
                  updateCursors();
                  direction = NONE;
                  return;
              }

              if(nodeInfo.node.next == tail || nodeInfo.node.count > (nodeSize / 2)) {
                  StoutList.this.remove(logicalIndex - 1);
                  logicalIndex--;
                  updateCursors();
                  direction = NONE;
              } else {
                  StoutList.this.remove(logicalIndex - 1);
                  updateCursors();
                  direction = NONE;
              }
          }
      }

      /**
       * Set the last returned element to a new element.
       * @param e the element with which to replace the last element returned by
       *          {@code next} or {@code previous}
       */
      @Override
      public void set(E e) {
          if(direction == NONE) {
              throw new IllegalStateException();
          }

          if(direction == AHEAD) {
              nodeCursor.data[offsetCursor] = e;
          } else {

              if(offsetCursor == 0) {
                  nodeCursor.previous.data[nodeCursor.previous.count - 1] = e;
              } else {
                  nodeCursor.data[offsetCursor - 1] = e;
              }
          }
      }

      /**
       * Add an element to the list.
       * @param e the element to insert
       */
      @Override
      public void add(E e) {
        StoutList.this.add(logicalIndex, e);
        logicalIndex++;
        updateCursors();
        direction = NONE;
      }

      // Other methods you may want to add or override that could possibly facilitate
    // other operations, for instance, addition, access to the previous element, etc.
    // 
    // ...
    //

      /**
       * Helper method to update the nodeCursor and offsetCursor.
       */
      private void updateCursors() {
        nodeCursor = find(logicalIndex).node;
        offsetCursor = find(logicalIndex).offset;
      }

  }

    /**
     * Stores a node and an offset
     */
 private class NodeInfo {
      public Node node;
      public int offset;

      public NodeInfo(Node node, int offset) {
          this.node = node;
          this.offset = offset;
      }
 }

    /**
     * Helper method to find the Node and offset an element is in given the logical
     * position of the element.
     * @param pos logical index of element
     * @return NodeInfo which contains which Node and at what index the element is in
     */
 private NodeInfo find(int pos) {

     if (pos == -1) throw new IndexOutOfBoundsException();
     if (pos == size && pos % nodeSize == 0) {
         return new NodeInfo(tail, 0);
     }

     Node node = head.next;
     int count = 0;

     while (node != tail) {
         if (count + node.count <= pos) {
             count += node.count;
             node = node.next;
             continue;
         }
         return new NodeInfo(node, pos - count);
     }

     return new NodeInfo(node.previous, node.previous.count);
 }
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
      for(int i = 1; i < arr.length; i++) {
          E key = arr[i];
          int j = i - 1;

          while(j >= 0 && comp.compare(arr[j], key) > 0) {
              arr[j + 1] = arr[j];
              arr[j + 1] = arr[j];
              j--;
          }
          arr[j+1] = key;
      }
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
      for(int i = 0; i < arr.length - 1; i++) {
          for(int j = 0; j < arr.length - i - 1; j++) {
              if(arr[j].compareTo(arr[j+1]) < 0) {
                  //swap
                  E temp = arr[j];
                  arr[j] = arr[j+1];
                  arr[j+1] = temp;
              }
          }
      }
  }

    /**
     * Comparator used when calling insertion sort.
     * @param <E>
     */
    class ElementComparator<E extends Comparable<E>> implements Comparator<E> {
        /**
         * Compare two objects.
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return
         */
    @Override
    public int compare(E o1, E o2) {
        return o1.compareTo(o2);
    }
}
}
class MyLinkedList<T>
{
    //private Comparator<T> cp ;
    
    class Node
    {
        T data;
        Node next;
        Node parent;
        public Node(T obj)
        {
            data = obj;
            next = null;
            parent = null;
        }
    }
    private Node head;
    private Node tail;
    private int size;

    public synchronized int size()
    {
        return size;
    }

    public synchronized String toString()
    {
        String str= "";
        String temp = "";
        Node itr = head;
        while(itr != null)
        {
            temp = itr.data.toString();
            if(!temp.equals(""))
            {
                str = str+", "+temp;
            }
            itr = itr.next;
        }
        if(str.equals(""))
        {
            return str;
        }
        else
        {
            return str.substring(2);
        }
    }

    public synchronized Node head()
    {
        return head;
    }

    public synchronized Node tail()
    {
        return tail;
    }
    public synchronized Node search(T obj)
    {
        Node itr = head;
        while(itr != null && !obj.equals(itr.data))
        //while(itr != null && !cp.equalTo(obj,itr.data))
        {
            //System.out.println("Obj - "+obj.getClass().getName()+"itr - "+itr.data.getClass().getName());
            //System.out.println("Obj - "+obj+" itr - "+itr.data);
            //System.out.println(obj.equals(itr.data));
            itr = itr.next;

        }
        return itr;
    }

    public synchronized boolean isEmpty()
    {
        return size==0;
    }

    public synchronized void add(T data)
    {
        if(head==null)
        {
            head = new Node(data);
            tail = head;
        }
        else
        {
            Node temp = new Node(data);
            tail.next = temp;
            temp.parent = tail;
            tail = temp;
        }
        size++;
    }


    public synchronized void add(MyLinkedList<T> positions)
    {
        if(head == null)
        {
            head = positions.head();
            tail = positions.tail();
        }
        else
        {
            tail.next = positions.head();
            tail.next.parent = tail;
            tail = positions.tail();
        }
    }

    public synchronized Node remove(Node node)
    {
        size--;
        if(node == head)
        {
            if(head == tail)
            {
                head = null;
                tail = null;
            }
            else
            {
                head = head.next;
                head.parent = null;
            }
        }
        else
        {
            node.parent.next = node.next;
            if(node.next != null)
            {
                node.next.parent = node.parent;
            }
            else
            {
                tail = node.parent;
            }
        }
        return node.next;
    }

    public synchronized T remove()
    {
        if(head == null)
        {
            throw new IllegalStateException();
        }
        if(head == tail)
        {
            T temp = head.data;
            head = null;
            tail = null;
            size--;
            return temp;
        }
        else
        {
            T data = head.data;
            head = head.next;
            head.parent = null;
            size--;
            return data;
        }
    }
}




//abstract class Comparator<T>{
   //abstract public boolean equalTo(T w1,T w2);
//}
//
//class WCmp extends Comparator<WordEntry>{
    //public boolean equalTo();
//}

public class MySet<T>
{
    private MyLinkedList<T> objectSet;

    public MySet()
    {
        objectSet = new MyLinkedList<T>();
    }

    public MyLinkedList<T> getSet()
    {
        return objectSet;
    }

    public T search(T o)
    {
        MyLinkedList<T>.Node obj = objectSet.search(o);
        if(obj == null)
        {
            return null;
        }
        else
        {
            return obj.data;
        }
    }

    public int size()
    {
        return objectSet.size();
    }

    public boolean isEmpty()
    {
        return objectSet.isEmpty();
    }

    public boolean isMember(T o)
    {
        //System.out.println("ISMEMEBER -"+ o);
        //MyLinkedList<T>.Node temp = objectSet.search(o);
        //System.out.println(temp);
        return (objectSet.search(o) != null);
    }

    public void insert(T o) throws AlreadyPresent
    {
        if (!isMember(o))
        {
            objectSet.add(o);
        }
        else
        {
            throw new AlreadyPresent("Already Present");
        }
    }

    public void delete(T o) throws NotFound
    {
        MyLinkedList<T>.Node delNode = objectSet.search(o);
        if(delNode != null)
        {
            objectSet.remove(delNode);
        }
        else
        {
            throw new NotFound("Element not present in Set");
        }
    }

    public MySet<T> union(MySet<T> a)
    {
        MySet<T> unionSet = new MySet<T>();
        MyLinkedList<T>.Node itr = objectSet.head();
        while(itr != null)
        {
            try
            {
                unionSet.insert(itr.data);
            }
            catch(AlreadyPresent e)
            {

            }
            itr = itr.next;
        }
        itr = a.objectSet.head();
        while(itr != null)
        {
            try
            {
                unionSet.insert(itr.data);
            }
            catch(AlreadyPresent e)
            {

            }
            itr = itr.next;
        }
        return unionSet;
    }

    public MySet<T> intersection (MySet<T> a)
    {
        MySet<T> interSet = new MySet<T>();
        MyLinkedList<T>.Node itr = objectSet.head();
        while(itr != null)
        {
            try
            {
                interSet.insert(itr.data);
            }
            catch(AlreadyPresent e)
            {

            }
            itr = itr.next;
        }
        return interSet;
    }

    public String toString()
    {
        return objectSet.toString();
    }

    public static void main(String[] args)
    {
        try
        {
            MySet<Integer> a = new MySet<Integer>();
            System.out.println(a.isEmpty());
            a.insert(1);
            a.insert(2);
            a.insert(3);
            a.insert(4);
            a.insert(5);
            System.out.println(a.isEmpty());

            MySet<Integer> b = new MySet<Integer>();
            b.insert(4);
            b.insert(3);
            b.insert(0);
            b.insert(9);
            b.insert(7);
            b.insert(-1);
            System.out.println(a.isMember(1));
            System.out.println(a.isMember(7));
            System.out.println(a);
            a.delete(3);
            System.out.println(a);
            System.out.println(b);
            System.out.println(a.union(b));
            System.out.println(a.intersection(b));
        }
        catch (Exception e)
        {
            System.out.println("Except");
        }
    }

}

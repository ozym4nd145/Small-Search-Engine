import java.util.*;

public class AVL<T extends Comparable<T> >
{
    Node root;
    public class Node
    {
        T data;
        Node leftChild;
        Node rightChild;
        Node parent;
        int height;
        public Node(T obj)
        {
            data = obj;
            height = 1;
        }

        public String toString()
        {
            return "("+data+","+height+")";
        }
    }

    private void makeConnection(Node parent, Node child)
    {
        if(child != null)
        {
            if(parent.data.compareTo(child.data)<0)
            {
                parent.rightChild = child;
            }
            else
            {
                parent.leftChild = child;
            }
            child.parent = parent;
        }
        else
        {
            if(parent.leftChild != null && parent.rightChild != null)
            {
                throw new NullPointerException();
            }
        }

    }
    private int childHeight(Node node, boolean left)
    {
        Node child = (left)?(node.leftChild):(node.rightChild);
        return (child!=null)?(child.height):(0);
    }
    private boolean checkCondition(Node parent)
    {
        int height1 = childHeight(parent,true);
        int height2= childHeight(parent,false);
        return (Math.abs(height1-height2) <= 1);
    }
    private Node selectCorrectChild(Node grandpa,Node pa)
    {
        int height1 = childHeight(pa,true);
        int height2= childHeight(pa,false);
        if(height1 != height2)
        {
            return (height1>height2)?(pa.leftChild):(pa.rightChild);
        }
        else
        {
            return (grandpa.leftChild == pa)?(pa.leftChild):(pa.rightChild);
        }
    }
    public void rotate(Node parent, Node child)
    {
        // System.out.println(parent);
        // System.out.println(parent.leftChild);
        // System.out.println(parent.rightChild);
        // System.out.println(child.leftChild);
        // System.out.println(child.rightChild);
        Node transferChild;
        if(parent.leftChild == child)
        {
            transferChild = child.rightChild;
            child.rightChild = null;
            parent.leftChild = null;
        }
        else
        {
            transferChild = child.leftChild;
            child.leftChild = null;
            parent.rightChild = null;
        }
        // System.out.println(transferChild);
        child.parent = parent.parent;
        if(child.parent == null)
        {
            root = child;
        }
        else
        {
            if(child.parent.leftChild == parent)
            {
                child.parent.leftChild = child;
            }
            else
            {
                child.parent.rightChild = child;
            }
        }
        makeConnection(child,parent);
        makeConnection(parent,transferChild);
        updateHeight(parent);
        updateHeight(child);
        // System.out.println();
    }
    private void updateHeight(Node node)
    {
        int height1 = childHeight(node,true);
        int height2= childHeight(node,false);
        node.height = 1+ Math.max(height1,height2);
    }
    public Node search(T data)
    {
        Node curr = root;
        while(curr != null && curr.data.compareTo(data) != 0)
        {
            if(curr.data.compareTo(data)>0)
            {
                curr = curr.leftChild;
            }
            else
            {
                curr = curr.rightChild;
            }
        }
        return curr;
    }
    public Node successor(Node node)
    {
        Node successor = null;
        if(node.rightChild != null)
        {
            successor = node.rightChild;
            while(successor.leftChild != null)
            {
                successor = successor.leftChild;
            }
            return successor;
        }
        successor = node;
        node = node.parent;
        while(node != null && node.leftChild != successor)
        {
            successor = node;
            node = node.parent;
        }
        return node;
    }
    public Node predecessor(Node node)
    {
        Node predecessor = null;
        if(node.leftChild != null)
        {
            predecessor = node.leftChild;
            while(predecessor.rightChild != null)
            {
                predecessor = predecessor.rightChild;
            }
            return predecessor;
        }
        predecessor = node;
        node = node.parent;
        while(node != null && node.rightChild != predecessor)
        {
            predecessor = node;
            node = node.parent;
        }
        return node;
    }
    public void add(T data)
    {
        if (root == null)
        {
            root = new Node(data);
        }
        else
        {
            //BST insert
            Node curr = root;
            Node prev = null;
            Node newNode = new Node(data);
            while(curr != null && curr.data.compareTo(data) != 0)
            {
                prev = curr;
                if(curr.data.compareTo(data)<0)
                {
                    curr = curr.rightChild;
                }
                else
                {
                    curr = curr.leftChild;
                }
            }
            prev = (curr==null)?prev:curr;
            curr = newNode;
            makeConnection(prev,newNode);
            updateHeight(prev);
            // System.out.println(prev);
            // System.out.println(curr);
            //bubble Up
            while(prev != null && checkCondition(prev))
            {
                updateHeight(prev);
                curr = prev;
                prev = prev.parent;
            }
            if(prev == null)
            {
                root = curr;
            }
            else
            {
                Node x = prev;
                Node y = curr;
                Node z = selectCorrectChild(x,y);
                // System.out.println("X - "+x);
                // System.out.println("Y - "+y);
                // System.out.println("Z - "+z);
                Node newRoot;
                if(z!=null)
                {
                    if((z.data.compareTo(x.data)>0 && z.data.compareTo(y.data)<0)||(z.data.compareTo(x.data)<0 && z.data.compareTo(y.data)>0))
                    {
                        rotate(y,z);
                        // print(root);
                        rotate(x,z);
                        newRoot = x;
                    }
                    else
                    {
                        rotate(x,y);
                        newRoot = y;
                    }
                }
                else
                {
                    rotate(x,y);
                    newRoot = y;
                }
                if(newRoot.parent == null)
                {
                    root = newRoot;
                }
            }
        }
    }

    public MyLinkedList<T> elements()
    {
        MyLinkedList<T> list = new MyLinkedList<T>();
        getElement(root,list);
        return list;
    }

    protected void getElement(Node p, MyLinkedList<T> arr)
    {
        if(p==null)
        {
            return;
        }
        getElement(p.leftChild,arr);
        arr.add(p.data);
        getElement(p.rightChild,arr);
    }

    public void print(Node p)
    {
        Queue<Node> que = new LinkedList<Node>();
        que.add(p);
        int size = 0;
        boolean exitFlag=false;
        while(!exitFlag)
        {
            size = que.size();
            exitFlag = true;
            while(size > 0)
            {
                Node node = que.remove();
                if(node == null)
                {
                    System.out.print("  ,");
                }
                else
                {
                    System.out.print(node+",");
                }
                if(node != null)
                {
                    exitFlag = false;
                    que.add(node.leftChild);
                    que.add(node.rightChild);
                }
                else
                {
                    que.add(null);
                    que.add(null);
                }
                size--;
                if(size%2==0)
                {
                    System.out.print("|");
                }
            }
            System.out.println("");
        }
    }

    public static void main(String[] args)
    {
        AVL<Integer> node = new AVL<Integer>();
        Scanner in = new Scanner(System.in);
        int response;
        boolean exitFlag = false;
        while(!exitFlag)
        {
            System.out.print("Choose - \n\t1) Insert\n\t2) Search\n\t3) Print\n\t4) Maximum\n\t5) Minimum\n\t6) Predecessor\n\t7) Successor\n\t8) Delete\n\t9) Size\n\t10) Height\n\t0) Exit\n: ");
            response = in.nextInt();
            AVL<Integer>.Node resp = null;
            switch(response)
            {
                case 1:
                    System.out.print("Enter number to insert: ");
                    response = in.nextInt();
                    node.add(response);
                    break;
                case 2:
                    System.out.println("Enter number to search: ");
                    response = in.nextInt();
                    if(node.search(response) == null)
                    {
                        System.out.print("Not inserted\n");
                    }
                    else
                    {
                        System.out.print("Present in tree\n");
                    }
                    break;
                case 3:
                    node.print(node.root);
                    System.out.println(node.elements()+"\n");
                    break;
                case 6:
                    System.out.print("Enter the number to find predecessor of: ");
                    response = in.nextInt();
                    resp = node.search(response);
                    //System.out.print(resp.parent+"\n"+resp.parent.children[0]+" | "+resp.parent.children[1]+"\n"+resp.children[0]+" | "+resp.children[1]+"\n");
                    if(resp == null)
                    {
                        System.out.println("You entered a number not present in tree!");
                        break;
                    }
                    resp = node.predecessor(resp);
                    if(resp == null)
                    {
                        System.out.println("No predecessor! It is the smallest element");
                        break;
                    }
                    else
                    {
                        System.out.println(resp);
                    }
                    break;
                case 7:
                    System.out.print("Enter the number to find successor of: ");
                    response = in.nextInt();
                    resp = node.search(response);
                    if(resp == null)
                    {
                        System.out.println("You entered a number not present in tree!");
                        break;
                    }
                    resp = node.successor(resp);
                    if(resp == null)
                    {
                        System.out.println("No successor! It is the largest element");
                        break;
                    }
                    else
                    {
                        System.out.println(resp);
                    }
                    break;
                case 0:
                    System.out.println("Exiting");
                    exitFlag = true;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}

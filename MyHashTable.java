//essentially an array of linked list of HashNode
public class MyHashTable
{
    private long keys;
    private int SIZE;

    private MyLinkedList<HashNode>[] hashArray;

    @SuppressWarnings("unchecked")
    public MyHashTable()
    {
        keys = 0;
        SIZE = 1<<18 - 1;
        hashArray = new MyLinkedList[SIZE];
    }

    private int getHashIndex(String str)
    {
        int code = str.hashCode();
        if(code < 0)
        {
            return (Integer.MAX_VALUE + code)%SIZE;
        }
        else
        {
            return code%SIZE;
        }
    }

    public void addPositionsForWord(WordEntry w)
    {
        int index = getHashIndex(w.identifier());
        //System.out.println("Index - "+index+" Word - "+w);
        //new word insert
        HashNode word = new HashNode(w.identifier());
        word.addPositions(w.getAllPositionsForThisWord());

        if (hashArray[index] == null)
        {
            //System.out.println("new");
            hashArray[index] = new MyLinkedList<HashNode>();
            hashArray[index].add(word);
            keys++;
        }
        //already a value present
        else
        {
            try
            {
                //System.out.println("merge - "+w);
                //merging case
                HashNode node = hashArray[index].search(new HashNode(w.identifier())).data;
                //System.out.println(node);
                node.addPositions(w.getAllPositionsForThisWord());
            }
            catch(NullPointerException e)
            {
                //e.printStackTrace();
                //System.out.println("collison");
                //System.out.println(hashArray[index].head().data+" - "+w+" - "+index);
                //collison case - handled by chaining
                hashArray[index].add(word);
                keys++;
            }
        }
    }

    public HashNode getPositionForWord(String word)
    {
        int index = getHashIndex(word);
        //System.out.println("Get - index - "+index);
        if(hashArray[index] != null)
        {
            try
            {
                HashNode node = hashArray[index].search(new HashNode(word)).data;
                return node;
            }
            catch(NullPointerException e)
            {
            }
        }
        return null;
    }
    public long size()
    {
        return keys;
    }
}

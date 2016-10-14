//essentially an array of linked list of WordEntry
public class MyHashTable
{
    private long keys;
    private int SIZE;

    private MyLinkedList<WordEntry>[] hashArray;

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
        if (hashArray[index] == null)
        {
            //System.out.println("new");
            hashArray[index] = new MyLinkedList<WordEntry>();
            hashArray[index].add(w);
            keys++;
        }
        //already a value present
        else
        {
            try
            {
                //System.out.println("merge - "+w);
                //merging case
                WordEntry node = hashArray[index].search(w).data;
                //System.out.println(node);
                node.addPositions(w.getAllPositionsForThisWord());
            }
            catch(NullPointerException e)
            {
                //e.printStackTrace();
                //System.out.println("collison");
                //System.out.println(hashArray[index].head().data+" - "+w+" - "+index);
                //collison case - handled by chaining
                hashArray[index].add(w);
                keys++;
            }
        }
    }

    public WordEntry getPositionForWord(String word)
    {
        int index = getHashIndex(word);
        //System.out.println("Get - index - "+index);
        if(hashArray[index] != null)
        {
            try
            {
                WordEntry node = hashArray[index].search(new WordEntry(word)).data;
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

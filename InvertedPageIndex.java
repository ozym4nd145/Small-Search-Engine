public class InvertedPageIndex
{
    MyHashTable hashTable;
    public InvertedPageIndex()
    {
        hashTable = new MyHashTable();
    }

    public void addPage(PageEntry p)
    {
        MyLinkedList<WordEntry> words = p.getPageIndex().getWordEntries();
        MyLinkedList<WordEntry>.Node itr = words.head();

        while(itr != null)
        {
            hashTable.addPositionsForWord(itr.data);
            itr = itr.next;
        }
    }

    public WordEntry getWordEntry(String str)
    {
        return hashTable.getPositionForWord(str);
    }

    public MySet<PageEntry> getPagesWhichContainWord(String str)
    {
        WordEntry word = hashTable.getPositionForWord(str);
        if(word == null)
        {
            return null;
        }

        MyLinkedList<Position> positonList = word.getAllPositionsForThisWord();
        MySet<PageEntry> pageSet = new MySet<PageEntry>();

        MyLinkedList<Position>.Node itr = positonList.head();

        while(itr != null)
        {
            try
            {
                pageSet.insert(itr.data.getPageEntry());
            }
            catch(AlreadyPresent e)
            {
            }
            itr = itr.next;
        }
        return pageSet;
    }
}

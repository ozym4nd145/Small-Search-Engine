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

    public HashNode getWordEntry(String str)
    {
        return hashTable.getPositionForWord(str);
    }

    public MySet<PageEntry> getPagesWhichContainWord(String str)
    {
        HashNode word = hashTable.getPositionForWord(str);
        if(word == null)
        {
            return null;
        }

        MyLinkedList<Position> positionList = word.getAllPositionsForThisWord();
        MySet<PageEntry> pageSet = new MySet<PageEntry>();

        MyLinkedList<Position>.Node itr = positionList.head();

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

    public MySet<PageEntry> getPagesWhichContainAllWord(String str[])
    {
        if(str.length > 0)
        {
            MySet<PageEntry> possiblePages = getPagesWhichContainWord(str[0]);
            for (int i=1;i<str.length;i++)
            {
                possiblePages = possiblePages.intersection(getPagesWhichContainWord(str[i]));
                if(possiblePages.size() == 0)
                {
                    break;
                }
            }
            if(possiblePages.size() > 0)
            {
                return possiblePages;
            }
        }
        return null;
    }

    public MySet<PageEntry> getPagesWhichContainAnyWord(String str[])
    {
        if(str.length > 0)
        {
            MySet<PageEntry> possiblePages = getPagesWhichContainWord(str[0]);
            for (int i=1;i<str.length;i++)
            {
                possiblePages = possiblePages.union(getPagesWhichContainWord(str[i]));
                if(possiblePages.size() == 0)
                {
                    break;
                }
            }
            if(possiblePages.size() > 0)
            {
                return possiblePages;
            }
        }
        return null;
    }


    public MySet<PageEntry> getPagesWhichContainPhrase(String str[])
    {
        MySet<PageEntry> possiblePages = getPagesWhichContainAllWord(str);
        if(possiblePages != null)
        {
            MyLinkedList<PageEntry> pages = possiblePages.getSet();
            MyLinkedList<PageEntry>.Node itr = pages.head();
            MySet<PageEntry> validPages = new MySet<PageEntry>();
            while(itr != null)
            {
                // System.out.println("haha");
                if(itr.data.occurPhrase(str) != null)
                {
                    try
                    {
                        validPages.insert(itr.data);
                    }
                    catch(AlreadyPresent e)
                    {

                    }
                }
                // System.out.println("kakak");
                itr = itr.next;
            }
            if(validPages.size() > 0)
            {
                return validPages;
            }
        }
        return null;
    }
}

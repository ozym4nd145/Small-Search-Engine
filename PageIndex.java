public class PageIndex
{
    private MyLinkedList<WordEntry> presentWords;
    public PageIndex()
    {
        presentWords = new MyLinkedList<WordEntry>();
    }

    public void addPositionForWord(String str, Position p)
    {
        WordEntry node = null;
        try
        {
            //case when str is present in presentWords
            node = presentWords.search(new WordEntry(str)).data;
            //assuming no repetition
            node.addPosition(p);
        }
        catch(NullPointerException e)
        {
            //case when str is not present
            node = new WordEntry(str);
            node.addPosition(p);
            presentWords.add(node);
        }

    }

    public MyLinkedList<WordEntry> getWordEntries()
    {
        return presentWords;
    }
}

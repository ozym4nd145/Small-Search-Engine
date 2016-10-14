public class Position
{
    private PageEntry page;
    private int wordIndex;

    public Position(PageEntry p, int wordIndex)
    {
        page = p;
        this.wordIndex = wordIndex;
    }

    public PageEntry getPageEntry()
    {
        return page;
    }

    public int getWordIndex()
    {
        return wordIndex;
    }
    public String toString()
    {
        return ("< "+page.toString()+","+wordIndex+" >");
    }
}

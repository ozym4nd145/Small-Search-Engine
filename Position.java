public class Position implements Comparable<Position>
{
    private PageEntry page;
    private int wordIndex;
    private int fakeIndex;

    public Position(PageEntry p, int wordIndex, int fakeIndex)
    {
        page = p;
        this.wordIndex = wordIndex;
        this.fakeIndex = fakeIndex;
    }

    public PageEntry getPageEntry()
    {
        return page;
    }

    public int getWordIndex()
    {
        return wordIndex;
    }

    public int getFakeIndex()
    {
        return fakeIndex;
    }

    public String toString()
    {
        return ("< "+page.toString()+","+wordIndex+", "+fakeIndex+" >");
    }

    @Override
    public int compareTo(Position obj)
    {
        return this.fakeIndex - obj.getFakeIndex();
    }
}

public class SearchResult implements Comparable<SearchResult>
{
    private PageEntry page;
    private float relevance;
    public SearchResult(PageEntry p, float r)
    {
        page = p;
        relevance = r;
    }
    public PageEntry getPageEntry()
    {
        return page;
    }
    public float getRelevance()
    {
        return relevance;
    }
    public int compareTo(SearchResult obj)
    {
        return relevance - obj.getRelevance();
    }
    public String toString()
    {
        return "( "+page.toString()+", "+relevance+" )";
    }
}

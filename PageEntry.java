public class PageEntry
{
    private String name;
    private PageIndex pageIndex;

    public PageEntry(String pageName)
    {
        this.name = pageName;
        this.pageIndex = new PageIndex();
    }

    public void setPageIndex(PageIndex newIndex)
    {
        this.pageIndex = newIndex;
    }

    public PageIndex getPageIndex()
    {
        return this.pageIndex;
    }

    public String getName()
    {
        return name;
    }

    public boolean equals(Object obj)
    {
        return name.equals(((PageEntry)obj).getName());
    }

    public String toString()
    {
        return name;
    }
}

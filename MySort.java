import java.utils.*;

public class MySort
{
    public static ArrayList<SearchResult> sortThisList(MySet<SearchResult> listOfSortableEntries)
    {
        MyLinkedList<SearchResult> searches = listOfSortableEntries.getSet();
        MyLinkedList<SearchResult>.Node itr = searches.head();
        ArrayList<SearchResult> result = new ArrayList<SearchResult>();
        while(itr != null)
        {
            result.add(itr.data);
            itr = itr.next;
        }
        return Collections.sort(result);
    }
}

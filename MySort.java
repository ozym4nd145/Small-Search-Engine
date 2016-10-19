import java.util.*;

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
        Comparator<SearchResult> comparator = Collections.reverseOrder();
        // Collections.sort(result,comparator);
        MySort.quickSort(result,0,result.size()-1,comparator);
        return result;
    }

    private static <T> void quickSort(ArrayList<T> array, int first, int last, Comparator<T> comparator)
    {
        if(first>=last)
        {
            return;
        }
        T pivot = array.get(first);
        int less=first;int greater = last;
        T temp;
        while(less < greater)
        {
            while(less <= last && comparator.compare(array.get(less),pivot)<=0)
            {
                less++;
            }
            while(greater >= first && comparator.compare(array.get(greater),pivot)>0)
            {
                greater--;
            }
            if(less<greater)
            {
                temp = array.get(less);
                array.set(less,array.get(greater));
                array.set(greater,temp);
            }
        }
        temp = array.get(first);
        array.set(first,array.get(greater));
        array.set(greater,temp);
        quickSort(array,first,greater-1,comparator);
        quickSort(array,greater+1,last,comparator);
    }

    public static void main(String[] args)
    {
        ArrayList<Integer> array = new ArrayList<Integer>();
        int[] arr = new int[]{1,45,67,21,12,3425,231,45,99,12,45,1,3251,564,23,1,4,12,67};
        float[] arr1 = new float[]{(float)9.077,(float)4.207,(float)6.165};
        int len = arr.length;
        for(int i=0;i<len;i++)
        {
            array.add(arr[i]);
        }
        Comparator<Integer> comparator = Collections.reverseOrder();
        MySort.quickSort(array,0,len-1,comparator);
        for(int i=0;i<len;i++)
        {
            System.out.print(array.get(i)+" ");
        }
        System.out.println("");
    }
}

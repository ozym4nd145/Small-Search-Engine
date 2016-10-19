import java.util.*;
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

    public float getRelevanceOfPage(String str[], boolean repPhrase)
    {
        if(repPhrase)
        {
            // System.out.println("PHRASE");
            int[] positions = occurPhrase(str);
            float relevance = 0;
            for (int i=0;i<positions.length;i++)
            {
                // System.out.println(positions[i]);
                relevance += (1.0)/(positions[i]*positions[i]);
            }
            return relevance;
        }
        else
        {
            // System.out.println("ADD?OR");
            MyLinkedList<WordEntry> words = pageIndex.getWordEntries();
            float relevance = 0;
            int numWords = str.length;
            for(int i=0;i<numWords;i++)
            {
                try
                {
                    MyLinkedList<Position>.Node itr = words.search(new WordEntry(str[i])).data.getAllPositionsForThisWord().head();

                    while(itr != null)
                    {
                        // System.out.println(itr.data);
                        relevance += (1.0)/(itr.data.getWordIndex()*itr.data.getWordIndex());
                        itr = itr.next;
                    }
                }
                catch(NullPointerException e)
                {
                    // System.out.println("Check one time");
                }
            }
            return relevance;
        }
    }
    public int[] occurPhrase(String phrase[])
    {
        ArrayList<Integer> pos = new ArrayList<Integer>();
        int numWords = phrase.length;
        MyLinkedList<WordEntry> words = pageIndex.getWordEntries();
        // System.out.println(words);
        AVL<Position>[] phraseWords = new AVL[numWords-1];
        // System.out.println(numWords);
        // System.out.println(phraseWords.length);
        MyLinkedList<Position> firstWordPos = words.search(new WordEntry(phrase[0])).data.getAllPositionsForThisWord();
        // System.out.println(firstWordPos);
        MyLinkedList<Position>.Node iter = firstWordPos.head();
        // System.out.println(iter.data);
        for(int i=1;i<numWords;i++)
        {
            // System.out.println("&&");
            phraseWords[i-1] = words.search(new WordEntry(phrase[i])).data.getPositions();
        }
        while(iter != null)
        {
            // System.out.println(iter.data);
            int current_pos = iter.data.getFakeIndex();
            boolean isPhrase = true;
            for(int i=1;i<numWords;i++)
            {
                Position newPos = new Position(null,-4,current_pos+i);
                if(phraseWords[i-1].search(newPos) == null)
                {
                    isPhrase = false;
                    break;
                }
            }
            if(isPhrase)
            {
                pos.add(iter.data.getWordIndex());
            }
            iter = iter.next;
        }


        //MyLinkedList<Position>[] phraseWords = new MyLinkedList<Position>[numWords];
        //MyLinkedList<Position>.Node[] iter = new MyLinkedList<Position>.Node[numWords];
        //for(int i=0;i<phrase.length();i++)
        //{
            //phraseWords[i] = words.search(new WordEntry(phrase[i])).data.getAllPositionsForThisWord();
            //iter[i] = phraseWords[i].head();
        //}
        //boolean isDone = true;
        //boolean isPossible = true;
        //int index1 = 0;
        //int index2 = 0;
        //while(true)
        //{
            //isDone = true;
            //for(int i=0;i<numWords-1;i++)
            //{
                //index1 = iter[i].data.getFakeIndex();
                //index2 = iter[i+1].data.getFakeIndex();
                //if(index1 < index2 && index2-index1 != 1)
                //{
                    //iter[i] = iter[i].next();
                    //isDone = false;
                    //if(iter[i] == null)
                    //{
                        //isPossible = false;
                        //break;
                    //}
                //}
                //else if(index1 > index2)
                //{
                    //iter[i+1] = iter[i+1].next();
                    //isDone = false;
                    //if(iter[i+1] == null)
                    //{
                        //isPossible = false;
                        //break;
                    //}
                //}
            //}
            //if(isDone)
            //{
                //pos.add(iter[0].data.getWordIndex());
                //iter[0] = iter[0].next;
                //if(iter[0] == null)
                //{
                    //isPossible = false;
                //}
            //}
            //if(!isPossible)
            //{
                //break;
            //}
        //}
        if(pos.size() > 0)
        {
            int[] ret = new int[pos.size()];
            Iterator<Integer> iterator = pos.iterator();
            for (int i = 0; i < ret.length; i++)
            {
                ret[i] = iterator.next().intValue();
            }
            return ret;
        }
        else
        {
            return null;
        }
    }
}

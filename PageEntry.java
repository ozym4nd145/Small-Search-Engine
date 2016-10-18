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
            int[] positions = occurPhrase(str);
            float relevance = 0;
            for (int i=0;i<positions.length;i++)
            {
                relevance += (1.0)/(positions[i]*positions[i]);
            }
            return relevance;
        }
        else
        {
            float relevance = 0.0;
            int numWords = str.length();
            for(int i=0;i<numWords;i++)
            {
                try
                {
                    MyLinkedList<Position>.Node itr = words.search(new WordEntry(str[i])).data.getAllPositionsForThisWord().head();

                    while(itr != null)
                    {
                        relevance += (1.0)/(itr.data.getWordIndex()*itr.data.getWordIndex());
                        itr = itr.next;
                    }
                }
                catch(NullPointerException e)
                {
                    System.out.println("Check one time");
                }
            }
            return relevance;
        }
    }
    public int[] occurPhrase(String phrase[])
    {
        ArrayList<Integer> pos = new ArrayList<Integer>();
        int numWords = phrase.length();
        MyLinkedList<WordEntry> words = pageIndex.getWordEntries();

        AVL<Position>[] phraseWords = new AVL<Position>[numWords-1];
        MyLinkedList<Position> firstWordPos = words.search(new WordEntry(phrase[0])).data.getAllPositionsForThisWord();
        MyLinkedList<Position>.Node iter = firstWordPos.head();
        for(int i=1;i<numWords;i++)
        {
            phraseWords[i] = words.search(new WordEntry(phrase[i])).data.getPositions();
        }
        while(iter != null)
        {
            int current_pos = iter.data.getFakeIndex();
            boolean isPhrase = true;
            for(int i=1;i<numWords;i++)
            {
                Position newPos = new Position(null,-4,current_pos+i);
                if(phraseWords[i].search(newPos) == null)
                {
                    isPhrase = false;
                    break;
                }
            }
            if(isPhrase)
            {
                pos.add(current_pos);
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

import java.util.*;
import java.io.*;
public class SearchEngine
{
    private InvertedPageIndex wordSet;
    private MySet<PageEntry> pageSet;
    private MySet<String> connectorWords;
	public SearchEngine()
    {
        wordSet = new InvertedPageIndex();
        connectorWords = new MySet<String>();
        pageSet = new MySet<PageEntry> ();
        try
        {
            connectorWords.insert("a");
            connectorWords.insert("an");
            connectorWords.insert("the");
            connectorWords.insert("they");
            connectorWords.insert("these");
            connectorWords.insert("this");
            connectorWords.insert("for");
            connectorWords.insert("is");
            connectorWords.insert("are");
            connectorWords.insert("was");
            connectorWords.insert("of");
            connectorWords.insert("or");
            connectorWords.insert("and");
            connectorWords.insert("does");
            connectorWords.insert("will");
            connectorWords.insert("whose");
        }
        catch(AlreadyPresent e)
        {
        }
	}


    private String parseWord(String input)
    {
        String word =  input.toLowerCase();
        if(word.equals("stacks"))
        {
            word = "stack";
        }
        if(word.equals("structures"))
        {
            word = "structure";
        }
        if(word.equals("applications"))
        {
            word = "application";
        }
        return word;
    }

    private PageEntry parsePage(String fileName) throws NotFound
    {
        PageEntry page = new PageEntry(fileName);
        PageIndex index = page.getPageIndex();
        BufferedReader br = null;
        String input;
        String word;
        Scanner line;

        try
        {
            br = new BufferedReader( new FileReader("./webpages/"+fileName));
            int counter = 1;
            int fakeCounter = 1;
            while((input = br.readLine())!=null)
            {
                input = input.toLowerCase();
                String[] words = input.split("\\s++|\\{|}|<|>|\\(|\\)|\\.|,|;|'|\"|\\?|#|!|-|:");
                int size = words.length;
                for(int i=0;i<size;i++)
                {
                    if(!words[i].equals(""))
                    {
                        words[i] = parseWord(words[i]);
                        if(!connectorWords.isMember(words[i]))
                        {
                            // System.out.println(words[i]+" "+counter+" "+fakeCounter);
                            index.addPositionForWord(words[i], new Position(page,counter,fakeCounter));
                            fakeCounter++;
                        }
                        counter++;
                    }
                }
            }
        }
        catch(IOException e)
        {
            throw new NotFound("Error - \""+fileName+"\" File Does Not Exist");
        }
        finally
        {
            try
            {
                if(br != null)
                {
                    br.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return page;
    }
    private void printPages(ArrayList<SearchResult> pages)
    {
        int size = pages.size();
        for(int i=0;i<size;i++)
        {
            System.out.println(pages.get(i).getPageEntry());
        }
    }
    private ArrayList<SearchResult> getSortedPages(MySet<PageEntry> pages, String[] strings, boolean isPhrase)
    {
        MyLinkedList<PageEntry> pageList = pages.getSet();
        MyLinkedList<PageEntry>.Node itr = pageList.head();
        MySet<SearchResult> results = new MySet<SearchResult>();
        while(itr != null)
        {
            float rel = itr.data.getRelevanceOfPage(strings,isPhrase);
            try
            {
                results.insert(new SearchResult(itr.data,rel));
            }
            catch (AlreadyPresent e)
            {

            }
            itr = itr.next;
        }
        return MySort.sortThisList(results);
    }

	public void performAction(String actionMessage)
    {
        Scanner input = new Scanner(actionMessage);
        //System.out.println("pageSet - "+pageSet.size());
        try
        {
            String action = input.next();
            String word;
            String queryString;
            String pageName;
            PageEntry page;
            String[] strings;
            MySet<PageEntry> pages;
            ArrayList<String> words;
            switch(action)
            {
                case "addPage":
                    pageName = parseWord(input.next());
                    page = parsePage(pageName);
                    // System.out.println("Printing addressed page");
                    // System.out.println(page);
                    // PageIndex pi = page.getPageIndex();
                    // MyLinkedList<WordEntry> entries = pi.getWordEntries();
                    // MyLinkedList<WordEntry>.Node iter = entries.head();
                    // while(iter != null)
                    // {
                    //     if(iter.data.equals(new WordEntry("test")))
                    //     {
                    //         System.out.println("HHHHHHHHHHHHH");
                    //         MyLinkedList<Position> pos = iter.data.getPositions().elements();
                    //         MyLinkedList<Position>.Node itm = pos.head();
                    //         while(itm != null)
                    //         {
                    //             System.out.println(itm.data);
                    //             itm = itm.next;
                    //         }
                    //         System.out.println("HHHHHHHHHHHHH");
                    //     }
                    //     iter = iter.next;
                    // }
                    // System.out.println("Printing Done");
                    pageSet.insert(page);
                    wordSet.addPage(page);
                    System.out.println("-------Added page - \""+page+"\"--------\n");
                    System.out.println("");
                    break;

                case "queryFindPagesWhichContainWord":
                    word = parseWord(input.next());
                    MySet<PageEntry> containingPages = wordSet.getPagesWhichContainWord(word);
                    if(containingPages == null)
                    {
                        System.out.println("No webpages contains word \""+word+"\"\n");
                    }
                    else
                    {
                        String[] str = new String[1];
                        str[0] = word;
                        System.out.println("-------\""+word+"\" is present in pages -------");
                        printPages(getSortedPages(containingPages,str,false));
                        System.out.println("");
                    }
                    break;

                case "queryFindPagesWhichContainAllWords":
                    queryString = new String("");
                    words = new ArrayList<String>();
                    while(input.hasNext())
                    {
                        word = parseWord(input.next());
                        words.add(word);
                        queryString += word+" ";
                    }
                    strings = new String[words.size()];
                    for(int i=0;i<strings.length;i++)
                    {
                        strings[i] = words.get(i);
                    }

                    pages = wordSet.getPagesWhichContainAllWord(strings);
                    if(pages == null)
                    {
                        System.out.println("No Such Pages");
                    }
                    else
                    {
                        System.out.println("-----Pages containing all words \""+queryString+"\" ----");
                        printPages(getSortedPages(pages,strings,false));
                        System.out.println("");
                    }
                    break;

                case "queryFindPagesWhichContainAnyOfTheseWords":
                    queryString = new String("");
                    // System.out.println("ANY");
                    words = new ArrayList<String>();
                    while(input.hasNext())
                    {
                        word = parseWord(input.next());
                        words.add(word);
                        queryString += word+" ";
                    }
                    strings = new String[words.size()];
                    for(int i=0;i<strings.length;i++)
                    {
                        strings[i] = words.get(i);
                    }

                    pages = wordSet.getPagesWhichContainAnyWord(strings);
                    if(pages == null)
                    {
                        System.out.println("No Such Pages");
                    }
                    else
                    {
                        System.out.println("-----Pages containing any word \""+queryString+"\" -----");
                        printPages(getSortedPages(pages,strings,false));
                        System.out.println("");
                    }
                    break;

                case "queryFindPagesWhichContainPhrase":
                    queryString = new String("");
                    // System.out.println("PHRASE");
                    words = new ArrayList<String>();
                    while(input.hasNext())
                    {
                        word = parseWord(input.next());
                        words.add(word);
                        queryString += word+" ";
                    }
                    strings = new String[words.size()];
                    for(int i=0;i<strings.length;i++)
                    {
                        strings[i] = words.get(i);
                    }

                    pages = wordSet.getPagesWhichContainPhrase(strings);
                    if(pages == null)
                    {
                        System.out.println("No Such Pages");
                    }
                    else
                    {
                        System.out.println("-------Pages containing Phrase \""+queryString+"\" -----");
                        printPages(getSortedPages(pages,strings,true));
                        System.out.println("");
                    }
                    break;

                case "queryFindPositionsOfWordInAPage":
                    word = parseWord(input.next());
                    pageName = parseWord(input.next());
                    page = pageSet.search(new PageEntry(pageName));
                    if(page == null)
                    {
                        throw new NotFound("Webpage \""+pageName+"\" does not exist");
                    }

                    try
                    {
                        WordEntry foundWord =  page.getPageIndex().getWordEntries().search(new WordEntry(word)).data;
                        MyLinkedList<Position> positions = foundWord.getAllPositionsForThisWord();
                        MyLinkedList<Position>.Node itr = positions.head();
                        String indexString = "";
                        while(itr != null)
                        {
                            //System.out.println(itr.data.getPageEntry());
                            if(itr.data.getPageEntry().equals(page))
                            {
                                indexString += (itr.data.getWordIndex()+", ");
                            }
                            itr = itr.next;
                        }
                        if(indexString.equals(""))
                        {
                            System.out.println("Webpage "+pageName+" does not contain word "+word);
                        }
                        else
                        {
                            System.out.println("-----Index of \""+word+"\" in page \""+pageName+"\" -------");
                            System.out.println(indexString.substring(0,indexString.length()-2));
                            System.out.println("");
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.println("Webpage "+pageName+" does not contain word "+word);
                    }
                    break;


            }
        }
        catch(Exception e)
        {
            // e.printStackTrace();
            System.out.println(e.getMessage());
        }
	}
}

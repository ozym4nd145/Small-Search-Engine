import java.util.Scanner;
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
        return input.toLowerCase();
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
                        if(words[i].equals("stacks"))
                        {
                            words[i] = "stack";
                        }
                        if(words[i].equals("structures"))
                        {
                            words[i] = "structure";
                        }
                        if(words[i].equals("applications"))
                        {
                            words[i] = "application";
                        }
                        if(!connectorWords.isMember(words[i]))
                        {
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

    private MySet<SearchResult> getSortedPages(MySet<PageEntry> pages, String[] strings, boolean isPhrase)
    {
        MyLinkedList<PageEntry> pageList = pages.getSet();
        MyLinkedList<PageEntry>.Node itr = pageList.head();
        MySet<SearchResult> results;
        while(itr != null)
        {
            float rel = itr.data.getRelevanceOfPage(strings,isPhrase);
            results.add(new SearchResult(itr.data,relevance));
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
            String pageName;
            PageEntry page;
            switch(action)
            {
                case "addPage":
                    pageName = parseWord(input.next());
                    page = parsePage(pageName);
                    pageSet.insert(page);
                    wordSet.addPage(page);
                    System.out.println("Added page - \""+page+"\"");
                    break;

                case "queryFindPagesWhichContainWord":
                    word = parseWord(input.next());
                    MySet<PageEntry> containingPages = wordSet.getPagesWhichContainWord(word);
                    if(containingPages == null)
                    {
                        System.out.println("No webpages contains word \""+word+"\"");
                    }
                    else
                    {
                        String[] str = new String[1];
                        str[0] = word;
                        System.out.print("\""+word+"\" is present in pages - \"");
                        System.out.println(getSortedPages(containingPages,str,false) + "\"");
                    }
                    break;

                case "queryFindPagesWhichContainAllWords":
                    ArrayList<String> words = new ArrayList<String>();
                    while(input.hasNext())
                    {
                        words.add(parseWord(input.next());
                    }
                    String[] strings = new String[words.size()];
                    for(int i=0;i<strings.length;i++)
                    {
                        strings[i] = words.get(i);
                    }

                    MySet<PageEntry> pages = wordSet.getPagesWhichContainAllWord(strings)
                    if(pages == null)
                    {
                        System.out.println("No Such Pages");
                    }
                    else
                    {
                        System.out.println(getSortedPages(pages,strings,false));
                    }

                case "queryFindPagesWhichContainAnyOfTheseWords":
                    ArrayList<String> words = new ArrayList<String>();
                    while(input.hasNext())
                    {
                        words.add(parseWord(input.next());
                    }
                    String[] strings = new String[words.size()];
                    for(int i=0;i<strings.length;i++)
                    {
                        strings[i] = words.get(i);
                    }

                    MySet<PageEntry> pages = wordSet.getPagesWhichContainAnyWord(strings)
                    if(pages == null)
                    {
                        System.out.println("No Such Pages");
                    }
                    else
                    {
                        System.out.println(getSortedPages(pages,strings,false));
                    }

                case "queryFindPagesWhichContainPhrase":
                    ArrayList<String> words = new ArrayList<String>();
                    while(input.hasNext())
                    {
                        words.add(parseWord(input.next());
                    }
                    String[] strings = new String[words.size()];
                    for(int i=0;i<strings.length;i++)
                    {
                        strings[i] = words.get(i);
                    }

                    MySet<PageEntry> pages = wordSet.getPagesWhichContainPhrase(strings)
                    if(pages == null)
                    {
                        System.out.println("No Such Pages");
                    }
                    else
                    {
                        System.out.println(getSortedPages(pages,strings,true));
                    }

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
                            System.out.print("Index of \""+word+"\" in page \""+pageName+"\" - ");
                            System.out.println(indexString.substring(0,indexString.length()-2));
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
            System.out.println(e.getMessage());
        }
	}
}

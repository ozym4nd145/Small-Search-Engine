public class WordEntry
{
    private String word;
    private AVL<Position> position;

    public WordEntry(String word)
    {
        this.word = word;
        this.position = new AVL<Position>();
    }

    public void addPosition(Position position)
    {
        //check for redundancy?
        this.position.add(position);
        //System.out.println(this.position.elements());
    }

    public MyLinkedList<Position> getAllPositionsForThisWord()
    {
        return position.elements();
    }

    public String identifier()
    {
        return word;
    }
    public AVL<Position> getPositions()
    {
        return position;
    }
    //defining equality when identifiers are equal irrespective of position list
    @Override
    public boolean equals(Object obj)
    {
        return this.word.equals(((WordEntry)obj).identifier());
    }

    public String toString()
    {
        return word;
    }
}

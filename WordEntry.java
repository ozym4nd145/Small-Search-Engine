public class WordEntry
{
    private String word;
    private MyLinkedList<Position> position;

    public WordEntry(String word)
    {
        this.word = word;
        this.position = new MyLinkedList<Position>();
    }

    public void addPosition(Position position)
    {
        //check for redundancy?
        this.position.add(position);
    }

    public void addPositions(MyLinkedList<Position> positions)
    {
        this.position.add(positions);
    }

    public MyLinkedList<Position> getAllPositionsForThisWord()
    {
        return position;
    }

    public String identifier()
    {
        return word;
    }

    //defining equality when identifiers are equal irrespective of position list
    @Override
    public boolean equals(Object obj)
    {
        //System.out.println("calling");
        return this.word.equals(((WordEntry)obj).identifier());
    }

    public String toString()
    {
        return word;
    }
}

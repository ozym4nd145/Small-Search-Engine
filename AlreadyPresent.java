class AlreadyPresent extends Exception
{
    private String message = null;

    public AlreadyPresent() {
        super();
    }

    public AlreadyPresent(String message) {
        super(message);
        this.message = message;
    }

    public AlreadyPresent(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

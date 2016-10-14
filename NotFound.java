class NotFound extends Exception
{
    private String message = null;

    public NotFound() {
        super();
    }

    public NotFound(String message) {
        super(message);
        this.message = message;
    }

    public NotFound(Throwable cause) {
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

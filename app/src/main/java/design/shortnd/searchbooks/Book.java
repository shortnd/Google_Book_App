package design.shortnd.searchbooks;

/**
 * {@link Book} object contains information related to a single Book
 */
public class Book {

    /**
     * Title of the Book
     */
    private String mBookTitle;

    /**
     * Author of the Book
     * Returns the first Author if there are multiple in the array
     */
    private String mBookAuthor;

    /**
     * Description of the Book
     */
    private String mBookDescription;

    /**
     * Website URL of the Book
     */
    private String mBookUrl;

    /**
     * @param bookTitle       is the Title of the Book
     * @param bookAuthor      is the Author(s) of the Book
     * @param bookDescription is the Description of the Book
     * @param bookUrl         is the URL of the Current Book
     */
    public Book(String bookTitle, String bookAuthor, String bookDescription, String bookUrl) {
        mBookTitle = bookTitle;
        mBookAuthor = bookAuthor;
        mBookDescription = bookDescription;
        mBookUrl = bookUrl;
    }

    /**
     * Returns the Title of the Current Book
     */
    public String getBookTitle() {
        return mBookTitle;
    }

    /**
     * Returns the Author(s) of the Current Book
     */
    public String getBookAuthor() {
        return mBookAuthor;
    }

    /**
     * Returns the Description of the Current Book
     */
    public String getBookDescription() {
        return mBookDescription;
    }

    /**
     * Returns the website URL to find more information about the book
     */
    public String getBookUrl() {
        return mBookUrl;
    }

}

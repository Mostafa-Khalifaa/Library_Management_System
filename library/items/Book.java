package library.items;
public class Book extends LibraryItem {

    private String author;
    private int pages;

     public Book(String id, String title, String author, int pages) {
        super(id, title);
        this.author = author;
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {

        return pages;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPages(int pages) {

        this.pages = pages;
    }

    @Override
    public String getItemDetails() {
        return "Book [id: " + getId() + ", title: " + getTitle() +
                ", author: " + getAuthor() + ", pages: " + pages + "]";

    }

}

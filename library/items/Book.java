package library.items;

public class Book extends LibraryItem {
    private String author;
    private int pages;
    
    public Book(String id, String title, String author, int pages, int stock) {
        super(id, title, stock);
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
        return "Book [ID: " + getId() + ", Title: " + getTitle() + 
               ", Author: " + author + ", Pages: " + pages + 
               ", Stock: " + getStock() + ", Available: " + (isAvailable() ? "Yes" : "No") + "]";
    }
    
    @Override
    public void update(String... params) {
        if (params.length >= 3) {
            setTitle(params[0]);
            setAuthor(params[1]);
            setPages(Integer.parseInt(params[2]));
        }
    }
}
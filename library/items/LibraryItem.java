package library.items;

import library.interfaces.CRUDOperations;

public abstract class LibraryItem implements CRUDOperations {
    private String id;
    private String title;
    private int stock; // Number of copies available
    private boolean isAvailable;
    
    public LibraryItem(String id, String title, int stock) {
        this.id = id;
        this.title = title;
        this.stock = stock;
        this.isAvailable = stock > 0;
    }
    
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public int getStock() {
        return stock;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    

    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
        this.isAvailable = stock > 0;
    }
    
    public boolean borrowItem() {
        if (stock > 0) {
            stock--;
            isAvailable = stock > 0;
            return true;
        }
        return false;
    }
    
    public void returnItem() {
        stock++;
        isAvailable = true;
    }
    
    // CRUD Operations
    @Override
    public void create() {
        System.out.println("Library item created: " + title);
    }
    
    @Override
    public String read() {
        return getItemDetails();
    }
    
    @Override
    public void delete() {
        System.out.println("Library item deleted: " + title);
    }
    
    public abstract String getItemDetails();
}
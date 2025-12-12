package library.management;

import java.util.ArrayList;
import library.interfaces.CRUDOperations;
import library.items.LibraryItem;

public class Client implements CRUDOperations {
    private String id;
    private String name;
    private String email;
    private ArrayList<LibraryItem> borrowedItems;
    
    public Client(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedItems = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public ArrayList<LibraryItem> getBorrowedItems() {
        return borrowedItems;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public void borrowItem(LibraryItem item) {
        borrowedItems.add(item);
    }
    
    
    public boolean returnItem(String itemId) {
        for (int i = 0; i < borrowedItems.size(); i++) {
            if (borrowedItems.get(i).getId().equals(itemId)) {
                borrowedItems.remove(i);
                return true;
            }
        }
        return false;
    }
    
    // Display client details
    public String getClientDetails() {
        String details = "Client [ID: " + id + ", Name: " + name + ", Email: " + email + "]";
        details += "\nBorrowed Items: " + borrowedItems.size();
        if (!borrowedItems.isEmpty()) {
            details += "\n  Items:";
            for (LibraryItem item : borrowedItems) {
                details += "\n  - " + item.getTitle() + " (ID: " + item.getId() + ")";
            }
        }
        return details;
    }
    
    // CRUD Operations
    @Override
    public void create() {
        System.out.println("Client created: " + name);
    }
    
    @Override
    public String read() {
        return getClientDetails();
    }
    
    @Override
    public void update(String... params) {
        if (params.length >= 2) {
            setName(params[0]);
            setEmail(params[1]);
        }
    }
    
    @Override
    public void delete() {
        System.out.println("Client deleted: " + name);
    }
}
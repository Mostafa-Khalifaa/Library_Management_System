package library.management;

import java.util.ArrayList;
import library.exceptions.ItemNotFoundException;
import library.items.LibraryItem;

public class Library<T extends LibraryItem> {
    private ArrayList<T> items;
    
    public Library() {
        items = new ArrayList<>();
    }
    
    public boolean idExists(String id) {
        return items.stream()
                   .anyMatch(item -> item.getId().equals(id));
    }
    
    public void addItem(T item) {
        if (idExists(item.getId())) {
            System.out.println("Error: Item with ID " + item.getId() + " already exists!");
            return;
        }
        items.add(item);
        item.create();
        System.out.println("Item added successfully!");
    }
    
    public T getItemById(String id) throws ItemNotFoundException {
        return items.stream()
                   .filter(item -> item.getId().equals(id))
                   .findFirst()
                   .orElseThrow(() -> new ItemNotFoundException("Item with ID " + id + " not found!"));
    }
    
    public void updateItem(String id, String newTitle) throws ItemNotFoundException {
        T item = getItemById(id);
        item.setTitle(newTitle);
        System.out.println("Item updated successfully!");
    }
    
    public void deleteItem(String id) throws ItemNotFoundException {
        T item = getItemById(id);
        item.delete();
        items.remove(item);
        System.out.println("Item deleted successfully!");
    }
    
    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items in the library.");
        } else {
            System.out.println("\n--- Library Items ---");
            items.forEach(item -> System.out.println(item.getItemDetails()));
        }
    }
    
    public void displayAvailableItems() {
        System.out.println("\n--- Available Items ---");
        long count = items.stream()
                         .filter(LibraryItem::isAvailable)
                         .peek(item -> System.out.println(item.getItemDetails()))
                         .count();
        
        if (count == 0) {
            System.out.println("No items available.");
        }
    }
    
    public void searchByTitle(String keyword) {
        System.out.println("\n--- Search Results for: " + keyword + " ---");
        long count = items.stream()
                         .filter(item -> item.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                         .peek(item -> System.out.println(item.getItemDetails()))
                         .count();
        
        if (count == 0) {
            System.out.println("No items found.");
        }
    }
    
    public void addAll(ArrayList<? extends LibraryItem> newItems) {
        for (LibraryItem item : newItems) {
            items.add((T) item);
        }
        System.out.println(newItems.size() + " items added successfully!");
    }
}
package library.management;

import java.util.ArrayList;
import library.exceptions.ItemNotFoundException;
import library.items.LibraryItem;

public class Library<T extends LibraryItem> {

    private ArrayList<T> items;

 public Library(){
    items = new ArrayList<>();
 }


 public void addItem(T item){
    items.add(item);
    System.out.println("item added successfully");
 }

 // Get an item by ID
 public T getItemById(String id) throws ItemNotFoundException{
  for (T item : items){
    if(item.getId().equals(id)){
        return item;
    }
  }
  throw  new ItemNotFoundException("item not found for this id");
 }


public void updateItem(String id , String newTitle) throws ItemNotFoundException {
    T item = getItemById(id);
    item.setTitle(newTitle);
    System.out.println("item updated");

}
public void deleteItem(String id) throws ItemNotFoundException {
        T item = getItemById(id);
        items.remove(item);
        System.out.println("Item deleted successfully!");
    }
    
    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items in the library.");
        } else {
            System.out.println("\n  Library Items ");
            for (T item : items) {
                System.out.println(item.getItemDetails());
            }
        }
    }
    
    // Method using wildcards to add items from another collection
    public void addAll(ArrayList<? extends LibraryItem> newItems) {
        for (LibraryItem item : newItems) {
            items.add((T) item);
        }
        System.out.println(newItems.size() + " items added successfully!");
    }
}





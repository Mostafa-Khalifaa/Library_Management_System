package library.management;

import java.util.ArrayList;
import library.exceptions.ItemNotFoundException;
import library.items.LibraryItem;

public class ClientManager {
    private ArrayList<Client> clients;
    
    public ClientManager() {
        clients = new ArrayList<>();
    }
    
    // Check if ID exists using streams
    public boolean idExists(String id) {
        return clients.stream()
                     .anyMatch(client -> client.getId().equals(id));
    }
    
    // Add a client
    public void addClient(Client client) {
        if (idExists(client.getId())) {
            System.out.println("Error: Client with ID " + client.getId() + " already exists!");
            return;
        }
        clients.add(client);
        client.create();
        System.out.println("Client added successfully!");
    }
    
    // Get a client by ID using 
    public Client getClientById(String id) throws ItemNotFoundException {
        return clients.stream()
                     .filter(client -> client.getId().equals(id))
                     .findFirst()
                     .orElseThrow(() -> new ItemNotFoundException("Client with ID " + id + " not found!"));
    }
    
    // Update a client
    public void updateClient(String id, String newName, String newEmail) throws ItemNotFoundException {
        Client client = getClientById(id);
        client.update(newName, newEmail);
        System.out.println("Client updated successfully!");
    }
    
    // Delete a client
    public void deleteClient(String id) throws ItemNotFoundException {
        Client client = getClientById(id);
        
        // Check if client has borrowed items
        if (!client.getBorrowedItems().isEmpty()) {
            System.out.println("Error: Client has " + client.getBorrowedItems().size() + 
                             " borrowed items. Please return all items before deleting.");
            return;
        }
        
        client.delete();
        clients.remove(client);
        System.out.println("Client deleted successfully!");
    }
    
    
    public void displayAllClients() {
        if (clients.isEmpty()) {
            System.out.println("No clients registered.");
        } else {
            System.out.println("\n--- Library Clients ---");
            clients.forEach(client -> System.out.println(client.getClientDetails()));
        }
    }
    
    // Search clients by name using streams
    public void searchByName(String keyword) {
        System.out.println("\n--- Search Results for: " + keyword + " ---");
        long count = clients.stream()
                           .filter(client -> client.getName().toLowerCase().contains(keyword.toLowerCase()))
                           .peek(client -> System.out.println(client.getClientDetails()))
                           .count();
        
        if (count == 0) {
            System.out.println("No clients found.");
        }
    }
    
    public void borrowItem(String clientId, String itemId, Library<LibraryItem> library) 
            throws ItemNotFoundException {
        Client client = getClientById(clientId);
        LibraryItem item = library.getItemById(itemId);
        
        if (!item.isAvailable()) {
            System.out.println("Error: Item is not available. All copies are borrowed.");
            return;
        }
        
        if (item.borrowItem()) {
            client.borrowItem(item);
            System.out.println("Success! " + client.getName() + " borrowed: " + item.getTitle());
        } else {
            System.out.println("Error: Unable to borrow item.");
        }
    }
    
    public void returnItem(String clientId, String itemId, Library<LibraryItem> library) 
            throws ItemNotFoundException {
        Client client = getClientById(clientId);
        LibraryItem item = library.getItemById(itemId);
        
        if (client.returnItem(itemId)) {
            item.returnItem();
            System.out.println("Success! " + client.getName() + " returned: " + item.getTitle());
        } else {
            System.out.println("Error: This client hasn't borrowed this item.");
        }
    }
}
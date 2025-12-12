package library.management;
import java.util.ArrayList;
import library.exceptions.ItemNotFoundException;

// Class to manage library clients
public class ClientManager {

    private ArrayList<Client> clients;
    
    // Constructor
    public ClientManager() {
        clients = new ArrayList<>();
    }
    
    // Add a client
    public void addClient(Client client) {
        clients.add(client);
        System.out.println("Client added successfully!");
    }
    
    // Get a client by ID 
    public Client getClientById(String id) throws ItemNotFoundException {
        for (Client client : clients) {
            if (client.getId().equals(id)) {
                return client;
            }
        }
        throw new ItemNotFoundException("Client with ID " + id + " not found!");
    }
    
    // Update a client
    public void updateClient(String id, String newName, String newEmail) throws ItemNotFoundException {
        Client client = getClientById(id);
        client.setName(newName);
        client.setEmail(newEmail);
        System.out.println("Client updated successfully!");
    }
    
    // Delete a client
    public void deleteClient(String id) throws ItemNotFoundException {
        Client client = getClientById(id);
        clients.remove(client);
        System.out.println("Client deleted successfully!");
    }
    
    // Display all clients
    public void displayAllClients() {
        if (clients.isEmpty()) {
            System.out.println("No clients registered.");
        } else {
            System.out.println("\n--- Library Clients ---");
            for (Client client : clients) {
                System.out.println(client.getClientDetails());
            }
        }
    }
}
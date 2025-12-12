package library.main;

import java.util.Scanner;
import library.exceptions.ItemNotFoundException;
import library.items.*;
import library.management.*;

// Main class with menu system
public class LibraryManagementSystem {
    
    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);
    
    // Library and client manager instances
    private static Library<LibraryItem> library = new Library<>();
    private static ClientManager clientManager = new ClientManager();
    
    public static void main(String[] args) {
        System.out.println("Welcome to Library Management System");
        
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                switch (choice) {
                    case 1:
                        manageItems();
                        break;
                    case 2:
                        manageClients();
                        break;
                    case 3:
                        System.out.println("Thank you for using the system!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine(); // Clear buffer
            }
        }
        
        scanner.close();
    }
    
    // Display main menu
    private static void displayMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Manage Library Items");
        System.out.println("2. Manage Clients");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }
    
    // Manage library items submenu
    private static void manageItems() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n--- Item Management ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add Magazine");
            System.out.println("3. View Item Details");
            System.out.println("4. Update Book");
            System.out.println("5. Update Magazine");
            System.out.println("6. Delete Book");
            System.out.println("7. Delete Magazine");
            System.out.println("8. Display All Items");
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        addMagazine();
                        break;
                    case 3:
                        viewItemDetails();
                        break;
                    case 4:
                        updateBook();
                        break;
                    case 5:
                        updateMagazine();
                        break;
                    case 6:
                        deleteBook();
                        break;
                    case 7:
                        deleteMagazine();
                        break;
                    case 8:
                        library.displayAllItems();
                        break;
                    case 9:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (ItemNotFoundException e) {
                // Handle ItemNotFoundException without waiting for input
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                // Handle other exceptions (like invalid input)
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine(); // Clear buffer only for input mismatch
            }
        }
    }
    
    // Validate input is not empty
    private static String getValidInput(String prompt) {
        String input = "";
        while (input.trim().isEmpty()) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Error: Input cannot be empty. Please try again.");
            }
        }
        return input.trim();
    }
    
    // Get valid integer input
    private static int getValidIntInput(String prompt) {
        int value = -1;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print(prompt);
                value = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                if (value <= 0) {
                    System.out.println("Error: Please enter a positive number.");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine(); // Clear buffer
            }
        }
        return value;
    }
    
    // Add a book
    private static void addBook() {
        System.out.println("\n--- Add New Book ---");
        String id = getValidInput("Enter Book ID: ");
        String title = getValidInput("Enter Title: ");
        String author = getValidInput("Enter Author: ");
        int pages = getValidIntInput("Enter Number of Pages: ");
        
        Book book = new Book(id, title, author, pages);
        library.addItem(book);
    }
    
    // Add a magazine
    private static void addMagazine() {
        System.out.println("\n--- Add New Magazine ---");
        String id = getValidInput("Enter Magazine ID: ");
        String title = getValidInput("Enter Title: ");
        String publisher = getValidInput("Enter Publisher: ");
        int issue = getValidIntInput("Enter Issue Number: ");
        
        Magazine magazine = new Magazine(id, title, publisher, issue);
        library.addItem(magazine);
    }
    
    // View item details by ID
    private static void viewItemDetails() throws ItemNotFoundException {
        System.out.println("\n--- View Item Details ---");
        String id = getValidInput("Enter Item ID: ");
        
        LibraryItem item = library.getItemById(id);
        System.out.println("\n" + item.getItemDetails());
    }
    
    // Update a book
    private static void updateBook() throws ItemNotFoundException {
        System.out.println("\n--- Update Book ---");
        String id = getValidInput("Enter Book ID to update: ");
        
        LibraryItem item = library.getItemById(id);
        
        if (!(item instanceof Book)) {
            throw new ItemNotFoundException("This ID belongs to a Magazine, not a Book!");
        }
        
        Book book = (Book) item;
        
        String newTitle = getValidInput("Enter New Title: ");
        String newAuthor = getValidInput("Enter New Author: ");
        int newPages = getValidIntInput("Enter New Number of Pages: ");
        
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setPages(newPages);
        
        System.out.println("Book updated successfully!");
    }
    
    // Update a magazine
    private static void updateMagazine() throws ItemNotFoundException {
        System.out.println("\n--- Update Magazine ---");
        String id = getValidInput("Enter Magazine ID to update: ");
        
        LibraryItem item = library.getItemById(id);
        
        if (!(item instanceof Magazine)) {
            throw new ItemNotFoundException("This ID belongs to a Book, not a Magazine!");
        }
        
        Magazine magazine = (Magazine) item;
        
        String newTitle = getValidInput("Enter New Title: ");
        String newPublisher = getValidInput("Enter New Publisher: ");
        int newIssue = getValidIntInput("Enter New Issue Number: ");
        
        magazine.setTitle(newTitle);
        magazine.setPublisher(newPublisher);
        magazine.setIssueNumber(newIssue);
        
        System.out.println("Magazine updated successfully!");
    }
    
    // Delete a book
    private static void deleteBook() throws ItemNotFoundException {
        System.out.println("\n--- Delete Book ---");
        String id = getValidInput("Enter Book ID to delete: ");
        
        LibraryItem item = library.getItemById(id);
        
        if (!(item instanceof Book)) {
            throw new ItemNotFoundException("This ID belongs to a Magazine, not a Book!");
        }
        
        library.deleteItem(id);
    }
    
    // Delete a magazine
    private static void deleteMagazine() throws ItemNotFoundException {
        System.out.println("\n--- Delete Magazine ---");
        String id = getValidInput("Enter Magazine ID to delete: ");
        
        LibraryItem item = library.getItemById(id);
        
        if (!(item instanceof Magazine)) {
            throw new ItemNotFoundException("This ID belongs to a Book, not a Magazine!");
        }
        
        library.deleteItem(id);
    }
    
    // Manage clients submenu
    private static void manageClients() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n--- Client Management ---");
            System.out.println("1. Add Client");
            System.out.println("2. View Client Details");
            System.out.println("3. Update Client");
            System.out.println("4. Delete Client");
            System.out.println("5. Display All Clients");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                switch (choice) {
                    case 1:
                        addClient();
                        break;
                    case 2:
                        viewClientDetails();
                        break;
                    case 3:
                        updateClient();
                        break;
                    case 4:
                        deleteClient();
                        break;
                    case 5:
                        clientManager.displayAllClients();
                        break;
                    case 6:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (ItemNotFoundException e) {
                // Handle ItemNotFoundException without waiting for input
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                // Handle other exceptions (like invalid input)
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine(); // Clear buffer only for input mismatch
            }
        }
    }
    
    // Add a client
    private static void addClient() {
        System.out.println("\n--- Add New Client ---");
        String id = getValidInput("Enter Client ID: ");
        String name = getValidInput("Enter Name: ");
        String email = getValidInput("Enter Email: ");
        
        Client client = new Client(id, name, email);
        clientManager.addClient(client);
    }
    
    // View client details by ID
    private static void viewClientDetails() throws ItemNotFoundException {
        System.out.println("\n--- View Client Details ---");
        String id = getValidInput("Enter Client ID: ");
        
        Client client = clientManager.getClientById(id);
        System.out.println("\n" + client.getClientDetails());
    }
    
    // Update a client
    private static void updateClient() throws ItemNotFoundException {
        System.out.println("\n--- Update Client ---");
        String id = getValidInput("Enter Client ID to update: ");
        
        String newName = getValidInput("Enter New Name: ");
        String newEmail = getValidInput("Enter New Email: ");
        
        clientManager.updateClient(id, newName, newEmail);
    }
    
    // Delete a client
    private static void deleteClient() throws ItemNotFoundException {
        System.out.println("\n--- Delete Client ---");
        String id = getValidInput("Enter Client ID to delete: ");
        
        clientManager.deleteClient(id);
    }
}
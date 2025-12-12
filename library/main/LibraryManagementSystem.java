package library.main;

import java.util.Scanner;
import library.exceptions.ItemNotFoundException;
import library.items.*;
import library.management.*;

// Main class with menu system
public class LibraryManagementSystem {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Library<LibraryItem> library = new Library<>();
    private static ClientManager clientManager = new ClientManager();
    
    public static void main(String[] args) {
        System.out.println("LIBRARY MANAGEMENT SYSTEM");
        
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        manageItems();
                        break;
                    case 2:
                        manageClients();
                        break;
                    case 3:
                        borrowReturnMenu();
                        break;
                    case 4:
                        searchMenu();
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please select 1-5.");
                }
            } catch (Exception e) {
                System.out.println("\nError: Please enter a valid number.");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Manage Library Items");
        System.out.println("2. Manage Clients");
        System.out.println("3. Borrow/Return Items");
        System.out.println("4. Search");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static void manageItems() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n--- ITEM MANAGEMENT MENU ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add Magazine");
            System.out.println("3. View Item Details");
            System.out.println("4. Update Book");
            System.out.println("5. Update Magazine");
            System.out.println("6. Delete Book");
            System.out.println("7. Delete Magazine");
            System.out.println("8. Display All Items");
            System.out.println("9. Display Available Items");
            System.out.println("10. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
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
                        library.displayAvailableItems();
                        break;
                    case 10:
                        back = true;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.println("Returning to menu...");
                scanner.nextLine();
            }
        }
    }
    
    private static String getValidInput(String prompt) {
        String input = "";
        while (input.trim().isEmpty()) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
        return input.trim();
    }
    
    private static int getValidIntInput(String prompt) {
        int value = -1;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print(prompt);
                value = scanner.nextInt();
                scanner.nextLine();
                if (value <= 0) {
                    System.out.println("Please enter a positive number.");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return value;
    }
    
    private static void addBook() {
        System.out.println("\n--- ADD NEW BOOK ---");
        String id = getValidInput("Enter Book ID: ");
        String title = getValidInput("Enter Title: ");
        String author = getValidInput("Enter Author: ");
        int pages = getValidIntInput("Enter Number of Pages: ");
        int stock = getValidIntInput("Enter Stock (copies available): ");
        
        Book book = new Book(id, title, author, pages, stock);
        library.addItem(book);
    }
    
    private static void addMagazine() {
        System.out.println("\n--- ADD NEW MAGAZINE ---");
        String id = getValidInput("Enter Magazine ID: ");
        String title = getValidInput("Enter Title: ");
        String publisher = getValidInput("Enter Publisher: ");
        int issue = getValidIntInput("Enter Issue Number: ");
        int stock = getValidIntInput("Enter Stock (copies available): ");
        
        Magazine magazine = new Magazine(id, title, publisher, issue, stock);
        library.addItem(magazine);
    }
    
    private static void viewItemDetails() throws ItemNotFoundException {
        System.out.println("\n--- VIEW ITEM DETAILS ---");
        String id = getValidInput("Enter Item ID: ");
        
        LibraryItem item = library.getItemById(id);
        System.out.println("\n" + item.getItemDetails());
    }
    
    private static void updateBook() throws ItemNotFoundException {
        System.out.println("\n--- UPDATE BOOK ---");
        String id = getValidInput("Enter Book ID to update: ");
        
        LibraryItem item = library.getItemById(id);
        
        if (!(item instanceof Book)) {
            System.out.println("Error: This ID belongs to a Magazine, not a Book!");
            return;
        }
        
        Book book = (Book) item;
        
        String newTitle = getValidInput("Enter New Title: ");
        String newAuthor = getValidInput("Enter New Author: ");
        int newPages = getValidIntInput("Enter New Number of Pages: ");
        
        book.update(newTitle, newAuthor, String.valueOf(newPages));
        System.out.println("Book updated successfully!");
    }
    
    private static void updateMagazine() throws ItemNotFoundException {
        System.out.println("\n--- UPDATE MAGAZINE ---");
        String id = getValidInput("Enter Magazine ID to update: ");
        
        LibraryItem item = library.getItemById(id);
        
        if (!(item instanceof Magazine)) {
            System.out.println("Error: This ID belongs to a Book, not a Magazine!");
            return;
        }
        
        Magazine magazine = (Magazine) item;
        
        String newTitle = getValidInput("Enter New Title: ");
        String newPublisher = getValidInput("Enter New Publisher: ");
        int newIssue = getValidIntInput("Enter New Issue Number: ");
        
        magazine.update(newTitle, newPublisher, String.valueOf(newIssue));
        System.out.println("Magazine updated successfully!");
    }
    
    private static void deleteBook() throws ItemNotFoundException {
        System.out.println("\n--- DELETE BOOK ---");
        String id = getValidInput("Enter Book ID to delete: ");
        
        LibraryItem item = library.getItemById(id);
        
        if (!(item instanceof Book)) {
            System.out.println("Error: This ID belongs to a Magazine, not a Book!");
            return;
        }
        
        library.deleteItem(id);
    }
    
    private static void deleteMagazine() throws ItemNotFoundException {
        System.out.println("\n--- DELETE MAGAZINE ---");
        String id = getValidInput("Enter Magazine ID to delete: ");
        
        LibraryItem item = library.getItemById(id);
        
        if (!(item instanceof Magazine)) {
            System.out.println("Error: This ID belongs to a Book, not a Magazine!");
            return;
        }
        
        library.deleteItem(id);
    }
    
    private static void manageClients() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n--- CLIENT MANAGEMENT MENU ---");
            System.out.println("1. Add Client");
            System.out.println("2. View Client Details");
            System.out.println("3. Update Client");
            System.out.println("4. Delete Client");
            System.out.println("5. Display All Clients");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
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
                        System.out.println("\nInvalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.println("Returning to menu...");
                scanner.nextLine();
            }
        }
    }
    
    private static void addClient() {
        System.out.println("\n--- ADD NEW CLIENT ---");
        String id = getValidInput("Enter Client ID: ");
        String name = getValidInput("Enter Name: ");
        String email = getValidInput("Enter Email: ");
        
        Client client = new Client(id, name, email);
        clientManager.addClient(client);
    }
    
    private static void viewClientDetails() throws ItemNotFoundException {
        System.out.println("\n--- VIEW CLIENT DETAILS ---");
        String id = getValidInput("Enter Client ID: ");
        
        Client client = clientManager.getClientById(id);
        System.out.println("\n" + client.getClientDetails());
    }
    
    private static void updateClient() throws ItemNotFoundException {
        System.out.println("\n--- UPDATE CLIENT ---");
        String id = getValidInput("Enter Client ID to update: ");
        
        String newName = getValidInput("Enter New Name: ");
        String newEmail = getValidInput("Enter New Email: ");
        
        clientManager.updateClient(id, newName, newEmail);
    }
    
    private static void deleteClient() throws ItemNotFoundException {
        System.out.println("\n--- DELETE CLIENT ---");
        String id = getValidInput("Enter Client ID to delete: ");
        
        clientManager.deleteClient(id);
    }
    
    private static void borrowReturnMenu() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n--- BORROW/RETURN MENU ---");
            System.out.println("1. Borrow Item");
            System.out.println("2. Return Item");
            System.out.println("3. View Available Items");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        borrowItem();
                        break;
                    case 2:
                        returnItem();
                        break;
                    case 3:
                        library.displayAvailableItems();
                        break;
                    case 4:
                        back = true;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.println("Returning to menu...");
                scanner.nextLine();
            }
        }
    }
    
    private static void borrowItem() throws ItemNotFoundException {
        System.out.println("\n--- BORROW ITEM ---");
        String clientId = getValidInput("Enter Client ID: ");
        String itemId = getValidInput("Enter Item ID: ");
        
        clientManager.borrowItem(clientId, itemId, library);
    }
    
    private static void returnItem() throws ItemNotFoundException {
        System.out.println("\n--- RETURN ITEM ---");
        String clientId = getValidInput("Enter Client ID: ");
        String itemId = getValidInput("Enter Item ID: ");
        
        clientManager.returnItem(clientId, itemId, library);
    }
    
    private static void searchMenu() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n--- SEARCH MENU ---");
            System.out.println("1. Search Items by Title");
            System.out.println("2. Search Clients by Name");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter search keyword: ");
                        String itemKeyword = scanner.nextLine();
                        library.searchByTitle(itemKeyword);
                        break;
                    case 2:
                        System.out.print("Enter search keyword: ");
                        String clientKeyword = scanner.nextLine();
                        clientManager.searchByName(clientKeyword);
                        break;
                    case 3:
                        back = true;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.println("Returning to menu");
                scanner.nextLine();
            }
        }
    }
}
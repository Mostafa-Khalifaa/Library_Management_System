package library.interfaces;

public interface CRUDOperations {
    
    void create();
    
    String read();
    
    void update(String... params);
    
    void delete();
}
package library.items;

public class Magazine extends LibraryItem {
    private String publisher;
    private int issueNumber;
    
    public Magazine(String id, String title, String publisher, int issueNumber, int stock) {
        super(id, title, stock);
        this.publisher = publisher;
        this.issueNumber = issueNumber;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public int getIssueNumber() {
        return issueNumber;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }
    
    @Override
    public String getItemDetails() {
        return "Magazine [ID: " + getId() + ", Title: " + getTitle() + 
               ", Publisher: " + publisher + ", Issue: " + issueNumber + 
               ", Stock: " + getStock() + ", Available: " + (isAvailable() ? "Yes" : "No") + "]";
    }
    
    @Override
    public void update(String... params) {
        if (params.length >= 3) {
            setTitle(params[0]);
            setPublisher(params[1]);
            setIssueNumber(Integer.parseInt(params[2]));
        }
    }
}
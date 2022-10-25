package thewindmills.com.au.model;

import java.util.List;

public class BookMessage {
    String kind;
    Long totalItems;
    List<Book> items;
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public Long getTotalItems() {
        return totalItems;
    }
    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }
    public List<Book> getItems() {
        return items;
    }
    public void setItems(List<Book> items) {
        this.items = items;
    }

    
}

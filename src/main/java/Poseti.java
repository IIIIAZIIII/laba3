import lombok.Data;

import java.util.List;

@Data
public class Poseti {
    private String name;
    private String surname;
    private String phone;
    private boolean subscribed;
    private List<Book> favoriteBooks;
}

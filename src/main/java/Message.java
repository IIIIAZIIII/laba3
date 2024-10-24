import lombok.Data;

@Data
public class Message {
    public Message(String phone, String message) {
        this.phone = phone;
        this.message = message;
    }

    private String phone;
    private String message;
}

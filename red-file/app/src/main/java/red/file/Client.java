package red.file;

public class Client {
    private int id;
    private String FirstName;
    private String name;
    private String email;

    public Client(int id, String firstName, String name, String email) {
        this.id = id;
        this.FirstName = firstName;
        this.name = name;
        this.email = email;
    }
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
}

package Model;

public class User{
	
	private int id;
    private String username;
    private String password;
    
    // 3
    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }
    // 2
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    // 1
    public User(Integer id, String username, String password) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
	public String getPassword() {
		return password;
	}
}
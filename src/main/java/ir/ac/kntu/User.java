package ir.ac.kntu;

import java.util.ArrayList;

public class User {

    private String username;

    private String password;

    private String fullName;

    private String id;

    private String phone;

    private ArrayList<Ticket> tickets = new ArrayList<>();

    public User(String username, String password, String fullName, String id, String phone) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.id = id;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }
}

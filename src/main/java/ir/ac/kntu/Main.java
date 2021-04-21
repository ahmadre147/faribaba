package ir.ac.kntu; 

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    private static ArrayList<User> userData = new ArrayList<>();

    private static ArrayList<Ticket> ticketData = new ArrayList<>();

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        // This is practically just the default menu
        int choice = 0;
        while (choice != 4) {
            printDefaultMenu();
            choice = in.nextInt();
            in.nextLine();
            switch (choice){
                case 1:
                    defineUser();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    defineTicket();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("This option does not exist. Please try again.");
            }
        }

        in.close();
    }

    public static void handleTheOption(User currentUser){
        //This is the actual user menu
        int choice;
        do {
            printUserMenu();
            choice = in.nextInt();
            switch (choice){
                case 1:
                    showProfile(currentUser);
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    showUserTickets(currentUser);
                    break;
                case 4:
                    walletConfig(currentUser);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("This option does not exist. Please try again.");
            }
        } while (choice != 5);
    }

    private static void walletConfig(User currentUser) {
    }

    private static void showUserTickets(User currentUser) {
        for (Ticket ticket : currentUser.getTickets()){
            showTicket(ticket);
        }
    }

    private static void buyTicket() {
        Ticket ticket = new Ticket();
        in.nextLine();
        System.out.print("Please set your departure date (DD/MM/YYYY): ");
        ticket.setDate(new Date(in.nextLine()));
        ticket.setMode(initFlightMode());
        if (ticket.getMode().equals(Ticket.FlightMode.INTERNATIONAL)){
            ticket.setTravelClass(initTravelClass());
        }
        System.out.print("From: ");
        ticket.setSource(in.nextLine());
        System.out.print("To: ");
        ticket.setDestination(in.nextLine());

        Ticket selectedTicket = searchTickets(ticket);
    }

    private static Ticket searchTickets(Ticket ticket) {
        ArrayList<Ticket> foundTickets = new ArrayList<>();
        for (Ticket existingTicket : ticketData){
            if (ticket.getDate().equals(existingTicket.getDate()) && ticket.getSource().equals(existingTicket.getSource()) && ticket.getDestination().equals(existingTicket.getDestination())){
                existingTicket.setTravelClass(ticket.getTravelClass());
                foundTickets.add(existingTicket);
                System.out.println((foundTickets.indexOf(existingTicket)+1) + ":");
                showTicket(existingTicket);
            }
        }
        System.out.println("Select index of the Ticket you want to buy: ");
        int ticketChoice = in.nextInt();
        in.nextLine();

        return foundTickets.get(ticketChoice-1);
    }

    private static void showTicket(Ticket existingTicket) {
        System.out.println("\nFrom: " + existingTicket.getSource());
        System.out.println("To :" + existingTicket.getDestination());
        System.out.println("Date: " + existingTicket.getDate());
        System.out.println("Price: " +  existingTicket.calculatePrice());
        System.out.println();
    }

    private static void showProfile(User currentUser) {
        System.out.println("Username: " +  currentUser.getUsername());
        System.out.println("Name: " +  currentUser.getFullName());
        System.out.println("ID number: " +  currentUser.getId());
        System.out.println("Phone number: " +  currentUser.getPhone());
    }

    public static void defineUser(){
        System.out.print("Enter Username: ");
        String username = in.nextLine();
        System.out.print("Enter Password: ");
        String password = in.nextLine();
        System.out.print("Enter Full Name: ");
        String fullName = in.nextLine();
        System.out.print("Enter ID number: ");
        String id = in.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = in.nextLine();

        userData.add(new User(username, password, fullName, id, phone));
    }

    public static void defineTicket(){
        System.out.print("Enter flight ID: ");
        String id = in.nextLine();
        
        System.out.print("Enter flight base price (thousand Tomans): ");
        int basePrice = in.nextInt();
        in.nextLine();
        
        System.out.print("Enter date of flight (DD/MM/YYYY): ");
        String strDate = in.nextLine();
        Date date = new Date(strDate);
        
        System.out.print("Enter source of flight: ");
        String source = in.nextLine();
        
        System.out.print("Enter destination of flight: ");
        String destination = in.nextLine();
        
        Ticket.FlightMode mode = initFlightMode();

        Ticket ticket = new Ticket(id, basePrice, date, source, destination, mode);
        Ticket.TravelClass.setAdditionalPercentage(in);

        ticketData.add(ticket);
    }

    public static Ticket.FlightMode initFlightMode(){
        Ticket.FlightMode mode = null;
        while (mode == null){
            System.out.print("Is your flight International(I) or Domestic(D)? (D/I): ");
            String modeStr = in.nextLine();
            if (modeStr.strip().equals("D")) {
                mode = Ticket.FlightMode.DOMESTIC;
            } else if (modeStr.strip().equals("I")) {
                mode = Ticket.FlightMode.INTERNATIONAL;
            } else {
                System.out.println("Invalid entry. Please try again.");
            }
        }
        return mode;
    }

    public static Ticket.TravelClass initTravelClass(){
        Ticket.TravelClass travelClass = null;
        while (travelClass == null) {
            System.out.print("Is your flight class First, Business or Economy? (F/B/E): ");
            String classStr = in.nextLine();
            if (classStr.strip().equals("F")) {
                travelClass = Ticket.TravelClass.FIRST;
            } else if (classStr.strip().equals("B")) {
                travelClass = Ticket.TravelClass.BUSINESS;
            } else if (classStr.strip().equals("E")) {
                travelClass = Ticket.TravelClass.ECONOMY;
            } else {
                System.out.println("Invalid entry. Please try again.");
            }
        }
        return travelClass;
    }

    public static void login(){
        System.out.print("Enter Username: ");
        String username = in.nextLine();

        System.out.print("Enter Password: ");
        String password = in.nextLine();

        User login = userCheck(username, password);
        if(login != null){
            handleTheOption(login);
        } else {
            System.out.println("\nLogin failed. Please try again.");
        }
    }

    public static User userCheck(String username, String password){
        for (User user : userData){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public static void printUserMenu() {
        System.out.println("\n******************************");
        System.out.println("1-My Profile");
        System.out.println("2-Buy a ticket");
        System.out.println("3-Purchase history");
        System.out.println("4-My Wallet");
        System.out.println("5-Log out");
        System.out.println("******************************");
        System.out.print("\r\nPlease select your choice: ");
    }

    public static void printDefaultMenu() {
        System.out.println("\n******************************");
        System.out.println("1-Define a new user");
        System.out.println("2-Login user");
        System.out.println("3-Define a new ticket");
        System.out.println("4-Exit the program");
        System.out.println("******************************");
        System.out.print("\r\nPlease select your choice: ");
    }
}
package ir.ac.kntu; 

import java.util.Scanner;
import java.util.ArrayList;

public class Program {

    private ArrayList<User> userData;

    private ArrayList<Ticket> ticketData;

    private Scanner in;

    Program(){
        userData = new ArrayList<>();
        ticketData = new ArrayList<>();
        in = new Scanner(System.in);
    }

    public void handleFaribaba() {
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
                    defineFlight();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("This option does not exist. Please try again.");
            }
        }

        in.close();
    }

    public  void handleTheOption(User currentUser){
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
                    buyTicket(currentUser);
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

    private  void walletConfig(User currentUser) {
        System.out.println("Select between following operations: ");
        System.out.println("1. Show balance: ");
        System.out.println("2. Recharge and top up");
        System.out.println("3. Go back");

        int choice = in.nextInt();
        in.nextLine();

        switch (choice){
            case 1:
                System.out.println("Your balance is: " + currentUser.myWallet().getBalance() + "  IRR");
                break;
            case 2:
                rechargeWallet(currentUser);
                break;
            case 3:
                break;
            default:
                System.out.println("This option does not exist. Please try again.");
        }
    }

    private  void rechargeWallet(User currentUser) {
        System.out.print("Enter the additional amount (IRR): ");
        double add = in.nextDouble();
        in.nextLine();

        currentUser.myWallet().addBalance(add);
    }

    private  void showUserTickets(User currentUser) {
        for (Ticket ticket : currentUser.getTickets()){
            showTicket(ticket);
        }
    }

    private  void buyTicket(User currentUser) {
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
        if (selectedTicket == null){
            System.out.println("\nNo tickets found!");
            return;
        }
        if (currentUser.myWallet().getBalance() >= selectedTicket.calculatePrice()){
            currentUser.addTicket(selectedTicket);
            currentUser.myWallet().addBalance(-selectedTicket.calculatePrice());
            System.out.println("Purchase successful.");
        } else {
            System.out.println("Insufficient balance!");
        }

    }

    private  Ticket searchTickets(Ticket ticket) {
        ArrayList<Ticket> foundTickets = new ArrayList<>();

        for (Ticket existingTicket : ticketData){
            if (ticket.getDate().equals(existingTicket.getDate()) && ticket.getSource().equals(existingTicket.getSource()) && ticket.getDestination().equals(existingTicket.getDestination())){
                existingTicket.setTravelClass(ticket.getTravelClass());
                foundTickets.add(existingTicket);
                System.out.print("\n"+ (foundTickets.indexOf(existingTicket)+1) + ":");
                showTicket(existingTicket);
            }
        }

        if (foundTickets.size()>0){
            System.out.print("Select index of the Ticket you want to buy: ");
            int ticketChoice = in.nextInt();
            in.nextLine();
            return foundTickets.get(ticketChoice - 1);
        }
        return null;
    }

    private  void showTicket(Ticket existingTicket) {
        System.out.println("\nFlight ID: " + existingTicket.getId());
        System.out.println("From: " + existingTicket.getSource());
        System.out.println("To :" + existingTicket.getDestination());
        System.out.println("Date: " + existingTicket.getDate());
        System.out.println("Price: " +  existingTicket.calculatePrice());
        System.out.println();
    }

    private  void showProfile(User currentUser) {
        System.out.println("\n******** Profile ********\n");
        System.out.println("Username: " +  currentUser.getUsername());
        System.out.println("Name: " +  currentUser.getFullName());
        System.out.println("ID number: " +  currentUser.getId());
        System.out.println("Phone number: " +  currentUser.getPhone());
    }

    public  void defineUser(){
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

        userData.add(new User(username, password, fullName, id, phone, new Wallet(0)));
    }

    public  void defineFlight(){
        System.out.print("Enter flight ID: ");
        String id = in.nextLine();
        
        System.out.print("Enter flight base price (IRR): ");
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

        if (mode.equals(Ticket.FlightMode.INTERNATIONAL)){
            Ticket.TravelClass.setAdditionalPercentage(in);
        }

        ticketData.add(ticket);
    }

    public  Ticket.FlightMode initFlightMode(){
        Ticket.FlightMode mode = null;
        while (mode == null){
            System.out.print("Is your flight International(I) or Domestic(D)? (D/I): ");
            String modeStr = in.nextLine();
            switch (modeStr.strip()){
                case "D":
                    mode = Ticket.FlightMode.DOMESTIC;
                    break;
                case "I":
                    mode = Ticket.FlightMode.INTERNATIONAL;
                    break;
                default:
                    System.out.println("Invalid entry. Please try again.");
                    break;
            }
        }
        return mode;
    }

    public  Ticket.TravelClass initTravelClass(){
        Ticket.TravelClass travelClass = null;
        while (travelClass == null) {
            System.out.print("Is your flight class First, Business or Economy? (F/B/E): ");
            String classStr = in.nextLine();
            switch (classStr.strip()) {
                case "F":
                    travelClass = Ticket.TravelClass.FIRST;
                    break;
                case "B":
                    travelClass = Ticket.TravelClass.BUSINESS;
                    break;
                case "E":
                    travelClass = Ticket.TravelClass.ECONOMY;
                    break;
                default:
                    System.out.println("Invalid entry. Please try again.");
                    break;
            }
        }
        return travelClass;
    }

    public  void login(){
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

    public  User userCheck(String username, String password){
        for (User user : userData){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public  void printUserMenu() {
        System.out.println("\n******************************");
        System.out.println("1-My Profile");
        System.out.println("2-Buy a ticket");
        System.out.println("3-Purchase history");
        System.out.println("4-My Wallet");
        System.out.println("5-Log out");
        System.out.println("******************************");
        System.out.print("\r\nPlease select your choice: ");
    }

    public  void printDefaultMenu() {
        System.out.println("\n******************************");
        System.out.println("1-Sign Up");
        System.out.println("2-Login user");
        System.out.println("3-Define a new flight");
        System.out.println("4-Exit the program");
        System.out.println("******************************");
        System.out.print("\r\nPlease select your choice: ");
    }
}
package ir.ac.kntu;

import java.util.Scanner;

public class Ticket {

    private String id;

    private int basePrice;

    private Date date;

    private String source;

    private String destination;

    private TravelClass travelClass;

    private FlightMode mode;

    public enum FlightMode {
        DOMESTIC, INTERNATIONAL
    }

    public enum TravelClass{
        ECONOMY(), BUSINESS(), FIRST();

        double additionalPercentage;

        TravelClass(double additionalPercentage) {
            this.additionalPercentage = additionalPercentage;
        }

        TravelClass() {

        }

        public static void setAdditionalPercentage(Scanner in) {
            System.out.println("\nPlease enter additional percentage on price according to flight classes: ");
            System.out.print("Economy Class: ");
            ECONOMY.additionalPercentage = in.nextInt();
            in.nextLine();
            System.out.print("Business Class: ");
            BUSINESS.additionalPercentage = in.nextInt();
            in.nextLine();
            System.out.print("First Class: ");
            FIRST.additionalPercentage = in.nextInt();
            in.nextLine();
        }
    }

    public Ticket(){

    }

    public Ticket(String id, int basePrice, Date date, String source, String destination, FlightMode mode, TravelClass travelClass) {
        this.id = id;
        this.basePrice = basePrice;
        this.date = date;
        this.source = source;
        this.destination = destination;
        this.travelClass = travelClass;
        this.mode = mode;
    }

    public Ticket(String id, int basePrice, Date date, String source, String destination, FlightMode mode) {
        this.id = id;
        this.basePrice = basePrice;
        this.date = date;
        this.source = source;
        this.destination = destination;
        this.mode = mode;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public TravelClass getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(TravelClass travelClass) {
        this.travelClass = travelClass;
    }

    public FlightMode getMode() {
        return mode;
    }

    public void setMode(FlightMode mode) {
        this.mode = mode;
    }

    public double calculatePrice(){
        return mode.equals(FlightMode.INTERNATIONAL) ? basePrice*(1 + travelClass.additionalPercentage/100) : basePrice;
    }
}

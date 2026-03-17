/**
 * Book My Stay - Hotel Booking Management System
 *
 * This class demonstrates Use Case 2: Basic Room Types & Static Availability.
 *
 * Abstract Room class defines common attributes and behavior shared by all room types.
 * Concrete classes (SingleRoom, DoubleRoom, SuiteRoom) extend Room.
 * Availability is represented using simple static variables.
 *
 * @author Student
 * @version 2.1
 */

// Abstract class representing a generic Room
abstract class Room {
    protected String roomType;
    protected int numberOfBeds;
    protected double size; // in square meters
    protected double price; // per night

    // Constructor to initialize common attributes
    public Room(String roomType, int numberOfBeds, double size, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    // Abstract method to display room details
    public abstract void displayDetails();
}

// Concrete class for Single Room
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 20.0, 50.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(roomType + ": " + numberOfBeds + " bed(s), " +
                size + " sqm, $" + price + " per night");
    }
}

// Concrete class for Double Room
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 30.0, 80.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(roomType + ": " + numberOfBeds + " bed(s), " +
                size + " sqm, $" + price + " per night");
    }
}

// Concrete class for Suite Room
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 50.0, 150.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(roomType + ": " + numberOfBeds + " bed(s), " +
                size + " sqm, $" + price + " per night");
    }
}

// Main application class
public class UseCase1HotelBookingApp {

    // Static availability variables for each room type
    private static int singleRoomAvailability = 10;
    private static int doubleRoomAvailability = 5;
    private static int suiteRoomAvailability = 2;

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("       Welcome to Book My Stay App     ");
        System.out.println("       Hotel Booking System v2.1       ");
        System.out.println("======================================\n");

        // Initialize rooms using polymorphism
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Display room details
        singleRoom.displayDetails();
        System.out.println("Availability: " + singleRoomAvailability + "\n");

        doubleRoom.displayDetails();
        System.out.println("Availability: " + doubleRoomAvailability + "\n");

        suiteRoom.displayDetails();
        System.out.println("Availability: " + suiteRoomAvailability + "\n");

        System.out.println("Application terminated.");
    }
}
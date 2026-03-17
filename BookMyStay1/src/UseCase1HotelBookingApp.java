/**
 * Book My Stay - Hotel Booking Management System
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * This class demonstrates centralized management of room availability using HashMap.
 * Each room type is registered with an available count, and updates are handled through
 * controlled methods to ensure consistency.
 *
 * Key Concepts:
 * - Centralized HashMap inventory
 * - Encapsulation of inventory logic
 * - O(1) lookups and updates
 * - Single source of truth
 *
 * Author: Student
 * Version: 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Abstract Room class from previous use case
abstract class Room {
    protected String roomType;
    protected int numberOfBeds;
    protected double size; // in square meters
    protected double price; // per night

    public Room(String roomType, int numberOfBeds, double size, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public abstract void displayDetails();

    public String getRoomType() {
        return roomType;
    }
}

// Concrete room classes
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 20.0, 50.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(roomType + ": " + numberOfBeds + " bed(s), " + size + " sqm, $" + price + " per night");
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 30.0, 80.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(roomType + ": " + numberOfBeds + " bed(s), " + size + " sqm, $" + price + " per night");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 50.0, 150.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(roomType + ": " + numberOfBeds + " bed(s), " + size + " sqm, $" + price + " per night");
    }
}

// Centralized Room Inventory class
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor initializes inventory with room types and counts
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register a room type with initial availability
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Retrieve current availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update room availability (increase or decrease)
    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    // Display full inventory state
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " - Available: " + inventory.get(roomType));
        }
    }
}

// Main application class
public class UseCase1HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("       Welcome to Book My Stay App     ");
        System.out.println("       Hotel Booking System v3.1       ");
        System.out.println("======================================\n");

        // Initialize rooms using polymorphism
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Display room details
        singleRoom.displayDetails();
        doubleRoom.displayDetails();
        suiteRoom.displayDetails();

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType(singleRoom.getRoomType(), 10);
        inventory.addRoomType(doubleRoom.getRoomType(), 5);
        inventory.addRoomType(suiteRoom.getRoomType(), 2);

        // Display inventory state
        inventory.displayInventory();

        // Example update
        System.out.println("\nBooking 1 Single Room...");
        inventory.updateAvailability(singleRoom.getRoomType(), -1);

        System.out.println("Updated Inventory:");
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}
/**
 * Book My Stay - Hotel Booking Management System
 *
 * Use Case 4: Room Search & Availability Check
 *
 * This class demonstrates how guests can search for available rooms
 * and view room details without modifying the inventory.
 *
 * Key Concepts:
 * - Read-only access to inventory
 * - Defensive programming
 * - Separation of concerns (search logic isolated from booking logic)
 * - Domain model usage (Room objects provide details)
 *
 * Author: Student
 * Version: 4.0
 */

import java.util.HashMap;
import java.util.Map;

// Abstract Room class
abstract class Room {
    protected String roomType;
    protected int numberOfBeds;
    protected double size; // sqm
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
        System.out.println(roomType + ": " + numberOfBeds + " bed(s), "
                + size + " sqm, $" + price + " per night");
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 30.0, 80.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(roomType + ": " + numberOfBeds + " bed(s), "
                + size + " sqm, $" + price + " per night");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 50.0, 150.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(roomType + ": " + numberOfBeds + " bed(s), "
                + size + " sqm, $" + price + " per night");
    }
}

// Centralized Room Inventory class
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Read-only access to availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Display entire inventory
    public void displayInventory() {
        System.out.println("\nInventory Snapshot:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " - Available: " + entry.getValue());
        }
    }
}

// Search Service for read-only access
class RoomSearchService {
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Display available rooms only
    public void searchAvailableRooms(Room[] rooms) {
        System.out.println("\nSearch Results - Available Rooms:");
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) {
                room.displayDetails();
                System.out.println("Availability: " + available + "\n");
            }
        }
    }
}

// Main application class
public class UseCase1HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("       Welcome to Book My Stay App     ");
        System.out.println("       Hotel Booking System v4.0       ");
        System.out.println("======================================");

        // Initialize rooms
        Room[] rooms = new Room[] { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 10);
        inventory.addRoomType("Double Room", 0); // simulate no availability
        inventory.addRoomType("Suite Room", 2);

        // Search service (read-only)
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform search
        searchService.searchAvailableRooms(rooms);

        System.out.println("Application terminated.");
    }
}
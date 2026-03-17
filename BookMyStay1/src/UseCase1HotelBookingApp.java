/**
 * Book My Stay - Hotel Booking Management System
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * This class demonstrates confirming booking requests by assigning rooms
 * while ensuring inventory consistency and preventing double-booking.
 *
 * Key Concepts:
 * - Unique room ID assignment
 * - HashMap to map room types to allocated room IDs
 * - Set to enforce uniqueness of allocations
 * - Atomic update of inventory upon allocation
 *
 * Author: Student
 * Version: 6.0
 */

import java.util.*;

// Reservation class (from previous use case)
class Reservation {
    private String guestName;
    private String roomType;
    private int requestedNights;

    public Reservation(String guestName, String roomType, int requestedNights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.requestedNights = requestedNights;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getRequestedNights() {
        return requestedNights;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room: " + roomType + ", Nights: " + requestedNights;
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

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update inventory atomically (decrement for booking)
    public boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " - Available: " + entry.getValue());
        }
    }
}

// Booking Request Queue (from previous use case)
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation pollNextRequest() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }
}

// Booking Service - confirms reservations and assigns rooms
class BookingService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRoomIds; // roomType -> Set<roomID>
    private int roomIdCounter; // for generating unique room IDs

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRoomIds = new HashMap<>();
        roomIdCounter = 100; // starting room ID
    }

    // Confirm a reservation
    public void confirmReservation(Reservation reservation) {
        String roomType = reservation.getRoomType();
        // Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("Cannot confirm reservation for " + reservation.getGuestName()
                    + ". No " + roomType + " available.");
            return;
        }

        // Generate unique room ID
        String roomID;
        Set<String> allocatedSet = allocatedRoomIds.computeIfAbsent(roomType, k -> new HashSet<>());
        do {
            roomID = roomType.substring(0, 1).toUpperCase() + roomIdCounter++;
        } while (allocatedSet.contains(roomID));
        allocatedSet.add(roomID);

        // Allocate room and update inventory atomically
        boolean success = inventory.allocateRoom(roomType);
        if (success) {
            System.out.println("Reservation confirmed for " + reservation.getGuestName()
                    + ". Assigned Room ID: " + roomID);
        } else {
            System.out.println("Failed to allocate room for " + reservation.getGuestName()
                    + ". Please try later.");
        }
    }

    // Display allocated rooms
    public void displayAllocatedRooms() {
        System.out.println("\nAllocated Rooms:");
        for (Map.Entry<String, Set<String>> entry : allocatedRoomIds.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

// Main application class
public class UseCase1HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("       Welcome to Book My Stay App     ");
        System.out.println("       Hotel Booking System v6.0       ");
        System.out.println("======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);
        inventory.addRoomType("Suite Room", 1);

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        bookingQueue.addRequest(new Reservation("Alice", "Single Room", 2));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room", 3));
        bookingQueue.addRequest(new Reservation("Charlie", "Single Room", 1));
        bookingQueue.addRequest(new Reservation("Diana", "Suite Room", 2));

        // Initialize booking service
        BookingService bookingService = new BookingService(inventory);

        // Process booking requests in FIFO order
        while (!bookingQueue.isEmpty()) {
            Reservation res = bookingQueue.pollNextRequest();
            bookingService.confirmReservation(res);
        }

        // Display final inventory and allocations
        inventory.displayInventory();
        bookingService.displayAllocatedRooms();

        System.out.println("\nApplication terminated.");
    }
}
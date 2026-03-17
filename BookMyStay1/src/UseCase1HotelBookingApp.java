/**
 * Book My Stay - Hotel Booking Management System
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * This class demonstrates how guest booking requests are collected in a queue
 * to ensure fair processing based on arrival order.
 *
 * Key Concepts:
 * - Queue data structure (FIFO)
 * - Preservation of booking request order
 * - Decoupling request intake from allocation
 *
 * Author: Student
 * Version: 5.0
 */

import java.util.LinkedList;
import java.util.Queue;

// Reservation class represents a guest booking request
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

// BookingRequestQueue handles incoming booking requests in FIFO order
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add a new booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation); // enqueue
        System.out.println("Booking request added: " + reservation);
    }

    // Peek at the next request (read-only)
    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }

    // Retrieve and remove the next request for processing
    public Reservation pollNextRequest() {
        return requestQueue.poll();
    }

    // Display all pending requests
    public void displayAllRequests() {
        System.out.println("\nPending Booking Requests (FIFO Order):");
        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        for (Reservation res : requestQueue) {
            System.out.println(res);
        }
    }

    // Get current queue size
    public int getQueueSize() {
        return requestQueue.size();
    }
}

// Main application class
public class UseCase1HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("       Welcome to Book My Stay App     ");
        System.out.println("       Hotel Booking System v5.0       ");
        System.out.println("======================================");

        // Initialize booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate guests submitting booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room", 2));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room", 3));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room", 1));

        // Display current pending requests
        bookingQueue.displayAllRequests();

        // Peek at the next request (without removing)
        System.out.println("\nNext request to process (peek): " + bookingQueue.peekNextRequest());

        // Poll the next request (retrieve & remove)
        Reservation next = bookingQueue.pollNextRequest();
        System.out.println("Processing next request: " + next);

        // Display updated pending requests
        bookingQueue.displayAllRequests();

        System.out.println("\nApplication terminated.");
    }
}
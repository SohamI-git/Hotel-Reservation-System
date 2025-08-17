 import java.util.*;


class Room {
    private int roomId;
    private String type;
    private double price;
    private boolean isAvailable;

    public Room(int roomId, String type, double price) {
        this.roomId = roomId;
        this.type = type;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void bookRoom() {
        this.isAvailable = false;
    }

    public void releaseRoom() {
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return "Room{" +
                "ID=" + roomId +
                ", Type='" + type + '\'' +
                ", Price=" + price +
                ", Available=" + isAvailable +
                '}';
    }
}


class Reservation {
    private static int idCounter = 1;
    private int reservationId;
    private String customerName;
    private Room room;

    public Reservation(String customerName, Room room) {
        this.reservationId = idCounter++;
        this.customerName = customerName;
        this.room = room;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "ID=" + reservationId +
                ", Customer='" + customerName + '\'' +
                ", Room=" + room.getRoomId() +
                ", Type=" + room.getType() +
                '}';
    }
}


class ReservationSystem {
    private List<Room> rooms = new ArrayList<>();
    private Map<Integer, Reservation> reservations = new HashMap<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void viewRooms() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public void bookRoom(String customerName, int roomId) {
        for (Room room : rooms) {
            if (room.getRoomId() == roomId && room.isAvailable()) {
                room.bookRoom();
                Reservation reservation = new Reservation(customerName, room);
                reservations.put(reservation.getReservationId(), reservation);
                System.out.println(" Room booked successfully! Reservation ID: " + reservation.getReservationId());
                return;
            }
        }
        System.out.println(" Room not available or invalid Room ID.");
    }

    public void cancelReservation(int reservationId) {
        if (reservations.containsKey(reservationId)) {
            Reservation reservation = reservations.get(reservationId);
            reservation.getRoom().releaseRoom();
            reservations.remove(reservationId);
            System.out.println(" Reservation cancelled successfully.");
        } else {
            System.out.println(" Invalid Reservation ID.");
        }
    }

    public void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation r : reservations.values()) {
                System.out.println(r);
            }
        }
    }
}


public class HotelReservationApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ReservationSystem system = new ReservationSystem();

        
        system.addRoom(new Room(101, "Single", 1200));
        system.addRoom(new Room(102, "Double", 2000));
        system.addRoom(new Room(103, "Deluxe", 3500));
        system.addRoom(new Room(104, "Deluxe", 3000));
        system.addRoom(new Room(105, "Deluxe", 1200));

        int choice;
        do {
            System.out.println("\n===== Hotel Reservation System =====");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Reservations");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    system.viewRooms();
                    break;
                case 2:
                    System.out.print("Enter Customer Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Room ID to book: ");
                    int roomId = sc.nextInt();
                    system.bookRoom(name, roomId);
                    break;
                case 3:
                    System.out.print("Enter Reservation ID to cancel: ");
                    int resId = sc.nextInt();
                    system.cancelReservation(resId);
                    break;
                case 4:
                    system.viewReservations();
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }
}

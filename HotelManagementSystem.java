import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Room
{
    private int number;
    private String type;
    private Guest occupant;
    private boolean isCleaned;

    public Room(int number, String type)
    {
        this.number = number;
        this.type = type;
        this.occupant = null;
        this.isCleaned = true;
    }

    public boolean isAvailable()
     {
        return occupant == null && isCleaned;
     }

    public void setAvailability(boolean available)
     {
        isCleaned = !available;
     }

    public void checkInGuest(Guest guest) 
    {
        if (isAvailable())
         {
            occupant = guest;
            System.out.println("Guest checked in successfully to Room " + number + " (" + type + ")");
        } else
         {
            System.out.println("Room is not available for check-in.");
        }
    }

    public void checkOutGuest()
     {
        if (occupant != null)
         {
            occupant = null;
            System.out.println("Room cleaned and ready for next guest.");
        } else
         {
            System.out.println("Room is already empty.");
        }
    }

    public String getDetails()
     {
        return "Room " + number + " (" + type + "): " + (occupant == null ? "Vacant" : occupant.getName());
    }
}

class Guest
 {
    private String name;
    private Room currentRoom;

    public Guest(String name)
     {
        this.name = name;
        this.currentRoom = null;
    }

    public void setCurrentRoom(Room room)
     {
        this.currentRoom = room;
    }

    public String getCurrentRoom()
     {
        return currentRoom == null ? "None" : currentRoom.getDetails();
    }

    public String getName()
     {
        return name;
    }
}

class Hotel
 {
    private Map<String, Room> rooms;
    private ArrayList<Guest> guests;

    public Hotel()
     {
        rooms = new HashMap<>();
        guests = new ArrayList<>();

        // Initialize rooms
        rooms.put("suite1", new Room(1, "Suite"));
        rooms.put("suite2", new Room(2, "Suite"));
        rooms.put("deluxe1", new Room(3, "Deluxe"));
        rooms.put("deluxe2", new Room(4, "Deluxe"));
        rooms.put("standard1", new Room(5, "Standard"));
        rooms.put("standard2", new Room(6, "Standard"));

        // Set initial availability
        rooms.get("suite1").setAvailability(true);
        rooms.get("suite2").setAvailability(true);
        rooms.get("deluxe1").setAvailability(true);
        rooms.get("deluxe2").setAvailability(true);
        rooms.get("standard1").setAvailability(true);
        rooms.get("standard2").setAvailability(true);
    }

    public Room getRoom(String roomName)
     {
        return rooms.get(roomName);
    }

    public void checkInGuest(Guest guest, String roomName)
     {
        Room room = getRoom(roomName);
        if (room.isAvailable()) {
            room.checkInGuest(guest);
            guest.setCurrentRoom(room);
            System.out.println("Check-in successful.");
        } else {
            System.out.println("Room is not available for check-in.");
        }
    }

    public void checkOutGuest(String roomName) 
    {
        Room room = getRoom(roomName);
        if (room != null)
         {
            room.checkOutGuest();
            System.out.println("Check-out successful.");
        } else 
        {
            System.out.println("Room not found.");
        }
    }

    public void viewOccupancyReport()
     {
        System.out.println("\n--- Occupancy Report ---");
        for (Map.Entry<String, Room> entry : rooms.entrySet()) 
        {
            System.out.println(entry.getKey() + ": " + entry.getValue().getDetails());
        }
    }

    public void viewGuestList()
     {
        System.out.println("\n--- Guest List ---");
        for (Guest guest : guests)
         {
            System.out.println(guest.getName() + ": " + guest.getCurrentRoom());
        }
    }
}

public class HotelManagementSystem
 {
    public static void main(String[] args)
     {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);

        while (true)
         {
            System.out.println("\n--- Hotel Management System ---");
            System.out.println("1. Check-in guest");
            System.out.println("2. Check-out guest");
            System.out.println("3. View occupancy report");
            System.out.println("4. View guest list");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.next();
                    System.out.print("Enter room name (suite1/suite2/deluxe1/deluxe2/standard1/standard2): ");
                    String roomName = scanner.next();
                    Guest guest = new Guest(guestName);
                    hotel.checkInGuest(guest, roomName);
                    break;
                case 2:
                    System.out.print("Enter room name (suite1/suite2/deluxe1/deluxe2/standard1/standard2): ");
                    String checkoutRoom = scanner.next();
                    hotel.checkOutGuest(checkoutRoom);
                    break;
                case 3:
                    hotel.viewOccupancyReport();
                    break;
                case 4:
                    hotel.viewGuestList();
                    break;
                case 5:
                    System.out.println("Exiting system.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
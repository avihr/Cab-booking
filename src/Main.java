import java.util.ArrayList;
import java.util.List;

class User {
    private String name;
    private char gender;
    private int age;

    public User(String name, char gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    // Getters
    public String getName() {
        return name;
    }

    public char getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}

class Driver {
    private String name;
    private char gender;
    private int age;
    private String vehicleDetails;
    private Location currentLocation;
    private boolean available;

    public Driver(String name, char gender, int age, String vehicleDetails, Location currentLocation) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.vehicleDetails = vehicleDetails;
        this.currentLocation = currentLocation;
        this.available = true;
    }

    // Getters
    public String getName() {
        return name;
    }

    public char getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getVehicleDetails() {
        return vehicleDetails;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    // Setters
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class CabBookingSystem {
    private List<User> users;
    private List<Driver> drivers;

    public CabBookingSystem() {
        users = new ArrayList<>();
        drivers = new ArrayList<>();
    }

    public void addUser(String name, char gender, int age) {
        users.add(new User(name, gender, age));
    }

    public void addDriver(String name, char gender, int age, String vehicleDetails, int x, int y) {
        drivers.add(new Driver(name, gender, age, vehicleDetails, new Location(x, y)));
    }

    public List<Driver> findRide(String username, int sourceX, int sourceY, int destinationX, int destinationY) {
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : drivers) {
            if (driver.isAvailable() && isWithinRange(driver.getCurrentLocation(), sourceX, sourceY, 5)) {
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }

    public boolean chooseRide(String username, String driverName) {
        for (Driver driver : drivers) {
            if (driver.getName().equals(driverName)) {
                driver.setAvailable(false);
                return true;
            }
        }
        return false;
    }

    private boolean isWithinRange(Location driverLocation, int sourceX, int sourceY, int range) {
        int distance = Math.abs(driverLocation.getX() - sourceX) + Math.abs(driverLocation.getY() - sourceY);
        return distance <= range;
    }
}

public class Main {
    public static void main(String[] args) {
        CabBookingSystem cabSystem = new CabBookingSystem();

        // Onboard users
        cabSystem.addUser("Abhishek", 'M', 23);
        cabSystem.addUser("Rahul", 'M', 29);
        cabSystem.addUser("Nandini", 'F', 22);

        // Onboard drivers
        cabSystem.addDriver("Driver1", 'M', 22, "Swift, KA-01-12345", 10, 1);
        cabSystem.addDriver("Driver2", 'M', 29, "Swift, KA-01-12345", 11, 10);
        cabSystem.addDriver("Driver3", 'M', 24, "Swift, KA-01-12345", 5, 3);

        // Finding ride for users
        List<Driver> ride1 = cabSystem.findRide("Abhishek", 0, 0, 20, 1);
        if (ride1.isEmpty()) {
            System.out.println("No ride found for Abhishek.");
        }

        List<Driver> ride2 = cabSystem.findRide("Rahul", 10, 0, 15, 3);
        if (!ride2.isEmpty()) {
            System.out.println("Ride found for Rahul: " + ride2.get(0).getName());
            cabSystem.chooseRide("Rahul", ride2.get(0).getName());
        }

        List<Driver> ride3 = cabSystem.findRide("Nandini", 15, 6, 20, 4);
        if (ride3.isEmpty()) {
            System.out.println("No ride found for Nandini.");
        }
    }
}

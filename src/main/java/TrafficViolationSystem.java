import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TrafficViolationSystem {

    // =========================
    // ENUMS
    // =========================

    public enum VehicleType {
        TWO_WHEELER,
        CAR,
        BUS,
        TRUCK
    }

    public enum ViolationType {
        OVER_SPEEDING,
        SIGNAL_VIOLATION,
        ILLEGAL_PARKING
    }

    public enum PaymentStatus {
        UNPAID,
        PAID
    }

    public enum VehicleClassification {
        CLEAN,
        LOW_RISK,
        MEDIUM_RISK,
        HIGH_RISK
    }

    // =========================
    // CUSTOM EXCEPTIONS
    // =========================

    public static class InvalidVehicleException extends Exception {
        public InvalidVehicleException(String message) {
            super(message);
        }
    }

    public static class InvalidViolationException extends Exception {
        public InvalidViolationException(String message) {
            super(message);
        }
    }

    public static class DuplicateChallanException extends Exception {
        public DuplicateChallanException(String message) {
            super(message);
        }
    }

    public static class ChallanNotFoundException extends Exception {
        public ChallanNotFoundException(String message) {
            super(message);
        }
    }

    public static class PaymentException extends Exception {
        public PaymentException(String message) {
            super(message);
        }
    }

    // =========================
    // OWNER CLASS
    // =========================

    public static class Owner {

        private String name;
        private String phone;
        private String address;

        public Owner(String name, String phone, String address)
                throws InvalidVehicleException {

            if (name == null || name.trim().isEmpty()) {
                throw new InvalidVehicleException(
                        "Owner name cannot be empty."
                );
            }

            if (phone == null || phone.trim().isEmpty()) {
                throw new InvalidVehicleException(
                        "Owner phone cannot be empty."
                );
            }

            if (address == null || address.trim().isEmpty()) {
                throw new InvalidVehicleException(
                        "Owner address cannot be empty."
                );
            }

            this.name = name;
            this.phone = phone;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }
    }

    // =========================
    // VEHICLE CLASS
    // =========================

    public static class Vehicle {

        private String vehicleNumber;
        private Owner owner;
        private VehicleType vehicleType;

        private List<Challan> violationHistory;

        public Vehicle(String vehicleNumber,
                        Owner owner,
                        VehicleType vehicleType)
                throws InvalidVehicleException {

            if (vehicleNumber == null ||
                    vehicleNumber.trim().isEmpty()) {

                throw new InvalidVehicleException(
                        "Vehicle number cannot be empty."
                );
            }

            if (owner == null) {
                throw new InvalidVehicleException(
                        "Owner details cannot be null."
                );
            }

            if (vehicleType == null) {
                throw new InvalidVehicleException(
                        "Vehicle type cannot be null."
                );
            }

            this.vehicleNumber = vehicleNumber.toUpperCase();
            this.owner = owner;
            this.vehicleType = vehicleType;
            this.violationHistory = new ArrayList<>();
        }

        public String getVehicleNumber() {
            return vehicleNumber;
        }

        public Owner getOwner() {
            return owner;
        }

        public VehicleType getVehicleType() {
            return vehicleType;
        }

        public List<Challan> getViolationHistory() {
            return violationHistory;
        }
    }

    // =========================
    // CHALLAN CLASS
    // =========================

    public static class Challan {

        private String challanId;
        private Vehicle vehicle;
        private ViolationType violationType;
        private String location;
        private LocalDateTime timestamp;

        private double speed;
        private double permittedSpeed;

        private double fineAmount;
        private PaymentStatus paymentStatus;

        public Challan(String challanId,
                        Vehicle vehicle,
                        ViolationType violationType,
                        String location,
                        LocalDateTime timestamp,
                        double speed,
                        double permittedSpeed,
                        double fineAmount) {

            this.challanId = challanId;
            this.vehicle = vehicle;
            this.violationType = violationType;
            this.location = location;
            this.timestamp = timestamp;
            this.speed = speed;
            this.permittedSpeed = permittedSpeed;
            this.fineAmount = fineAmount;
            this.paymentStatus = PaymentStatus.UNPAID;
        }

        public String getChallanId() {
            return challanId;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public ViolationType getViolationType() {
            return violationType;
        }

        public String getLocation() {
            return location;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public double getSpeed() {
            return speed;
        }

        public double getPermittedSpeed() {
            return permittedSpeed;
        }

        public double getFineAmount() {
            return fineAmount;
        }

        public PaymentStatus getPaymentStatus() {
            return paymentStatus;
        }

        private void markPaid() {
            this.paymentStatus = PaymentStatus.PAID;
        }
    }

    // =========================
    // SYSTEM DATA
    // =========================

    private Map<String, Vehicle> vehicles;
    private Map<String, Challan> challans;

    private int challanCounter = 1000;

    // Base fines
    private static final double OVER_SPEEDING_FINE = 1000.0;
    private static final double SIGNAL_VIOLATION_FINE = 1500.0;
    private static final double ILLEGAL_PARKING_FINE = 500.0;

    public TrafficViolationSystem() {
        vehicles = new HashMap<>();
        challans = new HashMap<>();
    }

    // =========================
    // VEHICLE REGISTRATION
    // =========================

    public void registerVehicle(Vehicle vehicle)
            throws InvalidVehicleException {

        if (vehicle == null) {
            throw new InvalidVehicleException(
                    "Vehicle cannot be null."
            );
        }

        String number = vehicle.getVehicleNumber();

        if (vehicles.containsKey(number)) {
            throw new InvalidVehicleException(
                    "Vehicle is already registered."
            );
        }

        vehicles.put(number, vehicle);
    }

    // =========================
    // FIND VEHICLE
    // =========================

    public Vehicle getVehicle(String vehicleNumber) {

        if (vehicleNumber == null) {
            return null;
        }

        return vehicles.get(vehicleNumber.toUpperCase());
    }

    // =========================
    // CALCULATE FINE
    // =========================

    public double calculateFine(ViolationType violationType,
                                double speed,
                                double permittedSpeed,
                                int previousViolations)
            throws InvalidViolationException {

        if (violationType == null) {
            throw new InvalidViolationException(
                    "Violation type cannot be null."
            );
        }

        if (speed < 0 || permittedSpeed < 0) {
            throw new InvalidViolationException(
                    "Speed values cannot be negative."
            );
        }

        if (previousViolations < 0) {
            throw new InvalidViolationException(
                    "Previous violations cannot be negative."
            );
        }

        double baseFine;

        switch (violationType) {

            case OVER_SPEEDING:

                if (speed <= permittedSpeed) {
                    throw new InvalidViolationException(
                            "Speed is within the permitted limit."
                    );
                }

                double excess = speed - permittedSpeed;

                if (excess >= 30) {
                    baseFine = 2000.0;
                } else if (excess >= 15) {
                    baseFine = 1500.0;
                } else {
                    baseFine = OVER_SPEEDING_FINE;
                }

                break;

            case SIGNAL_VIOLATION:
                baseFine = SIGNAL_VIOLATION_FINE;
                break;

            case ILLEGAL_PARKING:
                baseFine = ILLEGAL_PARKING_FINE;
                break;

            default:
                throw new InvalidViolationException(
                        "Unknown violation type."
                );
        }

        // Higher penalty for repeated violations
        if (previousViolations >= 3) {
            baseFine *= 2.0;
        } else if (previousViolations >= 1) {
            baseFine *= 1.5;
        }

        return baseFine;
    }

    // =========================
    // GENERATE CHALLAN
    // =========================

    public Challan generateChallan(String vehicleNumber,
                                   ViolationType violationType,
                                   String location,
                                   LocalDateTime timestamp,
                                   double speed,
                                   double permittedSpeed)
            throws InvalidViolationException,
            ChallanNotFoundException,
            DuplicateChallanException {

        Vehicle vehicle = getVehicle(vehicleNumber);

        if (vehicle == null) {
            throw new ChallanNotFoundException(
                    "Vehicle is not registered."
            );
        }

        if (violationType == null) {
            throw new InvalidViolationException(
                    "Violation type cannot be null."
            );
        }

        if (location == null || location.trim().isEmpty()) {
            throw new InvalidViolationException(
                    "Violation location cannot be empty."
            );
        }

        if (timestamp == null) {
            throw new InvalidViolationException(
                    "Timestamp cannot be null."
            );
        }

        if (speed < 0 || permittedSpeed < 0) {
            throw new InvalidViolationException(
                    "Speed values cannot be negative."
            );
        }

        // Prevent duplicate challan for same violation event
        for (Challan existing : vehicle.getViolationHistory()) {

            boolean sameType =
                    existing.getViolationType() == violationType;

            boolean sameLocation =
                    existing.getLocation().equalsIgnoreCase(location);

            boolean sameTime =
                    existing.getTimestamp().equals(timestamp);

            if (sameType && sameLocation && sameTime) {
                throw new DuplicateChallanException(
                        "Duplicate challan for the same violation event."
                );
            }
        }

        int previousViolations =
                vehicle.getViolationHistory().size();

        double fine = calculateFine(
                violationType,
                speed,
                permittedSpeed,
                previousViolations
        );

        String challanId =
                "CH-" + (++challanCounter);

        Challan challan = new Challan(
                challanId,
                vehicle,
                violationType,
                location,
                timestamp,
                speed,
                permittedSpeed,
                fine
        );

        challans.put(challanId, challan);

        vehicle.getViolationHistory().add(challan);

        return challan;
    }

    // =========================
    // PAY CHALLAN
    // =========================

    public void payChallan(String challanId)
            throws ChallanNotFoundException, PaymentException {

        if (challanId == null ||
                challanId.trim().isEmpty()) {

            throw new PaymentException(
                    "Challan ID cannot be empty."
            );
        }

        Challan challan = challans.get(challanId);

        if (challan == null) {
            throw new ChallanNotFoundException(
                    "Challan not found."
            );
        }

        if (challan.getPaymentStatus() == PaymentStatus.PAID) {
            throw new PaymentException(
                    "Challan has already been paid."
            );
        }

        challan.markPaid();
    }

    // =========================
    // OUTSTANDING FINE
    // =========================

    public double getOutstandingFine(String vehicleNumber)
            throws ChallanNotFoundException {

        Vehicle vehicle = getVehicle(vehicleNumber);

        if (vehicle == null) {
            throw new ChallanNotFoundException(
                    "Vehicle is not registered."
            );
        }

        double total = 0;

        for (Challan challan :
                vehicle.getViolationHistory()) {

            if (challan.getPaymentStatus()
                    == PaymentStatus.UNPAID) {

                total += challan.getFineAmount();
            }
        }

        return total;
    }

    // =========================
    // VEHICLE CLASSIFICATION
    // =========================

    public VehicleClassification classifyVehicle(
            String vehicleNumber)
            throws ChallanNotFoundException {

        Vehicle vehicle = getVehicle(vehicleNumber);

        if (vehicle == null) {
            throw new ChallanNotFoundException(
                    "Vehicle is not registered."
            );
        }

        int count = vehicle.getViolationHistory().size();

        if (count == 0) {
            return VehicleClassification.CLEAN;
        }

        if (count <= 2) {
            return VehicleClassification.LOW_RISK;
        }

        if (count <= 4) {
            return VehicleClassification.MEDIUM_RISK;
        }

        return VehicleClassification.HIGH_RISK;
    }

    // =========================
    // HISTORY
    // =========================

    public List<Challan> getViolationHistory(
            String vehicleNumber)
            throws ChallanNotFoundException {

        Vehicle vehicle = getVehicle(vehicleNumber);

        if (vehicle == null) {
            throw new ChallanNotFoundException(
                    "Vehicle is not registered."
            );
        }

        return vehicle.getViolationHistory();
    }

    // =========================
    // GET CHALLAN
    // =========================

    public Challan getChallan(String challanId) {

        return challans.get(challanId);
    }

    // =========================
    // COUNTS
    // =========================

    public int getVehicleCount() {
        return vehicles.size();
    }

    public int getChallanCount() {
        return challans.size();
    }

    // =========================
    // DISPLAY CHALLAN
    // =========================

    public void displayChallan(Challan challan) {

        if (challan == null) {
            return;
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy HH:mm:ss"
                );

        System.out.println();
        System.out.println(
                "========================================"
        );
        System.out.println("          ELECTRONIC E-CHALLAN");
        System.out.println(
                "========================================"
        );

        System.out.println(
                "Challan ID       : " +
                        challan.getChallanId()
        );

        System.out.println(
                "Vehicle Number   : " +
                        challan.getVehicle()
                                .getVehicleNumber()
        );

        System.out.println(
                "Owner Name       : " +
                        challan.getVehicle()
                                .getOwner()
                                .getName()
        );

        System.out.println(
                "Vehicle Type     : " +
                        challan.getVehicle()
                                .getVehicleType()
        );

        System.out.println(
                "Violation        : " +
                        challan.getViolationType()
        );

        System.out.println(
                "Location         : " +
                        challan.getLocation()
        );

        System.out.println(
                "Timestamp        : " +
                        challan.getTimestamp()
                                .format(formatter)
        );

        System.out.println(
                "Speed            : " +
                        challan.getSpeed() + " km/h"
        );

        System.out.println(
                "Permitted Speed  : " +
                        challan.getPermittedSpeed() +
                        " km/h"
        );

        System.out.println(
                "Fine Amount      : Rs. " +
                        challan.getFineAmount()
        );

        System.out.println(
                "Payment Status   : " +
                        challan.getPaymentStatus()
        );

        System.out.println(
                "========================================"
        );
    }

    // =========================
    // MAIN DEMO
    // =========================

    public static void main(String[] args) {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            Owner owner = new Owner(
                    "Rahul Sharma",
                    "9876543210",
                    "Chennai"
            );

            Vehicle vehicle = new Vehicle(
                    "TN01AB1234",
                    owner,
                    VehicleType.CAR
            );

            system.registerVehicle(vehicle);

            Challan challan =
                    system.generateChallan(
                            "TN01AB1234",
                            ViolationType.OVER_SPEEDING,
                            "Chennai Main Road",
                            LocalDateTime.now(),
                            90,
                            60
                    );

            system.displayChallan(challan);

            System.out.println(
                    "\nOutstanding Fine: Rs. " +
                            system.getOutstandingFine(
                                    "TN01AB1234"
                            )
            );

            system.payChallan(
                    challan.getChallanId()
            );

            System.out.println(
                    "Payment successful."
            );

            System.out.println(
                    "Outstanding Fine after payment: Rs. " +
                            system.getOutstandingFine(
                                    "TN01AB1234"
                            )
            );

            System.out.println(
                    "Vehicle Classification: " +
                            system.classifyVehicle(
                                    "TN01AB1234"
                            )
            );

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }
}
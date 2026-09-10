import java.time.LocalDateTime;

public class TrafficViolationSystemTest {

    private static int passed = 0;
    private static int failed = 0;

    // =========================
    // TEST HELPER
    // =========================

    private static void check(
            boolean condition,
            String testName) {

        if (condition) {
            System.out.println(
                    testName + " : PASSED"
            );
            passed++;
        } else {
            System.out.println(
                    testName + " : FAILED"
            );
            failed++;
        }
    }

    // =========================
    // CREATE SYSTEM
    // =========================

    private static TrafficViolationSystem createSystem()
            throws Exception {

        TrafficViolationSystem system =
                new TrafficViolationSystem();

        TrafficViolationSystem.Owner owner =
                new TrafficViolationSystem.Owner(
                        "Test Owner",
                        "9876543210",
                        "Chennai"
                );

        TrafficViolationSystem.Vehicle vehicle =
                new TrafficViolationSystem.Vehicle(
                        "TN01AB1234",
                        owner,
                        TrafficViolationSystem.VehicleType.CAR
                );

        system.registerVehicle(vehicle);

        return system;
    }

    // =========================
    // TEST 1
    // =========================

    private static void testSystemCreation() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            check(
                    system != null,
                    "Test 1 - System creation"
            );

        } catch (Exception e) {

            check(false,
                    "Test 1 - System creation");
        }
    }

    // =========================
    // TEST 2
    // =========================

    private static void testVehicleRegistration() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            check(
                    system.getVehicleCount() == 1,
                    "Test 2 - Vehicle registration"
            );

        } catch (Exception e) {

            check(false,
                    "Test 2 - Vehicle registration");
        }
    }

    // =========================
    // TEST 3
    // =========================

    private static void testMultipleVehicles() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            for (int i = 1; i <= 3; i++) {

                TrafficViolationSystem.Owner owner =
                        new TrafficViolationSystem.Owner(
                                "Owner " + i,
                                "987654321" + i,
                                "Chennai"
                        );

                TrafficViolationSystem.Vehicle vehicle =
                        new TrafficViolationSystem.Vehicle(
                                "TN01AB100" + i,
                                owner,
                                TrafficViolationSystem.VehicleType.CAR
                        );

                system.registerVehicle(vehicle);
            }

            check(
                    system.getVehicleCount() == 3,
                    "Test 3 - Multiple vehicle registration"
            );

        } catch (Exception e) {

            check(false,
                    "Test 3 - Multiple vehicle registration");
        }
    }

    // =========================
    // TEST 4
    // =========================

    private static void testTwoWheeler() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            TrafficViolationSystem.Owner owner =
                    new TrafficViolationSystem.Owner(
                            "Bike Owner",
                            "9876543210",
                            "Chennai"
                    );

            TrafficViolationSystem.Vehicle vehicle =
                    new TrafficViolationSystem.Vehicle(
                            "TN02XY1234",
                            owner,
                            TrafficViolationSystem.VehicleType.TWO_WHEELER
                    );

            system.registerVehicle(vehicle);

            check(
                    system.getVehicle(
                            "TN02XY1234"
                    ).getVehicleType()
                            == TrafficViolationSystem.VehicleType.TWO_WHEELER,
                    "Test 4 - Two wheeler registration"
            );

        } catch (Exception e) {

            check(false,
                    "Test 4 - Two wheeler registration");
        }
    }

    // =========================
    // TEST 5
    // =========================

    private static void testTruckRegistration() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            TrafficViolationSystem.Owner owner =
                    new TrafficViolationSystem.Owner(
                            "Truck Owner",
                            "9876543210",
                            "Chennai"
                    );

            TrafficViolationSystem.Vehicle vehicle =
                    new TrafficViolationSystem.Vehicle(
                            "TN03TR1234",
                            owner,
                            TrafficViolationSystem.VehicleType.TRUCK
                    );

            system.registerVehicle(vehicle);

            check(
                    system.getVehicleCount() == 1,
                    "Test 5 - Truck registration"
            );

        } catch (Exception e) {

            check(false,
                    "Test 5 - Truck registration");
        }
    }

    // =========================
    // TEST 6
    // =========================

    private static void testEmptyVehicleNumber() {

        try {

            TrafficViolationSystem.Owner owner =
                    new TrafficViolationSystem.Owner(
                            "Owner",
                            "9876543210",
                            "Chennai"
                    );

            new TrafficViolationSystem.Vehicle(
                    "",
                    owner,
                    TrafficViolationSystem.VehicleType.CAR
            );

            check(false,
                    "Test 6 - Empty vehicle number");

        } catch (TrafficViolationSystem.InvalidVehicleException e) {

            check(true,
                    "Test 6 - Empty vehicle number");

        } catch (Exception e) {

            check(false,
                    "Test 6 - Empty vehicle number");
        }
    }

    // =========================
    // TEST 7
    // =========================

    private static void testNullVehicleNumber() {

        try {

            TrafficViolationSystem.Owner owner =
                    new TrafficViolationSystem.Owner(
                            "Owner",
                            "9876543210",
                            "Chennai"
                    );

            new TrafficViolationSystem.Vehicle(
                    null,
                    owner,
                    TrafficViolationSystem.VehicleType.CAR
            );

            check(false,
                    "Test 7 - Null vehicle number");

        } catch (TrafficViolationSystem.InvalidVehicleException e) {

            check(true,
                    "Test 7 - Null vehicle number");

        } catch (Exception e) {

            check(false,
                    "Test 7 - Null vehicle number");
        }
    }

    // =========================
    // TEST 8
    // =========================

    private static void testNullOwner() {

        try {

            new TrafficViolationSystem.Vehicle(
                    "TN01AB1234",
                    null,
                    TrafficViolationSystem.VehicleType.CAR
            );

            check(false,
                    "Test 8 - Null owner");

        } catch (TrafficViolationSystem.InvalidVehicleException e) {

            check(true,
                    "Test 8 - Null owner");

        } catch (Exception e) {

            check(false,
                    "Test 8 - Null owner");
        }
    }

    // =========================
    // TEST 9
    // =========================

    private static void testNullVehicleType() {

        try {

            TrafficViolationSystem.Owner owner =
                    new TrafficViolationSystem.Owner(
                            "Owner",
                            "9876543210",
                            "Chennai"
                    );

            new TrafficViolationSystem.Vehicle(
                    "TN01AB1234",
                    owner,
                    null
            );

            check(false,
                    "Test 9 - Null vehicle type");

        } catch (TrafficViolationSystem.InvalidVehicleException e) {

            check(true,
                    "Test 9 - Null vehicle type");

        } catch (Exception e) {

            check(false,
                    "Test 9 - Null vehicle type");
        }
    }

    // =========================
    // TEST 10
    // =========================

    private static void testDuplicateVehicle() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            TrafficViolationSystem.Owner owner =
                    new TrafficViolationSystem.Owner(
                            "Another Owner",
                            "9876543211",
                            "Chennai"
                    );

            TrafficViolationSystem.Vehicle vehicle =
                    new TrafficViolationSystem.Vehicle(
                            "TN01AB1234",
                            owner,
                            TrafficViolationSystem.VehicleType.CAR
                    );

            system.registerVehicle(vehicle);

            check(false,
                    "Test 10 - Duplicate vehicle");

        } catch (TrafficViolationSystem.InvalidVehicleException e) {

            check(true,
                    "Test 10 - Duplicate vehicle");

        } catch (Exception e) {

            check(false,
                    "Test 10 - Duplicate vehicle");
        }
    }

    // =========================
    // TEST 11
    // =========================

    private static void testOwnerNameValidation() {

        try {

            new TrafficViolationSystem.Owner(
                    "",
                    "9876543210",
                    "Chennai"
            );

            check(false,
                    "Test 11 - Empty owner name");

        } catch (TrafficViolationSystem.InvalidVehicleException e) {

            check(true,
                    "Test 11 - Empty owner name");

        } catch (Exception e) {

            check(false,
                    "Test 11 - Empty owner name");
        }
    }

    // =========================
    // TEST 12
    // =========================

    private static void testOwnerPhoneValidation() {

        try {

            new TrafficViolationSystem.Owner(
                    "Owner",
                    "",
                    "Chennai"
            );

            check(false,
                    "Test 12 - Empty owner phone");

        } catch (TrafficViolationSystem.InvalidVehicleException e) {

            check(true,
                    "Test 12 - Empty owner phone");

        } catch (Exception e) {

            check(false,
                    "Test 12 - Empty owner phone");
        }
    }

    // =========================
    // TEST 13
    // =========================

    private static void testOverSpeedingFine() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            double fine =
                    system.calculateFine(
                            TrafficViolationSystem.ViolationType.OVER_SPEEDING,
                            70,
                            60,
                            0
                    );

            check(
                    fine == 1000.0,
                    "Test 13 - Basic over-speeding fine"
            );

        } catch (Exception e) {

            check(false,
                    "Test 13 - Basic over-speeding fine");
        }
    }

    // =========================
    // TEST 14
    // =========================

    private static void testModerateOverSpeedingFine() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            double fine =
                    system.calculateFine(
                            TrafficViolationSystem.ViolationType.OVER_SPEEDING,
                            80,
                            60,
                            0
                    );

            check(
                    fine == 1500.0,
                    "Test 14 - Moderate over-speeding fine"
            );

        } catch (Exception e) {

            check(false,
                    "Test 14 - Moderate over-speeding fine");
        }
    }

    // =========================
    // TEST 15
    // =========================

    private static void testSevereOverSpeedingFine() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            double fine =
                    system.calculateFine(
                            TrafficViolationSystem.ViolationType.OVER_SPEEDING,
                            95,
                            60,
                            0
                    );

            check(
                    fine == 2000.0,
                    "Test 15 - Severe over-speeding fine"
            );

        } catch (Exception e) {

            check(false,
                    "Test 15 - Severe over-speeding fine");
        }
    }

    // =========================
    // TEST 16
    // =========================

    private static void testSignalViolationFine() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            double fine =
                    system.calculateFine(
                            TrafficViolationSystem.ViolationType.SIGNAL_VIOLATION,
                            0,
                            0,
                            0
                    );

            check(
                    fine == 1500.0,
                    "Test 16 - Signal violation fine"
            );

        } catch (Exception e) {

            check(false,
                    "Test 16 - Signal violation fine");
        }
    }

    // =========================
    // TEST 17
    // =========================

    private static void testIllegalParkingFine() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            double fine =
                    system.calculateFine(
                            TrafficViolationSystem.ViolationType.ILLEGAL_PARKING,
                            0,
                            0,
                            0
                    );

            check(
                    fine == 500.0,
                    "Test 17 - Illegal parking fine"
            );

        } catch (Exception e) {

            check(false,
                    "Test 17 - Illegal parking fine");
        }
    }

    // =========================
    // TEST 18
    // =========================

    private static void testSpeedBoundary() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            system.calculateFine(
                    TrafficViolationSystem.ViolationType.OVER_SPEEDING,
                    60,
                    60,
                    0
            );

            check(false,
                    "Test 18 - Speed boundary");

        } catch (TrafficViolationSystem.InvalidViolationException e) {

            check(true,
                    "Test 18 - Speed boundary");

        } catch (Exception e) {

            check(false,
                    "Test 18 - Speed boundary");
        }
    }

    // =========================
    // TEST 19
    // =========================

    private static void testNegativeSpeed() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            system.calculateFine(
                    TrafficViolationSystem.ViolationType.OVER_SPEEDING,
                    -10,
                    60,
                    0
            );

            check(false,
                    "Test 19 - Negative speed");

        } catch (TrafficViolationSystem.InvalidViolationException e) {

            check(true,
                    "Test 19 - Negative speed");

        } catch (Exception e) {

            check(false,
                    "Test 19 - Negative speed");
        }
    }

    // =========================
    // TEST 20
    // =========================

    private static void testNegativePermittedSpeed() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            system.calculateFine(
                    TrafficViolationSystem.ViolationType.OVER_SPEEDING,
                    70,
                    -60,
                    0
            );

            check(false,
                    "Test 20 - Negative permitted speed");

        } catch (TrafficViolationSystem.InvalidViolationException e) {

            check(true,
                    "Test 20 - Negative permitted speed");

        } catch (Exception e) {

            check(false,
                    "Test 20 - Negative permitted speed");
        }
    }

    // =========================
    // TEST 21
    // =========================

    private static void testRepeatedViolationPenalty() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            double first =
                    system.calculateFine(
                            TrafficViolationSystem.ViolationType.ILLEGAL_PARKING,
                            0,
                            0,
                            0
                    );

            double repeated =
                    system.calculateFine(
                            TrafficViolationSystem.ViolationType.ILLEGAL_PARKING,
                            0,
                            0,
                            1
                    );

            check(
                    repeated > first,
                    "Test 21 - Repeated violation penalty"
            );

        } catch (Exception e) {

            check(false,
                    "Test 21 - Repeated violation penalty");
        }
    }

    // =========================
    // TEST 22
    // =========================

    private static void testHeavyRepeatedPenalty() {

        try {

            TrafficViolationSystem system =
                    new TrafficViolationSystem();

            double fine =
                    system.calculateFine(
                            TrafficViolationSystem.ViolationType.ILLEGAL_PARKING,
                            0,
                            0,
                            3
                    );

            check(
                    fine == 1000.0,
                    "Test 22 - Heavy repeated penalty"
            );

        } catch (Exception e) {

            check(false,
                    "Test 22 - Heavy repeated penalty");
        }
    }

    // =========================
    // TEST 23
    // =========================

    private static void testGenerateChallan() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            ChallanHolder holder =
                    createChallan(system);

            check(
                    holder.challan != null,
                    "Test 23 - Generate e-challan"
            );

        } catch (Exception e) {

            check(false,
                    "Test 23 - Generate e-challan");
        }
    }

    // =========================
    // TEST 24
    // =========================

    private static void testChallanId() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            ChallanHolder holder =
                    createChallan(system);

            check(
                    holder.challan.getChallanId()
                            .startsWith("CH-"),
                    "Test 24 - Challan ID generation"
            );

        } catch (Exception e) {

            check(false,
                    "Test 24 - Challan ID generation");
        }
    }

    // =========================
    // TEST 25
    // =========================

    private static void testUnpaidStatus() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            ChallanHolder holder =
                    createChallan(system);

            check(
                    holder.challan.getPaymentStatus()
                            == TrafficViolationSystem.PaymentStatus.UNPAID,
                    "Test 25 - Initial unpaid status"
            );

        } catch (Exception e) {

            check(false,
                    "Test 25 - Initial unpaid status");
        }
    }

    // =========================
    // TEST 26
    // =========================

    private static void testOutstandingFine() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            ChallanHolder holder =
                    createChallan(system);

            double outstanding =
                    system.getOutstandingFine(
                            "TN01AB1234"
                    );

            check(
                    outstanding ==
                            holder.challan.getFineAmount(),
                    "Test 26 - Outstanding fine calculation"
            );

        } catch (Exception e) {

            check(false,
                    "Test 26 - Outstanding fine calculation");
        }
    }

    // =========================
    // TEST 27
    // =========================

    private static void testPayment() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            ChallanHolder holder =
                    createChallan(system);

            system.payChallan(
                    holder.challan.getChallanId()
            );

            check(
                    holder.challan.getPaymentStatus()
                            == TrafficViolationSystem.PaymentStatus.PAID,
                    "Test 27 - Challan payment"
            );

        } catch (Exception e) {

            check(false,
                    "Test 27 - Challan payment");
        }
    }

    // =========================
    // TEST 28
    // =========================

    private static void testOutstandingAfterPayment() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            ChallanHolder holder =
                    createChallan(system);

            system.payChallan(
                    holder.challan.getChallanId()
            );

            check(
                    system.getOutstandingFine(
                            "TN01AB1234"
                    ) == 0,
                    "Test 28 - Outstanding fine after payment"
            );

        } catch (Exception e) {

            check(false,
                    "Test 28 - Outstanding fine after payment");
        }
    }

    // =========================
    // TEST 29
    // =========================

    private static void testDuplicateChallan() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            LocalDateTime time =
                    LocalDateTime.of(
                            2026, 9, 10,
                            10, 0
                    );

            system.generateChallan(
                    "TN01AB1234",
                    TrafficViolationSystem.ViolationType.SIGNAL_VIOLATION,
                    "Chennai",
                    time,
                    0,
                    0
            );

            system.generateChallan(
                    "TN01AB1234",
                    TrafficViolationSystem.ViolationType.SIGNAL_VIOLATION,
                    "Chennai",
                    time,
                    0,
                    0
            );

            check(false,
                    "Test 29 - Duplicate challan prevention");

        } catch (TrafficViolationSystem.DuplicateChallanException e) {

            check(true,
                    "Test 29 - Duplicate challan prevention");

        } catch (Exception e) {

            check(false,
                    "Test 29 - Duplicate challan prevention");
        }
    }

    // =========================
    // TEST 30
    // =========================

    private static void testDifferentEventsAllowed() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            LocalDateTime time1 =
                    LocalDateTime.of(
                            2026, 9, 10,
                            10, 0
                    );

            LocalDateTime time2 =
                    LocalDateTime.of(
                            2026, 9, 10,
                            11, 0
                    );

            system.generateChallan(
                    "TN01AB1234",
                    TrafficViolationSystem.ViolationType.SIGNAL_VIOLATION,
                    "Chennai",
                    time1,
                    0,
                    0
            );

            system.generateChallan(
                    "TN01AB1234",
                    TrafficViolationSystem.ViolationType.SIGNAL_VIOLATION,
                    "Chennai",
                    time2,
                    0,
                    0
            );

            check(
                    system.getChallanCount() == 2,
                    "Test 30 - Different events allowed"
            );

        } catch (Exception e) {

            check(false,
                    "Test 30 - Different events allowed");
        }
    }

    // =========================
    // TEST 31
    // =========================

    private static void testViolationHistory() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            createChallan(system);

            check(
                    system.getViolationHistory(
                            "TN01AB1234"
                    ).size() == 1,
                    "Test 31 - Violation history"
            );

        } catch (Exception e) {

            check(false,
                    "Test 31 - Violation history");
        }
    }

    // =========================
    // TEST 32
    // =========================

    private static void testCleanVehicleClassification() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            check(
                    system.classifyVehicle(
                            "TN01AB1234"
                    ) == TrafficViolationSystem.VehicleClassification.CLEAN,
                    "Test 32 - Clean vehicle classification"
            );

        } catch (Exception e) {

            check(false,
                    "Test 32 - Clean vehicle classification");
        }
    }

    // =========================
    // TEST 33
    // =========================

    private static void testLowRiskClassification() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            createChallan(system);
            createSecondChallan(system);

            check(
                    system.classifyVehicle(
                            "TN01AB1234"
                    ) == TrafficViolationSystem.VehicleClassification.LOW_RISK,
                    "Test 33 - Low risk classification"
            );

        } catch (Exception e) {

            check(false,
                    "Test 33 - Low risk classification");
        }
    }

    // =========================
    // TEST 34
    // =========================

    private static void testMediumRiskClassification() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            for (int i = 0; i < 3; i++) {
                createUniqueChallan(system, i);
            }

            check(
                    system.classifyVehicle(
                            "TN01AB1234"
                    ) == TrafficViolationSystem.VehicleClassification.MEDIUM_RISK,
                    "Test 34 - Medium risk classification"
            );

        } catch (Exception e) {

            check(false,
                    "Test 34 - Medium risk classification");
        }
    }

    // =========================
    // TEST 35
    // =========================

    private static void testHighRiskClassification() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            for (int i = 0; i < 5; i++) {
                createUniqueChallan(system, i);
            }

            check(
                    system.classifyVehicle(
                            "TN01AB1234"
                    ) == TrafficViolationSystem.VehicleClassification.HIGH_RISK,
                    "Test 35 - High risk classification"
            );

        } catch (Exception e) {

            check(false,
                    "Test 35 - High risk classification");
        }
    }

    // =========================
    // TEST 36
    // =========================

    private static void testUnknownVehicle() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            system.getOutstandingFine(
                    "UNKNOWN"
            );

            check(false,
                    "Test 36 - Unknown vehicle");

        } catch (TrafficViolationSystem.ChallanNotFoundException e) {

            check(true,
                    "Test 36 - Unknown vehicle");

        } catch (Exception e) {

            check(false,
                    "Test 36 - Unknown vehicle");
        }
    }

    // =========================
    // TEST 37
    // =========================

    private static void testUnknownChallanPayment() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            system.payChallan("CH-9999");

            check(false,
                    "Test 37 - Unknown challan payment");

        } catch (TrafficViolationSystem.ChallanNotFoundException e) {

            check(true,
                    "Test 37 - Unknown challan payment");

        } catch (Exception e) {

            check(false,
                    "Test 37 - Unknown challan payment");
        }
    }

    // =========================
    // TEST 38
    // =========================

    private static void testDoublePayment() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            ChallanHolder holder =
                    createChallan(system);

            system.payChallan(
                    holder.challan.getChallanId()
            );

            system.payChallan(
                    holder.challan.getChallanId()
            );

            check(false,
                    "Test 38 - Double payment prevention");

        } catch (TrafficViolationSystem.PaymentException e) {

            check(true,
                    "Test 38 - Double payment prevention");

        } catch (Exception e) {

            check(false,
                    "Test 38 - Double payment prevention");
        }
    }

    // =========================
    // TEST 39
    // =========================

    private static void testNullViolationType() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            system.generateChallan(
                    "TN01AB1234",
                    null,
                    "Chennai",
                    LocalDateTime.now(),
                    0,
                    0
            );

            check(false,
                    "Test 39 - Null violation type");

        } catch (TrafficViolationSystem.InvalidViolationException e) {

            check(true,
                    "Test 39 - Null violation type");

        } catch (Exception e) {

            check(false,
                    "Test 39 - Null violation type");
        }
    }

    // =========================
    // TEST 40
    // =========================

    private static void testEmptyLocation() {

        try {

            TrafficViolationSystem system =
                    createSystem();

            system.generateChallan(
                    "TN01AB1234",
                    TrafficViolationSystem.ViolationType.ILLEGAL_PARKING,
                    "",
                    LocalDateTime.now(),
                    0,
                    0
            );

            check(false,
                    "Test 40 - Empty violation location");

        } catch (TrafficViolationSystem.InvalidViolationException e) {

            check(true,
                    "Test 40 - Empty violation location");

        } catch (Exception e) {

            check(false,
                    "Test 40 - Empty violation location");
        }
    }

    // =========================
    // HELPER CLASS
    // =========================

    private static class ChallanHolder {

        TrafficViolationSystem.Challan challan;

        ChallanHolder(
                TrafficViolationSystem.Challan challan) {

            this.challan = challan;
        }
    }

    // =========================
    // CREATE CHALLAN
    // =========================

    private static ChallanHolder createChallan(
            TrafficViolationSystem system)
            throws Exception {

        LocalDateTime time =
                LocalDateTime.of(
                        2026, 9, 10,
                        10, 0
                );

        TrafficViolationSystem.Challan challan =
                system.generateChallan(
                        "TN01AB1234",
                        TrafficViolationSystem.ViolationType.OVER_SPEEDING,
                        "Chennai Main Road",
                        time,
                        90,
                        60
                );

        return new ChallanHolder(challan);
    }

    private static void createSecondChallan(
            TrafficViolationSystem system)
            throws Exception {

        system.generateChallan(
                "TN01AB1234",
                TrafficViolationSystem.ViolationType.SIGNAL_VIOLATION,
                "Chennai Signal",
                LocalDateTime.of(
                        2026, 9, 10,
                        11, 0
                ),
                0,
                0
        );
    }

    private static void createUniqueChallan(
            TrafficViolationSystem system,
            int index)
            throws Exception {

        system.generateChallan(
                "TN01AB1234",
                TrafficViolationSystem.ViolationType.ILLEGAL_PARKING,
                "Location " + index,
                LocalDateTime.of(
                        2026,
                        9,
                        10,
                        12,
                        index
                ),
                0,
                0
        );
    }

    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {

        System.out.println(
                "========================================"
        );

        System.out.println(
                "TRAFFIC VIOLATION SYSTEM TESTS"
        );

        System.out.println(
                "========================================"
        );

        testSystemCreation();
        testVehicleRegistration();
        testMultipleVehicles();
        testTwoWheeler();
        testTruckRegistration();

        testEmptyVehicleNumber();
        testNullVehicleNumber();
        testNullOwner();
        testNullVehicleType();
        testDuplicateVehicle();

        testOwnerNameValidation();
        testOwnerPhoneValidation();

        testOverSpeedingFine();
        testModerateOverSpeedingFine();
        testSevereOverSpeedingFine();
        testSignalViolationFine();
        testIllegalParkingFine();

        testSpeedBoundary();
        testNegativeSpeed();
        testNegativePermittedSpeed();

        testRepeatedViolationPenalty();
        testHeavyRepeatedPenalty();

        testGenerateChallan();
        testChallanId();
        testUnpaidStatus();
        testOutstandingFine();
        testPayment();
        testOutstandingAfterPayment();

        testDuplicateChallan();
        testDifferentEventsAllowed();

        testViolationHistory();

        testCleanVehicleClassification();
        testLowRiskClassification();
        testMediumRiskClassification();
        testHighRiskClassification();

        testUnknownVehicle();
        testUnknownChallanPayment();
        testDoublePayment();

        testNullViolationType();
        testEmptyLocation();

        System.out.println();
        System.out.println(
                "========================================"
        );
        System.out.println(
                "Tests Passed : " + passed
        );
        System.out.println(
                "Tests Failed : " + failed
        );
        System.out.println(
                "Total Tests  : " + (passed + failed)
        );
        System.out.println(
                "========================================"
        );

        if (failed > 0) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }
}
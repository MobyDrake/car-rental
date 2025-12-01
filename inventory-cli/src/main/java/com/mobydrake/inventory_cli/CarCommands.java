package com.mobydrake.inventory_cli;

import inventory.model.CarResponse;
import inventory.model.InsertCarRequest;
import inventory.model.InventoryServiceGrpc;
import inventory.model.RemoveCarRequest;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command
public class CarCommands {

    private static final String REMOVED_CAR_WITH_PARAMETERS = "Removed car with parameters [%s]";
    private static final String CREATED_CAR_WITH_PARAMETERS = "Created car with parameters [%s]";

    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceBlockingStub;

    public CarCommands(InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceBlockingStub) {
        this.inventoryServiceBlockingStub = inventoryServiceBlockingStub;
    }

    @Command(description = "This command will create new Car.")
    public String addCar(@Option(required = true, longNames = "licensePlateNumber", shortNames = 'n') String licensePlateNumber,
                         @Option(required = true, longNames = "manufacturer") String manufacturer,
                         @Option(required = true) String model) {
        InsertCarRequest carRequest = InsertCarRequest.newBuilder()
                .setLicensePlateNumber(licensePlateNumber)
                .setManufacturer(manufacturer)
                .setModel(model)
                .build();
        CarResponse carResponse = inventoryServiceBlockingStub.addOne(carRequest);
        return CREATED_CAR_WITH_PARAMETERS.formatted(carResponse);
    }

    @Command(description = "This command will delete Car from inventory by licensePlateNumber.")
    public String removeCar(@Option(required = true, longNames = "licensePlateNumber", shortNames = 'n') String licensePlateNumber) {
        CarResponse remove = inventoryServiceBlockingStub.remove(RemoveCarRequest.newBuilder().setLicensePlateNumber(licensePlateNumber).build());
        return REMOVED_CAR_WITH_PARAMETERS.formatted(remove);
    }
}

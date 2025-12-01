package com.mobydrake.inventory_cli;

import inventory.model.InventoryServiceGrpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.grpc.client.ImportGrpcClients;
import org.springframework.shell.command.annotation.CommandScan;

@CommandScan
@ImportGrpcClients(target = "stub", types = InventoryServiceGrpc.InventoryServiceBlockingStub.class)
@SpringBootApplication
public class InventoryCliApplication {

	static void main(String[] args) {
		SpringApplication.run(InventoryCliApplication.class, args);
	}

}

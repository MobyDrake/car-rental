package com.mobydrake.inventory.service;


import com.google.protobuf.Empty;
import com.mobydrake.inventory.entity.Car;
import com.mobydrake.inventory.mapper.CarMapper;
import com.mobydrake.inventory.repository.CarRepository;
import inventory.model.CarResponse;
import inventory.model.InsertCarRequest;
import inventory.model.InventoryServiceGrpc;
import inventory.model.RemoveCarRequest;
import inventory.model.StreamCarResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;

import java.util.NoSuchElementException;
import java.util.Optional;

@GrpcService
@RequiredArgsConstructor
public class InventoryService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final CarMapper carMapper;
    private final CarRepository carRepository;

    @Override
    public StreamObserver<InsertCarRequest> add(StreamObserver<StreamCarResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(InsertCarRequest insertCarRequest) {
                Car car = carMapper.toSource(insertCarRequest);
                carRepository.save(car);
                //todo: implement error in streamObserver - add message with type Status
                StreamCarResponse carResponse = StreamCarResponse.newBuilder().setCar(carMapper.toResponse(car)).build();
                responseObserver.onNext(carResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Was error in stream: {}", throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("onCompleted call");
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void addOne(InsertCarRequest request, StreamObserver<CarResponse> responseObserver) {
        Car car = carMapper.toSource(request);
        carRepository.save(car);
        responseObserver.onNext(carMapper.toResponse(car));
        responseObserver.onCompleted();
    }

    @Override
    public void remove(RemoveCarRequest request, StreamObserver<Empty> responseObserver) {
        Optional<Car> carOptional = carRepository.findByLicensePlateNumber(request.getLicensePlateNumber());
        if (carOptional.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("Car with id not found").asRuntimeException());
        } else {
            carRepository.delete(carOptional.get());
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        }
    }
}

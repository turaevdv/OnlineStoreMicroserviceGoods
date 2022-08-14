package ru.turaev.goods.restconsumer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.turaev.goods.restconsumer.StorehouseRestConsumer;

@Service
@RequiredArgsConstructor
public class StorehouseRestConsumerImpl implements StorehouseRestConsumer {
    private final WebClient.Builder webClientBuilder;

    @Value("${microservice.storehouse.name}")
    private String storehouseServiceName;

    @Override
    public boolean isStorehouseExist(long id) {
        String path = "http://" + storehouseServiceName +"/api/v1/storehouses/exist/";
        return webClientBuilder.build()
                .get()
                .uri(path + id)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}

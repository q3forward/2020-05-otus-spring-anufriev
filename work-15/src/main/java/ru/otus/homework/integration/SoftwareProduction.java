package ru.otus.homework.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework.domain.Requirement;
import ru.otus.homework.domain.Product;

import java.util.Collection;

@MessagingGateway
public interface SoftwareProduction {

    @Gateway(requestChannel = "requirementChannel", replyChannel = "productChannel")
    Product produce(Collection<Requirement> requirements);
}

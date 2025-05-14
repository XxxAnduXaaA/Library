package com.example.Library.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class FindHelper {

    private <T> T findOrCreateEntity(JpaRepository<T, Long> repository, T entity, Function<String, Optional<T>> findFunction){
        return findFunction.apply(entity.toString()).orElseGet(() -> repository.save(entity));
    }

}

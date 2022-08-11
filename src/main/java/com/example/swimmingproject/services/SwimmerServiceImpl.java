package com.example.swimmingproject.services;

import com.example.swimmingproject.model.NotFoundException;
import com.example.swimmingproject.model.Swimmer;
import com.example.swimmingproject.repositories.SwimmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SwimmerServiceImpl implements SwimmerService{

    private final SwimmerRepository swimmerRepository;

    @Override
    public List<Swimmer> findAll() {
        return swimmerRepository.findAll();
    }

    @Override
    public Swimmer findById(Long id) {
        return swimmerRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}

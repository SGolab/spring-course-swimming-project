package com.example.sgswimming.services;

import com.example.sgswimming.model.NotFoundException;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.repositories.SwimmerRepository;
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

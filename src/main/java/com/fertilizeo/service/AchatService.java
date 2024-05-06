package com.fertilizeo.service;

import com.fertilizeo.entity.Achat;
import com.fertilizeo.repository.AchatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchatService {

    @Autowired
    private AchatRepository achatRepository;

    public Achat save(Achat achat) {
        return achatRepository.save(achat);
    }

    public List<Achat> findAll() {
        return achatRepository.findAll();
    }

    public Achat findById(Long id) {
        return achatRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        achatRepository.deleteById(id);
    }

}

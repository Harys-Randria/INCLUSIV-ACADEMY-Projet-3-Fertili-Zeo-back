package com.fertilizeo.controller;

import com.fertilizeo.entity.Achat;
import com.fertilizeo.service.AchatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achats")
public class AchatController {

    @Autowired
    private AchatService achatService;

    @PostMapping
    public Achat createAchat(@RequestBody Achat achat) {
        return achatService.save(achat);
    }

    @GetMapping
    public List<Achat> getAllAchats() {
        return achatService.findAll();
    }

    @GetMapping("/{id}")
    public Achat getAchatById(@PathVariable Long id) {
        return achatService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAchat(@PathVariable Long id) {
        achatService.deleteById(id);
    }

}

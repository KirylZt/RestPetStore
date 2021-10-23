package com.example.controller;

import com.example.entity.Pet;
import com.example.entity.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pet")
public class PetController {
    private final List<Pet> petList = new ArrayList<>();

    @PostMapping("/save")
    public void save(@Valid @RequestBody Pet pet) {
        petList.add(pet);
    }

    @PutMapping("/update")
    public void update(@RequestBody Pet pet){
        petList.set(petList.indexOf(pet), pet);
    }

    @GetMapping("/byStatus/{petStatus}")
    public ResponseEntity<List<Pet>> findByStatus(@PathVariable String petStatus) {
        List<Pet> returnPets = petList.stream()
                    .filter(p -> p.getStatus().equals(Status.valueOf(petStatus.toUpperCase(Locale.ENGLISH))))
                    .collect(Collectors.toList());
        if (returnPets.isEmpty()){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(returnPets, HttpStatus.OK);
    }

    @GetMapping("/byId/{petId}")
    public ResponseEntity<Pet> findById(@PathVariable long petId) {
        for (Pet pet : petList){
            if(pet.getId() == petId){
                return ResponseEntity.ok(pet);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/delete/{id}")
    public void deletePet(@PathVariable long id) {
        petList.removeIf(p -> p.getId() == id);
    }

    @PostMapping("/update/{petId}")
    public void update(@PathVariable long petId, Status status, String name){
        for (Pet pet : petList){
            if(pet.getId() == petId){
                pet.setStatus(status);
                pet.setName(name);
                break;
            }
        }
    }
}

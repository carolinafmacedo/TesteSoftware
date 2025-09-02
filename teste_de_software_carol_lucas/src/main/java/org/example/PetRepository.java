package org.example;

import java.util.ArrayList;
import java.util.List;


public class PetRepository {

    private final List<Pet> pets = new ArrayList<>();

    public Pet inserir(Pet pet) {
        this.pets.add(pet);
        return pet;
    }

}
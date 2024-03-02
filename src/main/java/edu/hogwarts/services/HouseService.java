package edu.hogwarts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.hogwarts.models.House;
import edu.hogwarts.repositories.HouseRepository;

import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    public House findOrCreateHouse(House house) {
        House existingHouse = houseRepository.findByName(house.getName());
        if (existingHouse != null) {
            return existingHouse;
        } else {
            return houseRepository.save(house);
        }
    }

    public House findHouseByName(String name) {
        return houseRepository.findByName(name);
    }
}

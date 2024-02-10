package com.project.isima.enums.services;

import com.project.isima.entities.Parcel;
import com.project.isima.entities.User;
import com.project.isima.exceptions.ParcelNotFoundException;
import com.project.isima.exceptions.UserNotFoundException;
import com.project.isima.repositories.ParcelRepository;
import com.project.isima.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ParcelService {
    private ParcelRepository parcelRepository;
    private UserRepository userRepository;

    public ParcelService(ParcelRepository parcelRepository, UserRepository userRepository) {
        this.parcelRepository = parcelRepository;
        this.userRepository = userRepository;
    }

    public List<Parcel> getAllParcels(Long senderId) {
        Optional<User> userOptional = userRepository.findById(senderId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found !");
        }
        User user = userOptional.get();
        return parcelRepository.findAllByUser(user);
    }

    public Parcel addNewParcel(Long senderId, Parcel parcel) {
        Optional<User> userOptional = userRepository.findById(senderId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found !");
        }
        User sender = userOptional.get();
        parcel.setUser(sender);
        return parcelRepository.save(parcel);
    }

    public void deleteParcel(Long id) throws ParcelNotFoundException {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new ParcelNotFoundException("Parcel Not Found !"));
        parcelRepository.deleteById(id);
    }

    public Parcel updateParcel(Parcel parcel) {
        Parcel found = parcelRepository.findById(parcel.getId()).orElseThrow(
                ()-> new ParcelNotFoundException("Parcel Not Found !")
        );
        return parcelRepository.save(parcel);
    }
}

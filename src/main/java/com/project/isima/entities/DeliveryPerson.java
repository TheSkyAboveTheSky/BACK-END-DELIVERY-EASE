package com.project.isima.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DeliveryPerson extends User {
    @Lob
    private byte[] card;

}


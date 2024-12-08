package com.hiennguyen.hotelapp.entities;

import java.util.UUID;

import com.hiennguyen.hotelapp.enums.RoomType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, columnDefinition = "NVARCHAR(255)")
    private String number;

    @Column(name="type", nullable=false)
    private RoomType type;

    @Column(name="capacity", nullable=false, columnDefinition="INT")
    private int capacity;

    @Column(name="price", nullable=false, columnDefinition="FLOAT")
    private double price;
}

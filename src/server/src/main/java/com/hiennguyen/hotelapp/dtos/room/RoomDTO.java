package com.hiennguyen.hotelapp.dtos.room;

import java.util.UUID;

import com.hiennguyen.hotelapp.enums.RoomType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private UUID id;

    private  String number;

    private RoomType Type;

    private int capacity;

    private double price;
}

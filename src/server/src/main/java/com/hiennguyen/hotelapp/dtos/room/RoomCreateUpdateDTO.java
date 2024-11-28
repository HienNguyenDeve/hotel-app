package com.hiennguyen.hotelapp.dtos.room;

import org.hibernate.validator.constraints.Length;

import com.hiennguyen.hotelapp.enums.RoomType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreateUpdateDTO {

    @NotBlank(message="Room number is required")
    @Length(min=2, max=255, message="Room Number must be between 2 and 255 characters")
    private String roomNumber;

    @NotNull(message="Room Type is required")
    private RoomType roomType;

    @NotNull(message="Room capacity is required")
    @Min(value=1, message="Room capacity must be greater than 0")
    private int capacity;

    @NotNull(message="Price is required")
    @Min(value=0, message="Room price must be greater than or equal to 0")
    private double price;
}

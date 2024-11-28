package com.hiennguyen.hotelapp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hiennguyen.hotelapp.dtos.room.RoomCreateUpdateDTO;
import com.hiennguyen.hotelapp.dtos.room.RoomDTO;
import com.hiennguyen.hotelapp.enums.RoomType;

public interface RoomService {
    List<RoomDTO> findAll();

    List<RoomDTO> findByRoomNumber(String keyword);

    List<RoomDTO> findByRoomType(RoomType type);

    Page<RoomDTO> findPaginated(String keyword, Pageable pageable);

    RoomDTO findById(UUID id);

    RoomDTO create(RoomCreateUpdateDTO roomCreateUpdateDTO);

    RoomDTO update(UUID id, RoomCreateUpdateDTO roomCreateUpdateDTO);

    boolean deleteById(UUID id);
}

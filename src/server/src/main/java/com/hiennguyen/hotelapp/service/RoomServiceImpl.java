package com.hiennguyen.hotelapp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hiennguyen.hotelapp.dtos.room.RoomCreateUpdateDTO;
import com.hiennguyen.hotelapp.dtos.room.RoomDTO;
import com.hiennguyen.hotelapp.entities.Room;
import com.hiennguyen.hotelapp.enums.RoomType;
import com.hiennguyen.hotelapp.repositories.RoomRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoomServiceImpl implements RoomService{
    private final  RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDTO> findAll() {
        // Find all rooms by repository
        var rooms = roomRepository.findAll();

        // Convert rooms to roomDTOs
        var roomDTOs = rooms.stream().map(room -> {
            var roomDTO = new RoomDTO();
            roomDTO.setId(room.getId());
            roomDTO.setNumber(room.getNumber());
            roomDTO.setType(room.getType());
            roomDTO.setCapacity(room.getCapacity());
            roomDTO.setPrice(room.getPrice());

            return roomDTO;
        }).toList();

        return roomDTOs;
    }

    @Override
    public List<RoomDTO> findByRoomNumber(String keyword) {
        // Check null keyword
        if (keyword == null) {
            throw new IllegalArgumentException("keyword is required");
        }

        // Check null
        Specification<Room> specification = (r, q, cb) -> {
            return cb.like(r.get("number"), "%" + keyword + "%");
        };

        var rooms = roomRepository.findAll(specification);

        // Convert rooms to roomDTOs
        var roomDTOs = rooms.stream().map(room -> {
            var roomDTO = new RoomDTO();
            roomDTO.setId(room.getId());
            roomDTO.setNumber(room.getNumber());
            roomDTO.setType(room.getType());
            roomDTO.setCapacity(room.getCapacity());
            roomDTO.setPrice(room.getPrice());

            return roomDTO;
        }).toList();

        return roomDTOs;
    }

    @Override
    public List<RoomDTO> findByRoomType(RoomType type) {
        Specification<Room> specification = (r, q, cb) -> {
            if (type == null) {
                return null;
            }
            return cb.equal(r.get("type"), type);
        };

        var rooms = roomRepository.findAll(specification);

        //Convert rooms to roomDTOS
        var roomDTOs = rooms.stream().map(room -> {
            var roomDTO = new RoomDTO();
            roomDTO.setId(room.getId());
            roomDTO.setNumber(room.getNumber());
            roomDTO.setType(room.getType());
            roomDTO.setCapacity(room.getCapacity());
            roomDTO.setPrice(room.getPrice());
            return roomDTO;
        }).toList();

        return roomDTOs;
    }

    @Override
    public Page<RoomDTO> findPaginated(String keyword, Pageable pageable) {
        Specification<Room> specification = (r, q, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.like(r.get("number"), "%" + keyword + "%");
        };

        var rooms = roomRepository.findAll(specification, pageable);

        //Convert rooms to roomDTOs
        var roomDTOs = rooms.map(room -> {
            var roomDTO = new RoomDTO();
            roomDTO.setId(room.getId());
            roomDTO.setNumber(room.getNumber());
            roomDTO.setType(room.getType());
            roomDTO.setCapacity(room.getCapacity());
            roomDTO.setPrice(room.getPrice());
            return roomDTO;
        });

        return roomDTOs;
    }

    @Override
    public RoomDTO findById(UUID id) {
        var room = roomRepository.findById(id).orElse(null);

        // Check null
        if (room == null) {
            throw new IllegalArgumentException("room is not found");
        }

        //convert room to roomDTO
        var roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setNumber(room.getNumber());
        roomDTO.setType(room.getType());
        roomDTO.setCapacity(room.getCapacity());
        roomDTO.setPrice(room.getPrice());

        return roomDTO;
    }

    @Override
    public RoomDTO create(RoomCreateUpdateDTO roomCreateUpdateDTO) {
        //Check null roomCreateUpdateDTO
        if (roomCreateUpdateDTO == null) {
            throw new IllegalArgumentException("RoomDTO is required");
        }

        //Check existing room
        var existingRoom = roomRepository.findByNumber(roomCreateUpdateDTO.getRoomNumber());
        if (existingRoom != null) {
            throw new IllegalArgumentException("Room Number is already existing");
        }
        //Convert roomCreateUpdateDTO to room
        var room = new Room();
        room.setNumber(roomCreateUpdateDTO.getRoomNumber());
        room.setType(roomCreateUpdateDTO.getRoomType());
        room.setCapacity(roomCreateUpdateDTO.getCapacity());
        room.setPrice(roomCreateUpdateDTO.getPrice());

        // Save to database
        room = roomRepository.save(room);

        // Convert room to RoomDTO
        var roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setNumber(room.getNumber());
        roomDTO.setType(room.getType());
        roomDTO.setCapacity(room.getCapacity());
        roomDTO.setPrice(room.getPrice());

        // Return roomDTO
        return roomDTO;
    }

    @Override
    public RoomDTO update(UUID id, RoomCreateUpdateDTO roomCreateUpdateDTO) {

        //Check null roomCreateUpdateDTO
        if (roomCreateUpdateDTO == null) {
            throw new IllegalArgumentException("RoomDTO is required");
        }

        var updatedRoom = roomRepository.findById(id).orElse(null);
        if (updatedRoom == null) {
            throw new IllegalArgumentException("Room is not existing");
        }

        //Check existing room
        var existingRoom = roomRepository.findByNumber(roomCreateUpdateDTO.getRoomNumber());
        if (existingRoom != null && existingRoom.getId().equals(id)) {
            throw new IllegalArgumentException("Room Number is already existing");
        }

        //Convert roomCreateUpdateDTO to room
        updatedRoom.setNumber(roomCreateUpdateDTO.getRoomNumber());
        updatedRoom.setType(roomCreateUpdateDTO.getRoomType());
        updatedRoom.setCapacity(roomCreateUpdateDTO.getCapacity());
        updatedRoom.setPrice(roomCreateUpdateDTO.getPrice());

        // Save to database
        updatedRoom = roomRepository.save(updatedRoom);

        // Convert room to RoomDTO
        var newRoomDTO = new RoomDTO();
        newRoomDTO.setId(updatedRoom.getId());
        newRoomDTO.setNumber(updatedRoom.getNumber());
        newRoomDTO.setType(updatedRoom.getType());
        newRoomDTO.setCapacity(updatedRoom.getCapacity());
        newRoomDTO.setPrice(updatedRoom.getPrice());

        // Return roomDTO
        return newRoomDTO;
    }

    @Override
    public boolean deleteById(UUID id) {
        // Check null room
        var room = roomRepository.findById(id).orElse(null);
        if (room == null) {
            throw new IllegalArgumentException("Room is not found");
        }

        // Delete
        roomRepository.delete(room);

        return !roomRepository.existsById(id);
    }
}

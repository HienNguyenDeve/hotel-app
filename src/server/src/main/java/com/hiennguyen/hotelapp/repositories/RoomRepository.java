package com.hiennguyen.hotelapp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hiennguyen.hotelapp.entities.Room;

public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room>{
    Room findByNumber(String number);
}
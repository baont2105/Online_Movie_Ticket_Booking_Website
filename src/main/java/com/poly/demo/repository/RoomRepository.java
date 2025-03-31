package com.poly.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
	List<Room> findByBranch(Branch branch);

	Optional<Room> findByRoomId(Integer id);

	void deleteByRoomId(Integer id);

	Page<Room> findAll(Pageable pageable);
}
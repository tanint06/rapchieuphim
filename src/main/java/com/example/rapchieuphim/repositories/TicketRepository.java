package com.example.rapchieuphim.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rapchieuphim.model.Ticket;

@Repository
public interface  TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByShowtimeId(Long showtimeId);
    
    List<Ticket> findByUserId(Long userId);
}

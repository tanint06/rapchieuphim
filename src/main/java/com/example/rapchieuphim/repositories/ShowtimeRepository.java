package com.example.rapchieuphim.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.rapchieuphim.model.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findByMovieId(Long movieId);
    @Query("SELECT s FROM Showtime s WHERE s.startTime BETWEEN :start AND :end")
    List<Showtime> findShowtimesByDate(LocalDateTime start, LocalDateTime end);
}

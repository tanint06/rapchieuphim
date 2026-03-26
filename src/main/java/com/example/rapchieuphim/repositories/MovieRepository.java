package com.example.rapchieuphim.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rapchieuphim.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Nếu sau này muốn tìm phim theo thể loại, ta chỉ cần khai báo:
    List<Movie> findByGenre(String genre);
    
    // Tìm phim theo tên có chứa từ khóa
    List<Movie> findByTitleContainingIgnoreCase(String keyword);
    List<Movie> findByActiveTrue();
}
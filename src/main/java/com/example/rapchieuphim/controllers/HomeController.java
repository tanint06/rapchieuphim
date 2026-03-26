package com.example.rapchieuphim.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.rapchieuphim.model.Movie;
import com.example.rapchieuphim.model.Showtime;
import com.example.rapchieuphim.model.User;
import com.example.rapchieuphim.repositories.MovieRepository;
import com.example.rapchieuphim.repositories.ShowtimeRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    // Gọi MovieRepository để làm việc với bảng Movies
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        // 1. Kiểm tra đăng nhập
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        // 2. Lấy toàn bộ danh sách phim từ Database
        List<Movie> movieList = movieRepository.findAll();
        
        // 3. Truyền dữ liệu sang giao diện (HTML)
        model.addAttribute("user", user); 
        model.addAttribute("movies", movieList); // Truyền danh sách phim sang HTML với tên biến là 'movies'
        
        return "index";
    }
    @GetMapping("/movie/detail/{id}")
public String movieDetail(@PathVariable Long id, Model model) {
    Movie movie = movieRepository.findById(id).orElse(null);
    // Lấy lịch chiếu của riêng phim này
    List<Showtime> showtimes = showtimeRepository.findByMovieId(id);
    
    model.addAttribute("movie", movie);
    model.addAttribute("showtimes", showtimes);
    return "customer/movie_detail";
}

}
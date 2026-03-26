package com.example.rapchieuphim.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.rapchieuphim.model.Movie;
import com.example.rapchieuphim.model.User;

import com.example.rapchieuphim.repositories.MovieRepository;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/movies") // Đặt tiền tố chung cho tất cả các đường dẫn trong file này
public class AdminMovieController {

    @Autowired
    private MovieRepository movieRepository;
   

    // Hàm dùng chung để kiểm tra quyền Admin
    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return user != null && "ADMIN".equals(user.getRole());
    }

    // 1. Xem danh sách phim (Giao diện bảng)
    @GetMapping
    public String listMovies(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/"; // Không phải Admin -> Đuổi về trang chủ

        List<Movie> movies = movieRepository.findByActiveTrue();
        model.addAttribute("movies", movies);
        return "/admin/admin_movies";
    }

    // 2. Mở form Thêm phim mới
    @GetMapping("/add")
    public String showAddForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/";

        model.addAttribute("movie", new Movie()); // Gửi 1 object Movie rỗng sang form
        return "/admin/movie_form";
    }

    // 3. Mở form Sửa phim (Lấy dữ liệu phim cũ đưa lên form)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/";

        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) return "redirect:/admin/movies";

        model.addAttribute("movie", movie);
        return "/admin/movie_form";
    }

    // 4. Xử lý Lưu phim (Dùng chung cho cả Thêm và Sửa)
    @PostMapping("/save")
    public String saveMovie(@ModelAttribute Movie movie, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";

        movieRepository.save(movie); // Nếu movie có ID thì nó sẽ Sửa (Update), chưa có ID nó sẽ Thêm (Insert)
        return "redirect:/admin/movies";
    }

    // 5. Xử lý Xóa phim
    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";

        movieRepository.deleteById(id);
        return "redirect:/admin/movies";
    }
    
}
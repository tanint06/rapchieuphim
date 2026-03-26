package com.example.rapchieuphim.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.rapchieuphim.model.Showtime;
import com.example.rapchieuphim.model.User;
import com.example.rapchieuphim.repositories.MovieRepository;
import com.example.rapchieuphim.repositories.ShowtimeRepository;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/admin/showtimes")
public class AdminShowtimeController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;
    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return user != null && "ADMIN".equals(user.getRole());
    }
    @GetMapping("/add")
    public String showAddShowtimeForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/";

        model.addAttribute("showtime", new Showtime());
        // Lấy danh sách phim để đổ vào Dropdown
        model.addAttribute("movies", movieRepository.findAll());
       
        
        return "/admin/showtime_form";
    }
    @PostMapping("/save")
    public String saveShowtime(@ModelAttribute Showtime showtime, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";

        // Lưu suất chiếu mới vào database (cần tạo repository và service cho Showtime)
        showtimeRepository.save(showtime);
        
        return "redirect:/admin/movies"; // Quay về trang danh sách phim sau khi lưu
    }
}

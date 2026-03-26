package com.example.rapchieuphim.controllers;

import com.example.rapchieuphim.model.User;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rapchieuphim.repositories.UserRepository;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // HIỂN THỊ TRANG ĐĂNG KÝ
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; 
    }

    // XỬ LÝ ĐĂNG KÝ
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, 
                               @RequestParam String password, 
                               Model model) {
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Tên tài khoản đã tồn tại!");
            return "register";
        }
        
        User newUser = new User(username, password);
        if (newUser.getUsername().equalsIgnoreCase("admin")) {
            newUser.setRole("ADMIN"); // Nếu tên đăng nhập là "admin" thì cho làm Quản trị viên
        } else {
            newUser.setRole("USER");  // Khách hàng bình thường
        }
        userRepository.save(newUser); // Lưu vào database
        
        model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "login";
    }

    // HIỂN THỊ TRANG ĐĂNG NHẬP
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // XỬ LÝ ĐĂNG NHẬP
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, 
                            @RequestParam String password, 
                            HttpSession session, 
                            Model model) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        
        if (user != null) {
            // Đăng nhập đúng -> Lưu thông tin vào Session
            session.setAttribute("loggedInUser", user);
            return "redirect:/"; // Chuyển hướng thẳng vào Trang Chủ
        } else {
            // Đăng nhập sai
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            return "login";
        }
    }

    // ĐĂNG XUẤT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa phiên làm việc
        return "redirect:/login"; // Quay lại trang đăng nhập
    }
}
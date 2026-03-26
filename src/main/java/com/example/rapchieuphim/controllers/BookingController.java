package com.example.rapchieuphim.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rapchieuphim.model.Showtime;
import com.example.rapchieuphim.model.Ticket;
import com.example.rapchieuphim.model.User;
import com.example.rapchieuphim.repositories.ShowtimeRepository;
import com.example.rapchieuphim.repositories.TicketRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // 1. TRANG CHỌN GHẾ: Khóa những ghế đã có trong DB
    @GetMapping("/{showtimeId}")
    public String showSeatMap(@PathVariable Long showtimeId, Model model) {
        Showtime showtime = showtimeRepository.findById(showtimeId).orElse(null);
        if (showtime == null) return "redirect:/";

        List<String> occupiedSeats = ticketRepository.findByShowtimeId(showtimeId)
                .stream()
                .map(Ticket::getSeatNumber)
                .collect(Collectors.toList());

        model.addAttribute("showtime", showtime);
        model.addAttribute("occupiedSeats", occupiedSeats);

        return "customer/seat_selection";
    }

    // 2. TRANG XÁC NHẬN THANH TOÁN: Kiểm tra trùng ghế rồi hiện hóa đơn
    @PostMapping("/confirm")
    public String confirmBooking(@RequestParam Long showtimeId,
                                 @RequestParam List<String> seatNumbers,
                                 HttpSession session,
                                 Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Showtime showtime = showtimeRepository.findById(showtimeId).orElse(null);
        
        // Kiểm tra lại lần nữa xem trong lúc chọn có ai nhanh tay đặt mất chưa
        List<String> occupiedSeats = ticketRepository.findByShowtimeId(showtimeId)
                .stream()
                .map(Ticket::getSeatNumber)
                .collect(Collectors.toList());

        for (String seat : seatNumbers) {
            if (occupiedSeats.contains(seat)) {
                return "redirect:/booking/" + showtimeId + "?error=true";
            }
        }

        // Tính toán thông tin để hiện trang thanh toán
        double totalPrice = seatNumbers.size() * showtime.getPrice();
        String selectedSeatsStr = String.join(",", seatNumbers); // Chuyển List thành "A1,A2"

        model.addAttribute("showtime", showtime);
        model.addAttribute("selectedSeatsStr", selectedSeatsStr);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("seatCount", seatNumbers.size());

        return "customer/payment_confirm"; // Trả về file HTML thanh toán
    }

    // 3. LƯU VÉ: Chỉ gọi sau khi bấm nút "Thanh toán" ở trang payment_confirm
    @PostMapping("/save")
    public String saveTickets(@RequestParam Long showtimeId,
                              @RequestParam String seats,
                              HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Showtime showtime = showtimeRepository.findById(showtimeId).orElse(null);
        String[] seatArray = seats.split(",");

        for (String s : seatArray) {
            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setShowtime(showtime);
            ticket.setSeatNumber(s);
            ticket.setBookingTime(LocalDateTime.now());
            ticket.setTotalPrice(showtime.getPrice());
            ticketRepository.save(ticket);
        }
        return "redirect:/booking/my-tickets";
    }

    @GetMapping("/my-tickets")
    public String showMyTickets(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        List<Ticket> myTickets = ticketRepository.findByUserId(user.getId());
        model.addAttribute("tickets", myTickets);
        return "customer/my_tickets";
    }

    @PostMapping("/cancel-ticket/{id}")
    public String cancelTicket(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket != null && ticket.getUser().getId().equals(user.getId())) {
            ticketRepository.delete(ticket);
        }
        return "redirect:/booking/my-tickets";
    }
}
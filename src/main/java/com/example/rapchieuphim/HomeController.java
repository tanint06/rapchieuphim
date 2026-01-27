package com.example.rapchieuphim;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Rạp Chiếu Phim</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        margin: 0;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    }
                    .container {
                        text-align: center;
                        background: white;
                        padding: 40px;
                        border-radius: 10px;
                        box-shadow: 0 10px 25px rgba(0,0,0,0.2);
                    }
                    h1 {
                        color: #333;
                        margin: 0 0 20px 0;
                    }
                    p {
                        color: #666;
                        font-size: 18px;
                    }
                    .links {
                        margin-top: 30px;
                    }
                    a {
                        display: inline-block;
                        padding: 10px 20px;
                        margin: 10px;
                        background-color: #667eea;
                        color: white;
                        text-decoration: none;
                        border-radius: 5px;
                        transition: background-color 0.3s;
                    }
                    a:hover {
                        background-color: #764ba2;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>🎬 Rạp Chiếu Phim</h1>
                    <p>Chào mừng đến với hệ thống quản lý rạp chiếu phim</p>
                    <div class="links">
                        <a href="/api/phim">Danh sách phim</a>
                        <a href="/api/status">Trạng thái</a>
                    </div>
                </div>
            </body>
            </html>
            """;
    }
    
    @GetMapping("/api/phim")
    @ResponseBody
    public String getMovies() {
        return """
            {
                "status": "success",
                "message": "Danh sách phim",
                "movies": [
                    {"id": 1, "title": "Avengers: Endgame", "year": 2019},
                    {"id": 2, "title": "Titanic", "year": 1997},
                    {"id": 3, "title": "Avatar", "year": 2009}
                ]
            }
            """;
    }
    
    @GetMapping("/api/status")
    @ResponseBody
    public String getStatus() {
        return """
            {
                "status": "running",
                "application": "Rạp Chiếu Phim",
                "version": "1.0",
                "timestamp": "2026-01-27"
            }
            """;
    }
}

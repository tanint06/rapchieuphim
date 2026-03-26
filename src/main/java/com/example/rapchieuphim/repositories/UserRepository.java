package com.example.rapchieuphim.repositories;

import com.example.rapchieuphim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Tự động tìm user theo username và password
    User findByUsernameAndPassword(String username, String password);
    
    // Tự động kiểm tra xem username đã tồn tại chưa
    boolean existsByUsername(String username);
}
package com.example.quizapp;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        // Trước khi lưu user, bạn có thể đặt giá trị cho timep nếu cần
        // Ví dụ: user.setTimep(0);
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        // Bạn có thể kiểm tra và thực hiện xử lý với trường timep ở đây nếu cần

        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setScore(user.getScore());
        existingUser.setQuestionbaomat(user.getQuestionbaomat());
        existingUser.setAnswerbaomat(user.getAnswerbaomat());
        existingUser.setKimcuong(user.getKimcuong());
        existingUser.setAvatar(user.getAvatar());
        existingUser.setTimep(user.getTimep()); // Cập nhật trường timep

        return userRepository.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

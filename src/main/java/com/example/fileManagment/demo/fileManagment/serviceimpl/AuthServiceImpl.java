package com.example.fileManagment.demo.fileManagment.serviceimpl;

import com.example.fileManagment.demo.fileManagment.dto.request.RegisterRequest;
import com.example.fileManagment.demo.fileManagment.dto.request.LoginRequest;
import com.example.fileManagment.demo.fileManagment.dto.response.AuthResponse;
import com.example.fileManagment.demo.fileManagment.enam.Role;
import com.example.fileManagment.demo.fileManagment.entity.User;
import com.example.fileManagment.demo.fileManagment.exception.AdminAlreadyExistsException;
import com.example.fileManagment.demo.fileManagment.exception.DuplicateUserException;
import com.example.fileManagment.demo.fileManagment.exception.InvalidPasswordException;
import com.example.fileManagment.demo.fileManagment.repository.UserRepository;

import com.example.fileManagment.demo.fileManagment.serviceI.AuthService;
import com.example.fileManagment.demo.fileManagment.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {
    private final UserRepository repo;
    private final JWTUtil jwt;
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;

   @Override
    public AuthResponse login(LoginRequest request) {

        User user = repo.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid credentials");
        }

        String token = jwt.generateToken(user.getUsername());

       return AuthResponse.builder()
               .token(token)
               .username(user.getUsername())
               .role(String.valueOf(user.getRole()))
               .build();


    }

    @Override

    public User register(RegisterRequest r) {

        if (repo.existsByUsername(r.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (repo.findByEmail(r.getEmail()).isPresent()) {
            throw new DuplicateUserException("Email already exists");
        }



            // 🔐 Normalize role input
            String requestedRole = r.getRole() == null
                    ? "USER"
                    : r.getRole().toUpperCase();

        if (requestedRole.equals("ADMIN")) {
            boolean adminExists = repo.existsByRole(Role.ADMIN);
            if (adminExists) {
                throw new AdminAlreadyExistsException(
                        "Admin already exists. Only one admin is allowed."
                );
            }
        }

        // ✅ Map request → entity
        User user = mapper.map(r, User.class);

        // 🔐 Secure password
        user.setPassword(encoder.encode(r.getPassword()));

        // 🔐 Set validated role
        user.setRole(Role.valueOf(requestedRole));

        return repo.save(user);

        }
    }



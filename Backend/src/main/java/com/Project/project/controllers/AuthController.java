package com.Project.project.controllers;
import com.Project.project.Repository.LessonRepo;
import com.Project.project.model.Course;
import com.Project.project.model.Lesson;
import com.Project.project.models.User;
import com.Project.project.models.ERole;
import com.Project.project.models.Role;
import com.Project.project.payload.request.LoginRequest;
import com.Project.project.payload.request.SignupRequest;
import com.Project.project.payload.response.MessageResponse;
import com.Project.project.payload.response.UserInfoResponse;
import com.Project.project.Repository.RoleRepository;
import com.Project.project.Repository.UserRepository;
import com.Project.project.security.jwt.JwtUtils;
import com.Project.project.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;
  @Autowired
  LessonRepo lessonRepo;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getId(),
                                   userDetails.getUsername(),
                                   userDetails.getEmail(),
                                   roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles.isEmpty() ) {
      Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            break;
          case "teacher":
            Role modRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);
            break;
          case "student":
            Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(studentRole);
            break;
          default:
            throw new RuntimeException("Error: Role not allowed.");
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }


  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }
  @PostMapping("/users")
  public ResponseEntity<?> addUser(@Valid @RequestBody SignupRequest signUpRequest) {
    // Check if the username already exists
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    // Check if the email is already in use
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles.isEmpty()) {
      Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            break;
          case "teacher":
            Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(teacherRole);
            break;
          case "student":
            Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(studentRole);
            break;
          default:
            throw new RuntimeException("Error: Role not allowed.");
        }
      });
    }

    // Create a new user's account
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @GetMapping("/users/{id}")

  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  @PutMapping("/users/{id}")

  public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody SignupRequest signUpRequest) {
    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      // Update the user details
      user.setUsername(signUpRequest.getUsername());
      user.setEmail(signUpRequest.getEmail());
      user.setPassword(encoder.encode(signUpRequest.getPassword()));
      Set<String> strRoles = signUpRequest.getRole();
      Set<Role> roles = new HashSet<>();
      // Set the roles based on the request
      // ...

      user.setRoles(roles);
      userRepository.save(user);

      return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  @DeleteMapping("/users/{id}")

  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isPresent()) {
      userRepository.delete(userOptional.get());
      return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  @GetMapping("/users/role/{roleName}")
  public ResponseEntity<?> getUsersByRole(@PathVariable("roleName") ERole roleName) {
    List<User> users = userRepository.findByRoleName(roleName);
    return ResponseEntity.ok(users);
  }
  @GetMapping("/users/{userId}/courses")
  public ResponseEntity<?> getUserCourses(@PathVariable Long userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      List<Course> courses = user.getCourses();
      return ResponseEntity.ok(courses);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  @GetMapping("/{userId}/lessons")
  public List<Lesson> getUserLessons(@PathVariable Long userId) {
    // Retrieve the user by userId
    User user = userRepository.findById(userId).orElse(null);

    if (user != null) {
      List<Lesson> userLessons = new ArrayList<>();

      // Retrieve courses associated with the user
      List<Course> userCourses = user.getCourses();

      // Iterate over the courses
      for (Course course : userCourses) {
        Long courseId = course.getCourseId();

        // Fetch lessons associated with the course using the courseId

        List<Lesson> courseLessons = lessonRepo.findByCourseId(courseId);

        // Add the course lessons to the user lessons list
        userLessons.addAll(courseLessons);
      }

      return userLessons;
    }

    return Collections.emptyList(); // User not found
  }








}

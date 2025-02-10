package com.example.airlinebooking.airline_booking_system.entity;

import com.example.airlinebooking.airline_booking_system.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @NotNull(message = "Username is required.")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters.")
    @Column(unique = true, nullable = false)
    private String userName;

    @NotNull(message = "Display name is required.")
    @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters.")
    @Column(name = "display_name", nullable = false)
    private String displayName;

    @NotNull(message = "Password is required.")
    @Column(nullable = false)
    @Size(min = 8, max = 20, message = "Password must be atleast 8 and less than 20 characters.")
    private String password;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required.")
    @Column(nullable = false, columnDefinition = "varchar(255) default 'CUSTOMER'")
    private UserRoles role;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    // One User can have multiple Notifications
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NotificationEntity> notifications;
}

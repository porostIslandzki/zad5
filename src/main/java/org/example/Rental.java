package org.example;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "rental")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {
    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Column(name = "vehicle_id", nullable = false)
    private String vehicleId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "rent_date", nullable = false)
    private String rentDate;

    @Column(name = "return_date")
    private String returnDate;
}
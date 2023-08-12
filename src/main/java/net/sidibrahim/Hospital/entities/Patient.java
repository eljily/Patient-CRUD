package net.sidibrahim.Hospital.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Patient {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@NotEmpty
@Size(min = 4,max = 20)
    private String nom;
@Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private Boolean malade=false;
    @Min(100)
    private int score ;


}

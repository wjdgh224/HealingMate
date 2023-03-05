package apptive.backend.login.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Disease {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diseaseId")
    private Long diseaseId;

    private String diseaseName;
}

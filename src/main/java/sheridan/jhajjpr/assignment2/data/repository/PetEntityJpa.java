package sheridan.jhajjpr.assignment2.data.repository;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "pet")
public class PetEntityJpa {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fpet_name")
    private String firstName = "";

    @Column(name = "lpet_name")
    private String lastName = "";

    @Column(name = "pet_kind")
    //By default in the drop-down menu the Dog pet is selected
    private String petKind = "Dog";

    @Column(name = "pet_gender")
    private String petGender = "Female";

    @Column(name = "pet_vaccination")
    private Boolean petVaccination = false;
}

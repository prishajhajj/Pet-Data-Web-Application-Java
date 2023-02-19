package sheridan.jhajjpr.assignment2.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class PetsForm implements Serializable {

    private int id = 0;

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "[A-Za-z]*")
    private String firstName = "";

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "[A-Za-z]*")
    private String lastName = "";

    @NotBlank
    @Pattern(regexp = "(Dog|Cat|Rabbit)?")
    private String petKind = "";

    @NotNull
    private String petGender = "Female";

    private boolean petVaccination = false;
}

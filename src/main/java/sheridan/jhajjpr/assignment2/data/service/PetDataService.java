package sheridan.jhajjpr.assignment2.data.service;

import sheridan.jhajjpr.assignment2.model.PetsForm;
import java.util.List;


public interface PetDataService {

    void insertPetForm(PetsForm form);

    List<PetsForm> getAllPetsForm();

    void deleteAllPetForms();

    void deletePetForm(int id);

    PetsForm getPetForm(int id);

    void updatePetForm(PetsForm form);


}

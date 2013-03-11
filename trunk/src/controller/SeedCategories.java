package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import model.Category;
import model.Entry;
import model.Person;
import model.Role;
import repository.CategoryRepository;
import repository.EntryRepository;
import repository.PersonRepository;

public class SeedCategories {

    static CategoryRepository _categoryRepo;
    static PersonRepository _personRepo;
    static EntryRepository _entryRepo;
    
    /**
     * @param args
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        _categoryRepo = new CategoryRepository();
        _personRepo = new PersonRepository();
        _entryRepo = new EntryRepository();
        
        addCategory("Jacquard");
        addCategory("Basket");
        addCategory("Leno or Gauze");
        addCategory("Pile");
        addCategory("Plain");
        addCategory("Satin");
        addCategory("Twill");
        
        addPeople();
        //addEntries();
    }
    
    private static void addCategory(String name) throws IOException {
        Category category = new Category();
        category.setName(name);     
        _categoryRepo.add(category);
    }
    
    private static void addPeople() {
           Person organizer = new Person();
            organizer.setEMail("organizer");
            organizer.setFirstName("Jean");
            organizer.setLastName("Bowman");
            organizer.setPassword("password");
            organizer.setRole(Role.Organizer);
            organizer.setPhoneNumber("242-395-5822");
            try {
                _personRepo.add(organizer);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            Person judge = new Person();
            judge.setEMail("judge");
            judge.setFirstName("Gary");
            judge.setLastName("Payton");
            judge.setPassword("password");
            judge.setRole(Role.Judge);
            judge.setPhoneNumber("222-315-5821");
            try {
                _personRepo.add(judge);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Person entrant = new Person();
            entrant.setEMail("julie@uw.edu");
            entrant.setFirstName("Julie");
            entrant.setLastName("Impola");
            entrant.setPassword("password");
            entrant.setRole(Role.Entrant);
            entrant.setPhoneNumber("253-355-5822");
            try {
                _personRepo.add(entrant);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            Person attendee = new Person();
            entrant.setEMail("james@uw.edu");
            entrant.setFirstName("James");
            entrant.setLastName("M");
            entrant.setPassword("password");
            entrant.setRole(Role.Attendee);
            entrant.setPhoneNumber("253-555-5555");
            try {
                _personRepo.add(entrant);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            Person entrant2 = new Person();
            entrant.setEMail("matt@uw.edu");
            entrant.setFirstName("Matt");
            entrant.setLastName("Adams");
            entrant.setPassword("password");
            entrant.setRole(Role.Entrant);
            entrant.setPhoneNumber("112-355-5822");
            try {
                _personRepo.add(entrant);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            Person entrant3 = new Person();
            entrant.setEMail("ralph@uw.edu");
            entrant.setFirstName("Ralph");
            entrant.setLastName("Feltis");
            entrant.setPassword("password");
            entrant.setRole(Role.Entrant);
            entrant.setPhoneNumber("253-444-4444");
            try {
                _personRepo.add(entrant);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    
    }
    
    private static void addEntries() {
        Entry entry2 = new Entry();
        entry2.setCategoryName("Satin");
        entry2.setEmail("julie@uw.edu");
        entry2.setDateSubmitted(new Date());
        entry2.setDescription("I love weaving more than you.");
        entry2.setMaterials("material1");
        entry2.setTechniques("technique1");
        entry2.setTitle("Cool Weave");
        try {
            _entryRepo.add(entry2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Entry entry3 = new Entry();
        entry3.setCategoryName("Twill");
        entry3.setEmail("julie@uw.edu");
        entry3.setDateSubmitted(new Date());
        entry3.setDescription("I like weaving.");
        entry3.setMaterials("material2, material3");
        entry3.setTechniques("technique1, technique2");
        entry3.setTitle("Coolest Weave");
        try {
            _entryRepo.add(entry3);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Entry entry4 = new Entry();
        entry4.setCategoryName("Satin");
        entry4.setEmail("matt@uw.edu");
        entry4.setDateSubmitted(new Date());
        entry4.setDescription("I like to weave.");
        entry4.setMaterials("material3");
        entry4.setTechniques("technique2");
        entry4.setTitle("Weave");
        try {
            _entryRepo.add(entry4);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Entry entry5 = new Entry();
        entry5.setCategoryName("Basket");
        entry5.setEmail("ralph@uw.edu");
        entry5.setDateSubmitted(new Date());
        entry5.setDescription("Weaving is fun.");
        entry5.setMaterials("material2");
        entry5.setTechniques("technique1");
        entry5.setTitle("Nice Weave");
        try {
            _entryRepo.add(entry5);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
    
    }

}

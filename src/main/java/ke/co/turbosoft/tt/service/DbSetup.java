package ke.co.turbosoft.tt.service;

import javax.annotation.PostConstruct;

import ke.co.turbosoft.tt.entity.Company;
import ke.co.turbosoft.tt.entity.User;
import ke.co.turbosoft.tt.repo.CompanyRepo;

import ke.co.turbosoft.tt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DbSetup {
	
	@Autowired
	private CompanyRepo repo;

    @Autowired
    private UserRepo userRepo;
	
	@PostConstruct
	public void init(){
		
		Company com1=new Company();
		com1.setCompanyName("Turbosoft Tech Solutions");
		repo.save(com1);
		
		Company com2=new Company();
		com2.setCompanyName("Espa Business Solutions");
		repo.save(com2);

        User kip = new User();
        kip.setUsername("kfaraji");
        kip.setPassword("pass123");
        kip.setEmail("ktonym@gmail.com");
        kip.setFirstName("Faraji");
        kip.setLastName("Kipkoech");
        kip.setAdminRole("Y".charAt(0));
        userRepo.save(kip);

        User esther = new User();
	    esther.setUsername("mwessy");
        esther.setPassword("pass1234");
        esther.setEmail("mwessy08@gmail.com");
        esther.setFirstName("Esther");
        esther.setLastName("Kipkoech");
        esther.setAdminRole("N".charAt(0));
        userRepo.save(esther);


	}

}

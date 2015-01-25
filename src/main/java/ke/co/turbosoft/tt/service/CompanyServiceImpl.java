package ke.co.turbosoft.tt.service;

import ke.co.turbosoft.tt.entity.Company;
import ke.co.turbosoft.tt.entity.User;
import ke.co.turbosoft.tt.repo.CompanyRepo;
import ke.co.turbosoft.tt.vo.Result;
import ke.co.turbosoft.tt.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ktonym on 1/25/15.
 */
@Transactional
@Service("companyService")
public class CompanyServiceImpl extends AbstractService implements CompanyService {

    @Autowired
    protected CompanyRepo companyRepo;

    public CompanyServiceImpl() {
        super();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<Company> store(Integer idCompany, String companyName, String actionUsername) {

        User actionUser=userRepo.findOne(actionUsername);

        if(!actionUser.isAdmin()){
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        Company company;

        if(idCompany == null){
            company = new Company();
        } else {
            company = companyRepo.findOne(idCompany);

            if(company==null){
                return ResultFactory.getFailResult("Unable to find company with ID=" +idCompany);
            }

        }

        company.setName(companyName);

        companyRepo.save(company);

        return ResultFactory.getSuccessResult(company);

    }

    @Transactional(readOnly = false,propagation =  Propagation.REQUIRED)
    @Override
    public Result<Company> remove(Integer idCompany, String actionUsername) {
        User actionUser = userRepo.findOne(actionUsername);

        if(!actionUser.isAdmin()){
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        if(idCompany == null){
            return ResultFactory.getFailResult("Unable to remove " +
                    "Company [null idCompany]");
        }

        Company company = companyRepo.findOne(idCompany);

        if (company == null){
            return ResultFactory.getFailResult("Unable to load Company for removal with idCompany=" + idCompany);
        }  else {

            if(company.getProjects()==null ||
                    company.getProjects().isEmpty()){
                companyRepo.delete(company);

                String msg = "Company " + company.getName()
                        + " was deleted by " + actionUsername;
                logger.info(msg);
                return ResultFactory.getSuccessResult(msg);

            }   else {

                return ResultFactory.getFailResult("Company has projects assigned and could not be deleted");

            }

        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<Company> find(Integer idCompany, String actionUsername) {
        if(isValidUser(actionUsername)){
            Company company = companyRepo.findOne(idCompany);
            return ResultFactory.getSuccessResult(company);
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<List<Company>> findAll(String actionUsername) {
        if(isValidUser(actionUsername)){
            List<Company> companyList = companyRepo.findAll();
            return ResultFactory.getSuccessResult(companyList);
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }
}

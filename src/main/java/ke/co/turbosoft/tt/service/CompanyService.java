package ke.co.turbosoft.tt.service;


import java.util.List;

import ke.co.turbosoft.tt.entity.Company;
import ke.co.turbosoft.tt.vo.Result;

public interface CompanyService {

	
	public Result<Company> store(
			Integer idCompany,
			String companyName,
			String actionUsername);
	
	public Result<Company> remove(Integer idCompany, String actionUsername);
	public Result<Company> find(Integer idCompany, String actionUsername);
	public Result<List<Company>> findAll(String actionUsername); 

}

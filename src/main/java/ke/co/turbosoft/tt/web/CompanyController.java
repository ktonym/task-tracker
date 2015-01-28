package ke.co.turbosoft.tt.web;

import ke.co.turbosoft.tt.entity.Company;
import ke.co.turbosoft.tt.entity.User;
import ke.co.turbosoft.tt.service.CompanyService;
import ke.co.turbosoft.tt.service.ProjectService;
import ke.co.turbosoft.tt.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;

import static ke.co.turbosoft.tt.web.SecurityHelper.getSessionUser;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

    @Autowired
    protected CompanyService companyService;
    @Autowired
    protected ProjectService projectService;

	@RequestMapping(value = "/find", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
	public String find(
            @RequestParam(value = "idCompany", required = true) Integer idCompany,
            HttpServletRequest request){

        User sessionUser = getSessionUser(request);

        if(sessionUser == null ){
            return getJsonErrorMsg("User is not logged on");
        }

        Result<Company> ar = companyService.find(idCompany, sessionUser.getUsername());

        if(ar.isSuccess()){

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }

	}

    @RequestMapping(value = "/store", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String store(@RequestParam(value = "data", required = true) String jsonData, HttpServletRequest request){

        User sessionUser = getSessionUser(request);

        if(sessionUser==null){
            return getJsonErrorMsg("User is not logged on");
        }

        JsonObject jsonObj = parseJsonObject(jsonData);

        Result<Company> ar = companyService.store(getIntegerValue(jsonObj.get("idCompany")),
                jsonObj.getString("companyName"),
                sessionUser.getUsername());

        if (ar.isSuccess()){

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }


}

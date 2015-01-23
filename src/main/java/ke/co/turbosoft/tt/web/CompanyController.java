package ke.co.turbosoft.tt.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CompanyController {
	
	@RequestMapping("/index")
	public String index(){
		return "hello";
		
	}

}

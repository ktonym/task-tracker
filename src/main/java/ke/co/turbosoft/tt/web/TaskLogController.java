package ke.co.turbosoft.tt.web;

import ke.co.turbosoft.tt.entity.TaskLog;
import ke.co.turbosoft.tt.entity.User;
import ke.co.turbosoft.tt.service.TaskLogService;
import ke.co.turbosoft.tt.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ke.co.turbosoft.tt.web.SecurityHelper.getSessionUser;

/**
 * Created by akipkoech on 1/29/15.
 */
@Controller
@RequestMapping("/taskLog")
public class TaskLogController extends AbstractController {

    static final DateTimeFormatter DATE_FORMAT_yyyyMMdd =  DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    protected TaskLogService taskLogService;

    @InitBinder
    public void initBinder(WebDataBinder binder){

        binder.registerCustomEditor(LocalDate.class,new LocalDateEditor(DATE_FORMAT_yyyyMMdd,true));

    }

    @RequestMapping(value = "/find",method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "idTaskLog", required = true) Integer idTaskLog,
            HttpServletRequest request){
        User sessionUser = getSessionUser(request);

        Result<TaskLog> ar = taskLogService.find(idTaskLog, sessionUser.getUsername());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }


    @RequestMapping(value = "/store",method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String store(
            @RequestParam(value = "data",required = true) String jsonData,
            HttpServletRequest request) throws ParseException{

        User sessionUser = getSessionUser(request);

        JsonObject jsonObj = parseJsonObject(jsonData);

        String dateVal = jsonObj.getString("taskLogDate");

        Result<TaskLog> ar = taskLogService.store(
                getIntegerValue(jsonObj.get("idTaskLog")),
                getIntegerValue(jsonObj.get("idTask")),
                jsonObj.getString("username"),
                jsonObj.getString("taskDescription"),
                LocalDate.parse(dateVal, DATE_FORMAT_yyyyMMdd),
                jsonObj.getInt("taskMinutes"),
                sessionUser.getUsername());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        } 
    }

    @RequestMapping(value = "/remove",method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String remove(
            @RequestParam(value = "data",required = true) String jsonData,
            HttpServletRequest request){

        User sessionUser=getSessionUser(request);

        JsonObject jsonObj = parseJsonObject(jsonData);

        Result<TaskLog> ar = taskLogService.remove(
                getIntegerValue(jsonObj.get("idTaskLog")),
                sessionUser.getUsername());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/findByUser",method = RequestMethod.GET,produces = {"application/json"})
    @ResponseBody
    public String findByUser(@RequestParam(value = "username",required = true) String username,
                             @RequestParam(value = "startDate", required = true) LocalDate startDate,
                             @RequestParam(value = "endDate", required = true) LocalDate endDate,
                             HttpServletRequest request){

        User sessionUser=getSessionUser(request);

        Result<List<TaskLog>> ar = taskLogService.findByUser(
                username,
                startDate,
                endDate,
                sessionUser.getUsername());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }


}

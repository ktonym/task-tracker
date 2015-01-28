package ke.co.turbosoft.tt.entity;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Created by akipkoech on 1/28/15.
 */
public interface JsonItem  {

    public JsonObject toJson();
    public void addJson(JsonObjectBuilder builder);

}

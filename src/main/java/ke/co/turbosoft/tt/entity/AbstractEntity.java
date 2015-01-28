package ke.co.turbosoft.tt.entity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.Serializable;

/**
 * Created by akipkoech on 1/28/15.
 */
public abstract class AbstractEntity implements JsonItem,Serializable {

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        addJson(builder);
        return builder.build();
    }


}

package discovery.model;

import com.google.common.base.MoreObjects;
import lombok.ToString;
import org.springframework.stereotype.Component;


public @ToString class Discover {

    private String key;
    private String value;

    public Discover() {
    }

    public Discover(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toStringHelper(){
        return MoreObjects.toStringHelper(new Discover()).add(key,value).toString();
    }





}

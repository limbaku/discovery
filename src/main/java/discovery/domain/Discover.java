package discovery.domain;

import com.google.common.base.MoreObjects;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public @ToString class Discover {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String key;
    private String value;

    public Discover() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

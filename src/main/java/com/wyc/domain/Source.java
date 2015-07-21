package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "source")
public class Source {
    @Id
    private String id;
    @Column(name = "type")
    private String type;
    @Column(name = "price")
    private Long price;
    @Column(name = "url")
    private String url;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}

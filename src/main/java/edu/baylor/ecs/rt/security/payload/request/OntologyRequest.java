package edu.baylor.ecs.rt.security.payload.request;

import java.util.Date;

public class OntologyRequest {
    private String userId;

    private String name;

    private Date createTime;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

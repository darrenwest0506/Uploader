package com.west.util;

import java.io.Serializable;

public class IdHolder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    public IdHolder() {

    }

    public IdHolder(String id) {
        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

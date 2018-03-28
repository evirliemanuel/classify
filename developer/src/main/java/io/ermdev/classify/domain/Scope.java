package io.ermdev.classify.domain;

public class Scope {

    public Long id;
    private String name;

    public Scope() {
        id = 0L;
        name = "";
    }

    public Scope(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

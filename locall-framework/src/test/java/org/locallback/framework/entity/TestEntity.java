package org.locallback.framework.entity;

import java.util.List;
import java.util.Map;

public class TestEntity {

    private int id;
    private String name;

    private List<String> list;

    private Map<String, Integer> map;

    public TestEntity() {
    }

    public TestEntity(int id, String name, List<String> list, Map<String, Integer> map) {
        this.id = id;
        this.name = name;
        this.list = list;
        this.map = map;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "TestEntity {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", list = " + list +
                ", map = " + map +
                '}';
    }
}

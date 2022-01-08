package com.example.nhahangamthuc;

public class User {
    int id;
    String name;
    int sdt;
    User() {}

    public User(int id, String name, int sdt) {
        this.id = id;
        this.name = name;
        this.sdt = sdt;
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

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sdt=" + sdt +
                '}';
    }
}

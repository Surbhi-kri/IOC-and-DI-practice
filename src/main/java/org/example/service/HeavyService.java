package org.example.service;

public class HeavyService {

    public HeavyService() {
        System.out.println("HeavyService constructor");
    }

    public void init() {
        System.out.println("HeavyService init");
    }

    public void destroy() {
        System.out.println("HeavyService destroy");
    }

    public String work() {
        return "heavy work";
    }
}

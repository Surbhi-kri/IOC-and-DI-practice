package org.example.service;

public class BarService {
    private final Foo foo;

    public BarService(Foo foo) {
        this.foo = foo;
    }

    public void print() {
        System.out.println("Injected bean: " + foo.name());
    }
}

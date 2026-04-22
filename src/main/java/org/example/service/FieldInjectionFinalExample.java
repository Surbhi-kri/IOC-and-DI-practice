package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;

public class FieldInjectionFinalExample {

    // This intentionally demonstrates why final field injection is problematic.
    @Autowired
    private final Foo foo = null;

    public Foo getFoo() {
        return foo;
    }
}

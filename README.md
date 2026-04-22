# IoC and DI Practice

This project demonstrates basic Spring IoC and Dependency Injection concepts with a small runnable example.

## Project Overview

The example uses Spring's `ApplicationContext` with Java-based configuration to create and wire beans.

Implemented concepts:

- IoC through Spring-managed bean creation
- Dependency Injection with constructor injection
- `@Qualifier` for resolving multiple beans of the same type
- `@Lazy` for delaying heavy bean creation
- Bean lifecycle methods with `initMethod` and `destroyMethod`

## Files

- `src/main/java/org/example/App.java`
- `src/main/java/org/example/config/AppConfig.java`
- `src/main/java/org/example/service/Foo.java`
- `src/main/java/org/example/service/FooImpl.java`
- `src/main/java/org/example/service/AnotherFooImpl.java`
- `src/main/java/org/example/service/BarService.java`
- `src/main/java/org/example/service/HeavyService.java`
- `src/main/java/org/example/service/FieldInjectionFinalExample.java`

## Answers to Questions

### 1. What is the ApplicationContext?

`ApplicationContext` is Spring's IoC container. It is responsible for:

- creating beans
- injecting dependencies into beans
- managing bean lifecycle
- reading configuration metadata
- making beans available for lookup and use

In this project, `AnnotationConfigApplicationContext` loads `AppConfig` and manages all defined beans.

### 2. What are the tradeoffs of different approaches to injecting beans?

There are three common approaches:

#### Constructor Injection

Advantages:

- best choice for required dependencies
- makes classes easier to test
- supports immutability with `final` fields
- ensures object is created in a valid state

Disadvantages:

- constructors become longer if too many dependencies are added

#### Setter Injection

Advantages:

- useful for optional dependencies
- allows dependency changes after object creation

Disadvantages:

- object can exist in an incomplete state
- easier to forget setting required dependencies

#### Field Injection

Advantages:

- shortest syntax

Disadvantages:

- hard to test without Spring
- hides dependencies
- does not work well with `final` fields
- generally not recommended in production code

In this project, constructor injection is used in `BarService`.

### 3. Why do we need to use `@Qualifier` when multiple of the same type are defined?

If Spring finds more than one bean of the same type, it does not know which one should be injected.

In this project:

- `fooBean()` returns a `Foo`
- `anotherFooBean()` also returns a `Foo`

So when Spring needs a `Foo`, there is ambiguity. `@Qualifier("fooBean")` tells Spring exactly which bean to inject.

Without `@Qualifier`, Spring usually throws `NoUniqueBeanDefinitionException`.

### 4. How to avoid loading of heavy beans on startup and decrease startup time?

Use lazy initialization.

In this project, `HeavyService` is marked with `@Lazy`, so it is not created when the application context starts. It is created only when `context.getBean(HeavyService.class)` is called.

This improves startup time for beans with:

- expensive object creation
- cache loading
- network connections
- heavy initialization logic

### 5. What are Spring lifecycle stages and methods?

Basic bean lifecycle:

1. Spring creates the bean instance
2. Spring injects dependencies
3. initialization logic runs
4. bean is ready for use
5. when context closes, destruction logic runs

Common lifecycle methods:

- constructor
- `initMethod`
- `destroyMethod`
- `@PostConstruct`
- `@PreDestroy`
- `InitializingBean.afterPropertiesSet()`
- `DisposableBean.destroy()`

In this project, `HeavyService` uses:

- `init()` as `initMethod`
- `destroy()` as `destroyMethod`

## Exercises and Observations

### Exercise 1

Create interface `Foo` and an implementation. Since we can reference the object both via implementation class and interface, try defining beans with both return types and inject them as a dependency in another bean.

Implemented in:

- `Foo`
- `FooImpl`
- `AnotherFooImpl`
- `AppConfig`

Observation:

If a bean is declared with method return type `Foo`, then Spring primarily registers it by that declared type. Even if the actual object is `FooImpl`, injecting it as `FooImpl` may not behave as expected in all cases.

Example:

- `fooBean()` returns `Foo`
- `fooImplBean()` returns `FooImpl`

This shows that the declared bean type matters, not just the concrete object created inside the method.

### Exercise 2

Try using `@Autowired` injection on a field that is `final`.

Implemented in:

- `FieldInjectionFinalExample`

Observation:

Using `@Autowired` on a `final` field is problematic because field injection happens after object construction, while `final` fields should be assigned during construction. This is one reason constructor injection is preferred.

### Exercise 3

Try to inject a correct bean without using `@Qualifier`.

Observation:

This fails when multiple beans of the same type exist. In this project there are two beans of type `Foo`, so injecting just `Foo` without `@Qualifier` leads to ambiguity.

Ways to solve it:

- use `@Qualifier`
- use `@Primary`
- inject a collection such as `List<Foo>`

## How This Project Demonstrates IoC and DI

IoC means object creation is controlled by the framework instead of manual `new` calls throughout business code.

DI means required dependencies are passed into a class from outside rather than created inside the class.

In this project:

- Spring creates `BarService`
- Spring injects a `Foo` implementation into `BarService`
- Spring manages when `HeavyService` is created and destroyed

## How to Run

Run the main class:

- `org.example.App`

Or from terminal:

```bash
mvn test
```

If you want to run the application directly:

```bash
mvn -q exec:java -Dexec.mainClass=org.example.App
```

## Expected Behavior

When the application starts:

- the application context is created
- `BarService` receives a `Foo` bean
- `HeavyService` is not created immediately because it is lazy

When `HeavyService` is requested:

- constructor runs
- init method runs
- bean becomes available

When the context closes:

- destroy method runs

## Conclusion

This project shows that:

- Spring `ApplicationContext` is the central IoC container
- DI helps create loosely coupled and testable code
- constructor injection is usually the best approach
- `@Qualifier` resolves ambiguity between same-type beans
- `@Lazy` helps reduce startup cost
- Spring supports bean lifecycle management

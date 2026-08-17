# cacheProxy

`cacheProxy` is a small Java library that adds result caching to service objects through a dynamic proxy built with `cglib`.

## What It Does

The project wraps a service implementation and intercepts method calls. When a method is marked as cacheable, repeated calls with the same arguments return the cached value instead of recalculating the result.

The cache can store values in two ways:

- in JVM memory
- on disk through Java serialization

## Main Components

- `cacheProxy.proxy.CacheProxy` creates a proxy around a target service
- `cacheProxy.proxy.Interceptor` applies the caching logic
- `cacheProxy.annotations.CacheableElement` marks cacheable classes or methods
- `cacheProxy.annotations.CacheStore` selects the storage mode: `JVM` or `DISK`

## Example Behavior

- method-level caching is demonstrated in `ServiceImpl`
- class-level caching is demonstrated in `ServiceImplCommon`
- `Demo` shows how to create a proxy and invoke cached methods

## Build

Compile the project with Maven:

```bash
mvn clean package
```

## Run

You can run the demo entry point after building the project:

```bash
mvn exec:java -Dexec.mainClass=cacheProxy.Demo
```

Before running the demo, update the root cache directory inside `src/main/java/cacheProxy/Demo.java` so it points to a valid folder on your machine.

## Notes

- disk caching requires cached values to be serializable
- the proxy implementation uses subclassing via `cglib`
- the repository currently does not contain automated tests

## Verification

The repository can be checked with:

```bash
mvn test
```

At the moment this succeeds as a build verification step and reports that there are no tests to run.

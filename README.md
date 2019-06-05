# Foobar
[![Build Status](https://travis-ci.org/jarslab/skippy.svg?branch=master)](https://travis-ci.org/jarslab/skippy)

Skippy is a Java library for easy exception transforming into detailed messages by annotating
classes and methods.

## Getting started

Use the maven.

```
<dependency>
    <groupId>com.jarslab.skippy</groupId>
    <artifactId>skippy-spi</artifactId>
    <version>1.0-RC</version>
</dependency>
```

## Usage

```java
@ExceptionMapping(exceptions = Exception.class, errorDetails = OverallErrorDetails.class)
private class SuspiciousClass
{
    @ExceptionMapping(exceptions = IllegalStateException.class,
                      errorDetails = IllegalStateErrorDetails.class)
    public void suspiciousMethod()
    {
      ...
    }
}

final ClassExceptionMapperProvider mapperProvider =
    new ClassExceptionMapperProvider(SuspiciousClass.class);
try {
    suspiciousClass.suspiciousMethod()
} catch (Exception e) {
    final ExceptionMapper mapper =
        mapperProvider.getMapper(SuspiciousClass.class.getMethod("notAnnotatedMethod"));
    final boolean mappable = mapper.test(exception);
    if (mappable) {
        final ErrorDetails errorDetails = mapper.apply(exception);
    }
}
```

## License
[Apache License](https://www.apache.org/licenses/LICENSE-2.0.txt)
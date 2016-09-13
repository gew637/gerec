![Build Status](https://img.shields.io/travis/vanillasource/jaywire.svg)
![Published Version](https://img.shields.io/maven-central/v/com.vanillasource.gerec/gerec-parent.svg)

Gerec, the Generic REST HTTP Client
===================================

A small wrapper over HTTP Clients to support building a proper RESTful
(Hypertext-enabled) API client. Specific features:

* Media-type centered
* Following links made easy
* Easy cache control
* Conditional requests
* Very simple and minimal API

### Getting the library

_Not yet published to maven central._

### The basics

In Gerec, everything revolves around the concept of a `ResourceReference`. This is
a serializable object that can be used to manipulate the remote resource it points to.
These references are not instantiated by the user, as often is the case with other
frameworks, but they are created when following links. The only exception is the very
first reference, created from a bookmark to some entry point into a web of resources.

The reference contains the usualy method to manipulate the resource it points to:
* `GET`
* `POST`
* `PUT`
* `DELETE`
* `HEAD`

The result of any of the HTTP methods will result in a `Response`. A `Response` can contain
a response body, the format of which is described in a `MediaType`. A `Response` might also
contain additional information for making subsequent requests, such as links (additional
`ResourceReference` objects) or conditions.

### Some random examples

Simple `GET` requests are made the following way:

```java
String body = reference.get(MediaTypes.TEXT_PLAIN).getContent();
```

Media types can be freely created for any representation, as they should be. So `GET`ting
a complex object looks like this:

```java
Person person = reference.get(Person.TYPE).getContent();
```

The given media type will be properly communicated to the server, using the normal HTTP
content negotiation mechanisms.

You can easily make conditional requests. The followin example gets a mutable _Person_ object, modifies it,
then tries to update the server, but only if it did not change since it was requested. (Also
known as optimistic locking):

```java
Response<Person> personResponse = currentUserReference.get(Person.TYPE);
Person person = personResponse.getContent();
person.setName("New Name");
currentUserReference.put(person, personResponse.ifMatch());
```

The `ifMatch()` condition above will use the `ETag` of the response to make the `PUT` conditional
upon the person not having been changed since it was requested.

### Documentation

Please visit the [Gerec Wiki](https://github.com/vanillasource/gerec/wiki) for more information.


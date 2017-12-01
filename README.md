# Ile dasz? - Web Application.

Application works like OLX or Allegro, the only difference is the fact that we do not know the value which we want to get. We put things up on sale and we get variable bids.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them

* [Postgresql](https://www.postgresql.org/) - database
* [node](https://nodejs.org/en/) - JavaScript runtime built
* [npm](https://www.npmjs.com/) - Package manager
* [Java 8](http://www.oracle.com/technetwork/java/index.html)
* [Maven](https://maven.apache.org/) - Dependency Management

### Installing

Utworzenie bazy:

```
sudo -u postgres createuser --interactive $USER
sudo -u postgres createdb base 
psql -d base
ALTER USER postgres with password 'postgres@321';
```

Uruchominie aplikacji

```
mvn install && mvn --projects backend spring-boot:run
```

Aby uruchomić frontend na serwerze z hotswapem, przechodzimy do katalogu frontend i wywołujemy:
```
npm run dev
```
## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://spring.io/guides/gs/spring-boot/)
* [Spring](https://spring.io/)

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

* **Wiktor Korol** - [wkorol](https://github.com/wkorol)
* **Michał Fierek** - [whoamix](https://github.com/whoamix)
* **Mariusz Tkaczyk** - [mtkaczyk](https://github.com/mtkaczyk)
* **Dawid Ćwik** - [dcwik96](https://github.com/dcwik96)
* **Łukasz Skibski** - [lukasz-skibski](https://github.com/lukasz-skibski)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


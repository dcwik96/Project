# Ile dasz? - Web Application.

Application works like OLX or Allegro, the only difference is the fact that we do not know the value which we want to get. We put things up on sale and we get variable bids.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

* Postgresql
* node
* npm
* java
* maven

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

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc


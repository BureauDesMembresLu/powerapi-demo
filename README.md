# PowerAPI demo

[travis-badge]: https://img.shields.io/travis/BureauDesMembresLu/powerapi-demo.svg
[travis]: https://travis-ci.org/BureauDesMembresLu/powerapi-demo
[sonarc-badge]: https://img.shields.io/sonar/https/sonarqube.com/org.keyboardplaying:powerapi-demo/coverage.svg
[sonarc]: https://sonarqube.com/overview/coverage?id=org.keyboardplaying:powerapi-demo
[sonarq-badge]: https://img.shields.io/sonar/https/sonarqube.com/org.keyboardplaying:powerapi-demo/tech_debt.svg
[sonarq]: https://sonarqube.com/overview/debt?id=org.keyboardplaying:powerapi-demo
[issues-badge]: https://img.shields.io/github/issues-raw/BureauDesMembresLu/powerapi-demo.svg
[issues]: https://github.com/BureauDesMembresLu/powerapi-demo/issues
[waffle]: https://waffle.io/BureauDesMembresLu/powerapi-demo
[licens-badge]: https://img.shields.io/github/license/BureauDesMembresLu/powerapi-demo.svg
[licens]: http://www.apache.org/licenses/LICENSE-2.0

[![Build status][travis-badge]][travis]
[![Test coverage][sonarc-badge]][sonarc]
[![Technical debt][sonarq-badge]][sonarq]
[![Issues][issues-badge]][waffle]
[![License: Apache 2.0][licens-badge]][licens]

This project was designed as a proof-of-concept to quickly demonstrate the possibilities of using PowerAPI and the importance of coding properly.

This project is not related to PowerAPI but only makes use of the tool.

## Project structure

The project is divided into two main modules:

- `powerapi-demo-rest-server`, the PCPDb (Poorly-Coded People Database), is a simple REST server that uses various non-optimized implementations to return the same results.
- `powerapi-demo-monitor-proxy` runs as a local server that proxifies calls to the monitored server and adds the monitoring information to the result from the original call. It comes with a web interface to show the code's performance.

Every behavior of each project can be tuned in the related `application.yml`.

## Running

1. Ensure the database is available (see [Database](#database))
2. Start `powerapi-demo-rest-server` (either with `mvn spring-boot:run` or `java -jar`)
3. Start `powerapi-demo-monitor-proxy` (either with `mvn spring-boot:run` or `java -jar`)
4. Head over [localhost:8080](http://localhost:8080) and start playing

## Database

The PCPDb relies on a Mongo database. The database must be available. The default configuration tries to reach it at `localhost:27017` 

### Running MongoDB as a Docker container

If you wish to run Mongo as a Docker container, here is how you will need to configure it to play nice with the project.
If you use a native installation, think of updating the `application.yml` file accordingly.

```bash
# Create the container to run at localhost
docker create --name pcpdb-mongo -p 27017:27017 [-v <path to db>:/data/db] mongo

# In Windows, it may be required to add an exception to the firewall
netsh advfirewall firewall add rule name="Open mongod port 27017" dir=in action=allow protocol=TCP localport=27017

# Start and stop the container
docker start pcpdb-mongo
docker stop pcpdb-mongo
```

### Inject people into the database

The `powerapi-demo-data-injector` creates fake data and inserts it into the database used by the PCPDb.

It runs as a command-line interface. Just run the application and follow the on-screen instructions to wipe or fill the database.

## Contributing

### Web interface development

Having to start the whole applications to develop web interfaces is not really efficient.
In order to avoid this, a development with a Sinon server responding with fake data is available.
To start it, head into the `powerapi-demo-monitor-proxy`.

```bash
# Install all dependencies
yarn install

# Run the dev server via Gulp
gulp serve:dev --gulpfile gulp
# Run using NPM
npm run dev
```

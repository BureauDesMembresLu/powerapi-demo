
# PowerAPI demo

[travis-badge]: https://img.shields.io/travis/BureauDesMembresLu/powerapi-demo.svg
[travis]: https://travis-ci.org/BureauDesMembresLu/powerapi-demo
[sonarc-badge]: https://img.shields.io/sonar/https/sonarqube.com/org.keyboardplaying:powerapi-demo/coverage.svg
[sonarc]: https://sonarqube.com/component_measures/domain/Coverage?id=org.keyboardplaying:powerapi-demo
[sonarq-badge]: https://img.shields.io/sonar/https/sonarqube.com/org.keyboardplaying:powerapi-demo/tech_debt.svg
[sonarq]: https://sonarqube.com/project/issues?id=org.keyboardplaying:powerapi-demo&resolved=false
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
2. Start `powerapi-demo-rest-server` (use `mvn spring-boot:run -f powerapi-demo-rest-server/pom.xml`)
3. Start `powerapi-demo-monitor-proxy` (use `mvn spring-boot:run -f powerapi-demo-monitoring-proxy/pom.xml`)
4. Head over [localhost:8080](http://localhost:8080) and start playing

> :warning: Be sure to use `mvn spring-boot:run` to launch the monitoring proxy.
> The SIGAR library won't be loaded otherwise. 

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

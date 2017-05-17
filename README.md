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

## Running MongoDB as a Docker container

The pcol (Poorly-Coded Movie Database) used as a demo project uses a Mongo database.

If you wish to run Mongo as a Docker container, here is how you will need to configure it to play nice with the project.
If you use a native installation, think of updating the `application.yml` file accordingly.

```bash
# Define a network in order to be able to specify IP
docker network create --subnet=172.18.0.0/16 pcol-net

# Create the container
docker create --name pcol-mongo --net pcol-net --ip 172.18.0.42 -p 27017:27017 -v <path to db>:/data/db mongo

# Start and stop the container
docker start pcol-mongo
docker stop pcol-mongo
```

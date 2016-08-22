[![Build Status](https://travis-ci.org/influx-gps/JConductor.svg?branch=master)](https://travis-ci.org/influx-gps/JConductor)

## Docker
To prepare docker image run from JCondutor directory: <br />
`$ mvn package docker:build` <br />
To run app with docker use: <br />
`$ docker run -p 8080:8080 -t springio/jconductor` <br />

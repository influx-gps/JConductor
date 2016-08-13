## Docker
To prepare docker image run from JCondutor directory: <br />
`$ mvn package docker:build` <br />
To run app with docker use: <br />
`$ docker run -p 8080:8080 -t springio/jconductor` <br />
Running mongo db and exposing its port: <br />
`docker run -p 127.0.0.1:$HOSTPORT:$CONTAINERPORT --name CONTAINER -t someimage`

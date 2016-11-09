[![Build Status](https://travis-ci.org/influx-gps/JConductor.svg?branch=master)](https://travis-ci.org/influx-gps/JConductor)

## Docker
To prepare docker image run from JCondutor directory: <br />
`$ mvn package docker:build` <br />
To run app with docker use: <br />
`$ docker run -p 8080:8080 -t springio/jconductor` <br />

## User management
To register user you can simply use: <br />
`curl -X POST <app_url>/register -H "Content-Type: application/json" -d '{"username": "<username>", "password": "<passw>", "email": "<email>"}'`
<br /><br />
To send track initializing request use: <br />
`curl -X POST -u <username>:<passw> <app_url>/rest/track -H "Content-Type: application/json" -d '{"accountId": "<x>", "locations": {"latitude": <x>, "longitude": <x>, "time": <x>}, "startTime": <x>,  "distance": 0}'
`
<br /><br />
To add location: <br />
`curl -X POST -u <username>:<passw> <app_url>/rest/track/<track_id> -H "Content-Type: application/json" -d '{"latitude": <x>, "longitude": <x>, "time": <x>}'`
<br /><br />
To finish track: <br />
`curl -X POST -u <username>:<passw> <app_url>/rest/track/<track_id>?finished=true -H "Content-Type: application/json" -d '{"latitude": <x>, "longitude": <x>, "time": <x>}'`
<br /><br />

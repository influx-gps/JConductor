#User register:
<br />

POST
<br />
`{
   "username":"<username>",
   "password":"<passw>",
   "email":"<email>"
}`
Response
<br />
`{
   "id": "5825ebf221d81816d2dcbb3c",
   "username":"username"
   "password":"password",
   "email":"email@email.com"
}`
<br />

#Start new track:
<br />

POST
<br />
`{
   "accountId":"<x>",
   "locations":{
      "latitude":<x>,
      "longitude":<x>,
      "time":<x>
   },
   "startTime":<x>,
   "distance":0
}`
<br />

#Add data to track:
<br />

POST
<br />
`{
   "latitude":<x>,
   "longitude":<x>,
   "time":<x>
}`


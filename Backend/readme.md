install express
install axios
to run server :- npx nodemon

in services i have created a file " maps.services".js 
function seving this file:

1.getAddressCoordinate
the response i am getting for this
 {
  "ltd": 28.6129,
  "lng": 77.2295
}
i have used "nominatim.openstreetmap"


2.getDistanceTime
output i am getting on check 
{
  "distance_km": "2.54",
  "duration_min": 4
}

3.getAutoCompleteSuggestions
output i am getting 
const url = `https://nominatim.openstreetmap.org/search?format=json
    &q=${encodeURIComponent(input)}
    &limit=5  limits of suggestion 
    &countrycodes=in  only india 
    &viewbox=76.84,28.88,77.35,28.40  limit to delhi 
    &bounded=1`;
[
  "Connaught Place, Chanakya Puri Tehsil, New Delhi, Delhi, 110001, India",
  <!-- "Connaught Peak Point, Mahabaleshwar, Satara, Maharashtra, 412806, India" -->
]
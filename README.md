# api-metrics
The operations for this service are available in:
http://[host]:[port]/swagger-ui.html#!/transaction-controller/ 

There are two possibles:

-> POST /metric/transactions - Add a new Transaction

Example:
curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -d '{ \ 
   "amount": 37.5, \ 
   "timestamp": 1501031914744 \ 
 }' 'http://localhost:8080/metric/transactions'
 
-> GET /metric/statistics - Returns the statistic based of the transactions of the last 60 seconds
 
Example:
curl -X GET --header 'Accept: application/json' 'http://localhost:8080/metric/statistics'

This project was created with:
Java 1.8
Spring-Boot
Swagger2
Google-Guava
Mockito

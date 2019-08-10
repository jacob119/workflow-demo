## Return to welcome message.
curl -v localhost:12000/

## startAll
curl -v localhost:12000/scheduler/startAll


## stopAll
curl -v localhost:12000/scheduler/stopAll


## getList
curl -v localhost:12000/scheduler/list

## start by name
curl -v localhost:12000/scheduler/start?name='etl'

## stop by name
curl -v localhost:12000/scheduler/stop?name='etl'
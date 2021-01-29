# dynamo
dynamoアクセス関連



## DynamoDBローカル

  DynamoDBをローカル実行できる物があるので使う  
  ローカルDBをにアクセスする場合、エンドポイントを指定する  
  cliは以下。(Javaの場合もエンドポイント指定する)
  ```
  aws dynamodb list-tables --endpoint-url http://localhost:8000
  ```

https://docs.aws.amazon.com/ja_jp/amazondynamodb/latest/developerguide/DynamoDBLocal.UsageNotes.html


### docker
```
docker-compose up -d
aws dynamodb list-tables --endpoint-url http://localhost:8000
```


### create-table
```
aws dynamodb create-table \
    --table-name Music \
    --attribute-definitions \
        AttributeName=Artist,AttributeType=S \
        AttributeName=SongTitle,AttributeType=S \
    --key-schema \
        AttributeName=Artist,KeyType=HASH \
        AttributeName=SongTitle,KeyType=RANGE \
--provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=5 \
    --endpoint-url http://localhost:8000
```
```
aws dynamodb describe-table --table-name Music --endpoint-url http://localhost:8000 | grep TableStatus
```

### put-item
```
aws dynamodb put-item \
--table-name Music  \
--item \
    '{"Artist": {"S": "No One You Know"}, "SongTitle": {"S": "Call Me Today"}, "AlbumTitle": {"S": "Somewhat Famous"}, "Awards": {"N": "1"}}' \
    --endpoint-url http://localhost:8000


aws dynamodb put-item \
    --table-name Music \
    --item \
    '{"Artist": {"S": "Acme Band"}, "SongTitle": {"S": "Happy Day"}, "AlbumTitle": {"S": "Songs About Life"}, "Awards": {"N": "10"} }' \
    --endpoint-url http://localhost:8000
```

### scan
全件取得
```
aws dynamodb scan --table-name Music --endpoint-url http://localhost:8000
```

件数取得  
```
aws dynamodb scan --table-name Music --select COUNT --endpoint-url http://localhost:8000
```

### get-item
```
aws dynamodb get-item --consistent-read \
    --table-name Music \
    --key '{ "Artist": {"S": "Acme Band"}, "SongTitle": {"S": "Happy Day"}}' \
    --endpoint-url http://localhost:8000
```




https://docs.aws.amazon.com/ja_jp/amazondynamodb/latest/developerguide/JavaDocumentAPIWorkingWithTables.html


# [Return to the tutorial](../README.md)
# [Returns a list of built-in functions](../builtFunc.md)
***

# nget
## Introduce
Send a `get` request
## Parameters
| Name          | Type                    | Description                   | Optional |
|:--------------|:------------------------|:------------------------------|:---------|
| url           | String                  | URL to be accessed            |          |
| params        | Dictionary              | URl parameters                | √        |
| headers       | Dictionary              | Headers                       | √        |
| cookies       | Dictionary              | Cookies                       | √        |
| data          | Dictionary, JSON string | Request body                  | √        |
| allow_rediect | Boolean                 | Is redirection allowed        | √        |
| timeout       | Number                  | Access timeout (milliseconds) | √        |  
## Return value
`Dictionary`

| Name        | Description                              | Type       |
|:------------|:-----------------------------------------|:-----------|
| url         | The URL after the visit                  | String     |
| content     | ByteString of response content           | ByteString |
| html        | The UTF-8 string of the response content | String     |
| headers     | Response Headers                         | Dictionary |
| cookies     | Response Cookies                         | Dictionary |
| status_code | Status Code                              | Number     |
## Example
```bullet
headers := {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36"
}
params := {
    "name": "Bullet"
}

rsp := nget( "https://www.baidu.com/", headers=headers, params=params )

println( rsp['url'] )
println( rsp['html'] )
```

# npost
Send a `post` request
## Parameters
| Parameter     | Type                    | Description                   | Optional |
|:--------------|:------------------------|:------------------------------|:---------|
| url           | String                  | URL to be accessed            |          |
| params        | Dictionary              | URl parameters                | √        |
| headers       | Dictionary              | Headers                       | √        |
| cookies       | Dictionary              | Cookies                       | √        |
| data          | Dictionary, JSON string | Request body                  | √        |
| allow_rediect | Boolean                 | Is redirection allowed        | √        |
| timeout       | Number                  | Access timeout (milliseconds) | √        | 
## Return value
`Dictionary`

| Name        | Description                              | Type       |
|:------------|:-----------------------------------------|:-----------|
| url         | The URL after the visit                  | String     |
| content     | ByteString of response content           | ByteString |
| html        | The UTF-8 string of the response content | String     |
| headers     | Response Headers                         | Dictionary |
| cookies     | Response Cookies                         | Dictionary |
| status_code | Status Code                              | Number     |
## Example
```bullet
headers := {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36"
}
data := "{\"name\":\"Bullet\"}"
rsp := npost( "https://www.baidu.com/", headers=headers, data=data )

println( rsp['url'] )
println( rsp['html'] )
```
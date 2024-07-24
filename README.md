# short-url
## Url shortener project

Simple url shortening project, give us long real world url, and we'll give you short version back and vv.

Complete working example of request on localhost and response in json format:

`GET http://localhost:8080/api/short-url?url=http://shorturl.com/yIQL4q1`

`POST http://localhost:8080/api/short-url
{
    "longUrl": "http://someverylongurl.com/where-good-things-can-be-found/use-this-promo-code"
}
`

GET response

```json
{
    "shortUrl": "http://shorturl.com/hyxTzxc",
    "longUrl": "http://someverylongurl.com/where-good-things-can-be-found/use-this-promo-code"
}
```

POST response

```json
{
    "shortUrl": "http://shorturl.com/hyxTzxc",
}
```

### Tech used

- Spring boot 3.2.3
- Java 17
- MongoDB

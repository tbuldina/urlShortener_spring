# URL Shortener #

Application simplifies urls by converting it to shorter one.

Opening short url will redirect user to original url.

## Approach ##

##### Encoding
1. long url -> id

   Match long URL with identificator
   (insert url to database and use its autoincremented identificator,
in case database already contain such url their identificator used)

2. id -> encoded short url

   Convert identificator to base62 string

3. Save short url (base62) in database

## How to use ##
1. *mvn package*

2. *java -jar target/urlShortener-0.0.1-SNAPSHOT.jar*

3. Application starts on port 8080
and accessible on http://localhost:8080/index


## Technologies ##
- Spring Boot
- H2 in-memory database



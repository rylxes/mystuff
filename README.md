#Build
##Backend (back subfolder)
###Maven
* Create jars
```bash
mnvw package
```
* Build docker image
```bash
mvnw dockerfile:build
``` 
##Frontend (*web subfolder*)
###Maven
* Build docker image
```bash
mvnw dockerfile:build
```
###Command line
* Preparations
    * Install npm ([instruction](https://www.npmjs.com/get-npm)).
    * Install angular cli. You can install it by execute command:
    ```bash
    npm install -g @angular/cli
    ```
* Build 
    * Installed npm modules
    ```bash
    npm i
    ```

    
#Run application
##Docker
###Web+back+neo4j
```bash
docker-compose up
```
##Backend
### From IDE
Run org.webtree.mystuff.boot.App
Require neo4j. You can [download it here](https://neo4j.com/download-center/). 
If you want to run App with embedded neo4j you should set property spring.data.neo4j.driver to org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver and spring.data.neo4j.uri as empty string. 
For more information about setting spring boot properties please read [spring documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)
##Frontend 
###Command line (require build from command line)
* For run just execute command
```bash
ng serve
```
* For run tests:
```bash
ng test
```

#
run: build start

start:
	docker compose build
	docker compose up

build:
	chmod 700 ./mvnw
	./mvnw clean install

stop:
	docker compose down

  <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
# auth-server
serwer autentykacyjny

docker build . -t auth-server

docker run -p 7000:7000 auth-server

Na porcie 7000 maszyny docker będzie uruchomiony serwer autoryzacyjny
IP maszyny docker sprawdzamy komendą:
docker-machine env

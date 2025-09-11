# Stage 1: Costruzione dell'app Angular
FROM node:latest AS node
WORKDIR /app
COPY package*.json ./
# Installo le dipendenze
RUN npm ci
COPY . .
RUN npm run build

# Stage 2: Servire l'app con Nginx
FROM nginx:alpine
EXPOSE 80
# Copio i file costruiti nell'immagine Nginx
COPY --from=node /app/dist/dieti-estates-frontend/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

##per eseguire:
    ## docker build . -t frontend-app
    ## docker run -d -p 4200:80 --name frontend-app frontend-app
##Per stoppare
    ##docker stop frontend-app
##Per rimuovere container
    ##docker rm frontend-app
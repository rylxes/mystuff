### STAGE 1: Build ###
FROM node:8-alpine as builder

RUN npm set progress=false && npm config set depth 0
#&& npm cache clean --force
RUN mkdir /web
WORKDIR /web
COPY . .
RUN npm config set registry http://registry.npmjs.org/
RUN npm i
RUN $(npm bin)/ng build --prod --build-optimizer

#RUN npm update && ng serve --prod

### STAGE 2: Setup ###
FROM nginx:1.13.3-alpine
#FROM janeczku/debian-nginx

# set back-url
COPY nginx/default.conf.template /etc/nginx/conf.d/default-template.conf
ENV back_url "localhost:9000"
RUN envsubst < /etc/nginx/conf.d/default-template.conf > /etc/nginx/conf.d/default.conf
RUN cat /etc/nginx/conf.d/default.conf

RUN rm -rf /usr/share/nginx/html/*

COPY --from=builder /web/dist /usr/share/nginx/html

CMD ['/bin/bash -c "envsubst < /etc/nginx/conf.d/default-template.conf > /etc/nginx/conf.d/default.conf && exec nginx -g \'daemon off;\'"']
#CMD ["nginx", "-g", "daemon off;"]

EXPOSE 80

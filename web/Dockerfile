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

COPY nginx/default.conf /etc/nginx/conf.d/

RUN rm -rf /usr/share/nginx/html/*

COPY --from=builder /web/dist /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]

EXPOSE 80

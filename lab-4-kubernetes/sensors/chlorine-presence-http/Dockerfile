FROM node:latest

RUN mkdir /app
WORKDIR /app/

ADD . /app/

RUN npm install

CMD ["node", "chlorine-presence-http.js"]

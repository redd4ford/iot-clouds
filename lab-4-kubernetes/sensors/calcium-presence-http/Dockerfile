FROM node:latest

RUN mkdir /app
WORKDIR /app/

ADD . /app/

RUN npm install

CMD ["node", "calcium-presence-http.js"]

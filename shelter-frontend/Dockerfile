FROM node:latest

WORKDIR /shelter-frontend


COPY package*.json ./

RUN npm install

COPY . .

EXPOSE 8081

CMD ["npm", "run", "serve", "--host=0.0.0.0"]
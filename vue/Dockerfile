FROM node:14.17.3
WORKDIR /app

COPY package.json .
COPY . .
RUN yarn install
CMD ["yarn", "run", "serve"]
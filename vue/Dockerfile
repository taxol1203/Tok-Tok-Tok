FROM node:14.17.3
WORKDIR /app

COPY package.json .
COPY yarn.lock .
COPY . .
RUN echo '--- run yarn install'
RUN yarn install
CMD ["yarn", "run", "serve"]
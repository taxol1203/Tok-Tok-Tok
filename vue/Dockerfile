FROM node:14.17.3 as frontend
WORKDIR /homepage

COPY package.json .
COPY yarn.lock .

RUN yarn install
COPY . .
RUN yarn run build

FROM nginx
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
COPY ./nginx/fullchain.pem /usr/share/nginx/fullchain.pem
COPY ./nginx/privkey.pem /usr/share/nginx/privkey.pem
COPY --from=frontend ./homepage/dist /usr/share/nginx/html/homepage

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

#RUN echo '--- run yarn install'
#RUN yarn install
#CMD ["yarn", "run", "serve"]
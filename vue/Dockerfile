FROM node:14.17.3 as frontend
RUN mkdir -p /homepage
WORKDIR /homepage

COPY package.json .
COPY yarn.lock .

RUN yarn install
COPY . .
RUN yarn run build

FROM nginx
RUN rm /etc/nginx/conf.d/default.conf
#COPY nginx/fullchain.pem /usr/share/nginx/
#COPY nginx/privkey.pem /usr/share/nginx/
#COPY nginx/app.conf /etc/nginx/conf.d/
COPY --from=frontend ./homepage/dist /usr/share/nginx/html/homepage

EXPOSE 80
EXPOSE 443
CMD ["nginx", "-g", "daemon off;"]

#RUN echo '--- run yarn install'
#RUN yarn install
#CMD ["yarn", "run", "serve"]
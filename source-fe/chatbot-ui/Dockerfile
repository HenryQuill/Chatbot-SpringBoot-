# (1) Use a Node image to build the React app
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Use a lightweight NGINX server to serve the built app
FROM nginx:alpine
# Copying build filefrom (1) to nginx html folder
COPY --from=build /app/build /usr/share/nginx/html

# Config nginx to support react router (avoid 404 error when reloading the page)
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf
 
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
# Use the official Node.js image
FROM node:18

# Create and set the working directory inside the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json for dependency installation
COPY package*.json ./

# Install Node.js dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Expose the application's port
EXPOSE 3000

# Command to start the application
CMD ["node", "index.js"]

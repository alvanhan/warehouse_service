#!/bin/bash

# Deployment script for Digital Ocean
echo "Starting deployment to Digital Ocean..."

# Stop only the application container (keep database and kafka running)
echo "Stopping application container..."
docker-compose stop warehouse-service
docker-compose rm -f warehouse-service

# Remove only the application image to force rebuild
echo "Removing old application image..."
docker rmi warhouse_service-warehouse-service 2>/dev/null || echo "No old image to remove"

# Build and start only the application service
echo "Building and starting warehouse service..."
docker-compose up --build -d warehouse-service

# Wait for application to be ready
echo "Waiting for application to start..."
sleep 30

# Check if services are running
echo "Checking service status..."
docker-compose ps

# Test the application
echo "Testing application endpoints..."
curl -f http://localhost:8080/api/hello || echo "Hello endpoint check failed"
curl -f http://localhost:8080/api/health || echo "Health endpoint check failed"

echo "Database and Kafka data preserved!"
echo "Deployment completed!"


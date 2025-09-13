@echo off
REM Deployment script for Digital Ocean - Windows Version
echo Starting deployment to Digital Ocean...

REM Stop only the application container (keep database and kafka running)
echo Stopping application container...
docker-compose stop warehouse-service
docker-compose rm -f warehouse-service

REM Remove only the application image to force rebuild
echo Removing old application image...
docker rmi warhouse_service-warehouse-service >nul 2>&1 || echo No old image to remove

REM Build and start only the application service
echo Building and starting warehouse service...
docker-compose up --build -d warehouse-service

REM Wait for application to be ready
echo Waiting for application to start...
timeout /t 30 /nobreak >nul

REM Check if services are running
echo Checking service status...
docker-compose ps

REM Test the application
echo Testing application endpoints...
curl -f http://localhost:8080/api/hello || echo Hello endpoint check failed
curl -f http://localhost:8080/api/health || echo Health endpoint check failed

echo Deployment completed!

pause

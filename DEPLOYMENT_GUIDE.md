# Panduan Deployment Warehouse Service ke DigitalOcean

## 🚀 Langkah-Langkah Deployment ke Server

### 1. PERSIAPAN SERVER DIGITALOCEAN

#### A. Login ke Server
```bash
ssh root@your-server-ip
```

#### B. Update System
```bash
apt update && apt upgrade -y
```

#### C. Install Docker
```bash
# Install Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh

# Start Docker service
systemctl start docker
systemctl enable docker

# Verify Docker installation
docker --version
```

#### D. Install Docker Compose
```bash
# Download Docker Compose
curl -L "https://github.com/docker/compose/releases/download/v2.21.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

# Make it executable
chmod +x /usr/local/bin/docker-compose

# Verify installation
docker-compose --version
```

#### E. Install Git
```bash
apt install git -y
```

#### F. Install Curl (untuk testing)
```bash
apt install curl -y
```

### 2. SETUP GITHUB REPOSITORY

#### A. Di Local (Windows):
```cmd
# Navigate ke project
cd E:\JavaApp\warhouse_service

# Initialize git (jika belum)
git init

# Add all files
git add .

# Commit
git commit -m "Initial commit - warehouse service ready for deployment"

# Add remote repository
git remote add origin https://github.com/yourusername/warehouse-service.git

# Push to GitHub
git push -u origin main
```

#### B. Buat Repository di GitHub:
1. Buka https://github.com
2. Klik "New repository"
3. Nama: `warehouse-service`
4. Public atau Private (pilih sesuai kebutuhan)
5. Jangan centang "Initialize with README" (karena sudah ada code)
6. Click "Create repository"

### 3. CLONE PROJECT DI SERVER

#### A. Clone dari GitHub
```bash
# Clone project
git clone https://github.com/yourusername/warehouse-service.git

# Masuk ke directory
cd warehouse-service

# Verify files
ls -la
```

### 4. SETUP FIREWALL (Security)

```bash
# Allow SSH (port 22)
ufw allow 22

# Allow HTTP (port 80) - optional for future
ufw allow 80

# Allow HTTPS (port 443) - optional for future
ufw allow 443

# Allow application port (8080)
ufw allow 8080

# Allow PostgreSQL (5432) - hanya jika perlu akses eksternal
ufw allow 5432

# Allow Kafka (9092) - hanya jika perlu akses eksternal
ufw allow 9092

# Enable firewall
ufw --force enable

# Check status
ufw status
```

### 5. DEPLOYMENT

#### A. Make script executable
```bash
chmod +x deploy.sh
```

#### B. Run deployment
```bash
./deploy.sh
```

#### C. Jika ada error, check logs:
```bash
docker-compose logs -f warehouse-service
docker-compose ps
```

### 6. TESTING

#### A. Test dari dalam server:
```bash
curl http://localhost:8080/api/hello
curl http://localhost:8080/api/health
```

#### B. Test dari luar (ganti dengan IP server Anda):
```bash
curl http://your-server-ip:8080/api/hello
curl http://your-server-ip:8080/api/health
```

### 7. MONITORING & MAINTENANCE

#### A. Check container status:
```bash
docker-compose ps
```

#### B. View logs:
```bash
# All services
docker-compose logs

# Specific service
docker-compose logs -f warehouse-service
docker-compose logs -f postgres-db
docker-compose logs -f kafka
```

#### C. Restart services jika perlu:
```bash
# Restart specific service
docker-compose restart warehouse-service

# Restart all
docker-compose restart
```

### 8. UPDATE APLIKASI (Workflow untuk perubahan code)

```bash
# Pull latest code
git pull origin main

# Rebuild and deploy
./deploy.sh
```

---

## 🔧 TROUBLESHOOTING

### Jika Docker build gagal:
```bash
# Check disk space
df -h

# Clean up Docker
docker system prune -f
```

### Jika port conflict:
```bash
# Check what's using port
netstat -tlnp | grep :8080

# Kill process if needed
kill -9 <PID>
```

### Jika database connection error:
```bash
# Check if PostgreSQL container is running
docker-compose ps

# Check PostgreSQL logs
docker-compose logs postgres-db
```

---

## 📋 CHECKLIST DEPLOYMENT

- [ ] Server setup (Docker, Docker Compose, Git)
- [ ] Firewall configured
- [ ] GitHub repository created
- [ ] Code pushed to GitHub
- [ ] Project cloned to server
- [ ] deploy.sh executed successfully
- [ ] Endpoints accessible from outside
- [ ] Logs checked for errors

---

## 🎯 HASIL AKHIR

Setelah selesai, aplikasi Anda akan berjalan di:

- **Hello World**: http://your-server-ip:8080/api/hello
- **Health Check**: http://your-server-ip:8080/api/health

Database PostgreSQL dan Kafka akan running persistent dengan data yang aman.

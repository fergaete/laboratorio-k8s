# Laboratorio Kubernetes con AWS EKS

Este proyecto demuestra la implementación de una arquitectura de microservicios utilizando Kubernetes en AWS EKS, con un frontend en Next.js y un backend en Spring Boot.

## Arquitectura

La arquitectura del proyecto incluye los siguientes componentes:

- **AWS EKS (Elastic Kubernetes Service)**: Cluster de Kubernetes gestionado
- **AWS ECR (Elastic Container Registry)**: Registro de contenedores para las imágenes Docker
- **AWS EC2**: Instancias para el cluster de Kubernetes
- **Frontend**: Aplicación Next.js
- **Backend**: Aplicación Spring Boot

## Prerrequisitos

- AWS CLI configurado con credenciales válidas
- kubectl instalado
- Docker instalado
- AWS EKS CLI instalado
- Java 17 o superior
- Node.js 18 o superior
- npm o yarn

## Configuración del Entorno

1. **Configurar AWS CLI**:
   ```bash
   aws configure
   ```

2. **Crear cluster EKS**:
   ```bash
   eksctl create cluster \
     --name lab-cluster \
     --region us-east-1 \
     --node-type t3.medium \
     --nodes 2 \
     --nodes-min 2 \
     --nodes-max 3
   ```

3. **Configurar kubectl**:
   ```bash
   aws eks update-kubeconfig --name lab-cluster --region us-east-1
   ```

## Estructura del Proyecto

```
.
├── frontend-calculadora/                 # Aplicación Next.js
│   ├── Dockerfile
├── backend-calculadora/                  # Aplicación Spring Boot
│   ├── Dockerfile
└── k8s/                                  # Configuraciones generales de Kubernetes
    ├── namespace.yaml
    └── backend/
    └── frontend/
```

## Despliegue

### 1. Crear Repositorios ECR

```bash
# Frontend
aws ecr create-repository --repository-name frontend-calculadora --region us-east-1

# Backend
aws ecr create-repository --repository-name backend-calculadora --region us-east-1
```

### 2. Construir y Subir Imágenes Docker

```bash
# Frontend
cd frontend
docker build -t frontend-app .
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com
docker tag frontend-app:latest $AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/frontend-app:latest
docker push $AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/frontend-app:latest

# Backend
cd ../backend
docker build -t backend-app .
docker tag backend-app:latest $AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/backend-app:latest
docker push $AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/backend-app:latest
```

### 3. Desplegar en Kubernetes

```bash
# Crear namespace
kubectl apply -f k8s/namespace.yaml

# Desplegar backend
kubectl apply -f k8s/backend/

# Desplegar frontend
kubectl apply -f k8s/frontend/
```

## Acceso a las Aplicaciones

- Frontend: http://frontend.example.com
- Backend API: http://backend.example.com

## Monitoreo y Logs

```bash
# Ver pods
kubectl get pods -n lab-namespace

# Ver logs de un pod
kubectl logs <pod-name> -n lab-namespace

# Ver servicios
kubectl get services -n lab-namespace
```

## Limpieza

Para eliminar todos los recursos:

```bash
# Eliminar deployments y servicios
kubectl delete -f kubernetes/ingress.yaml
kubectl delete -f frontend/kubernetes/
kubectl delete -f backend/kubernetes/
kubectl delete -f kubernetes/namespace.yaml

# Eliminar cluster EKS
eksctl delete cluster --name lab-cluster --region us-east-1
```

## Consideraciones de Seguridad

- Utilizar secrets de Kubernetes para credenciales
- Implementar políticas de red restrictivas
- Configurar RBAC adecuadamente
- Mantener las imágenes actualizadas con las últimas correcciones de seguridad

## Troubleshooting

### Problemas Comunes

1. **Pods en estado Pending**:
   ```bash
   kubectl describe pod <pod-name> -n lab-namespace
   ```

2. **Errores de conexión entre servicios**:
   ```bash
   kubectl get endpoints -n lab-namespace
   ```

3. **Problemas con ECR**:
   ```bash
   aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com
   ```

## Contribución

1. Fork el repositorio
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request
# Social Media Platform (Microservices Architecture)

A scalable, distributed social media platform built with Java and Spring Boot, utilizing a polyglot persistence model, event-driven communication, and cloud orchestration with Google Kubernetes Engine (GKE).

---

## 🛠️ Tech Stack & Architecture

### **Backend Frameworks & Core**
* **Java**: Primary programming language for service development.
* **Spring Boot**: Foundation for building RESTful microservices.
* **Apache Maven**: Project management and dependency build tool.

### **Database & Data Management**
* **Polyglot Persistence**:
  * **PostgreSQL**: Relational database handling User Accounts, Posts, and Notification data.
  * **Neo4j**: Graph database powering user connections and social graph relationships.

### **Messaging & Event-Driven Architecture**
* **Apache Kafka**: Distributed event streaming platform used for asynchronous inter-service communication and event processing.

### **Containerization, Cloud & Orchestration**
* **Docker & Docker Compose**: Containerized environment for local service orchestration and multi-container development.
* **Google Kubernetes Engine (GKE)**: Managed cloud Kubernetes service used to deploy, scale, and manage containerized microservices in production.
* **Kubernetes (k8s)**: Manages service discovery, automated rollouts, horizontal pod autoscaling, and load balancing across all microservices.

### **Frontend (Planned / In Development)**
* **React**: User interface framework.
* **TypeScript**: Strongly typed JavaScript environment.

---

## 🏗️ Services Breakdown

* **User Service**: Manages user profiles and authentication (`PostgreSQL`).
* **Post Service**: Handles media/text posts and feed data (`PostgreSQL`).
* **Notification Service**: Delivers event-driven alerts (`PostgreSQL` + `Kafka`).
* **Connections Service**: Graphs follower/following relationships (`Neo4j`).

---

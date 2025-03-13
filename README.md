# Real-Time Chat Backend

This is the backend server for the **Real-Time Chat Application**, built using **Spring Boot** with **GraphQL** to handle messaging and status updates.

## Features

- **GraphQL API** for efficient data fetching
- **Subscriptions** for real-time message updates
- **Spring Boot** for scalable backend services
- **MongoDB** for data storage
- **WebSockets** for real-time communication
- **Secure & Lightweight** architecture

## Tech Stack

- **Framework:** Spring Boot
- **GraphQL Library:** GraphQL Java & Spring Boot GraphQL
- **Database:** MongoDB
- **Messaging:** WebSockets, GraphQL Subscriptions
- **Build Tool:** Maven
- **Security:** JWT Authentication (optional)

## Getting Started

### Prerequisites
- JDK 17+
- Maven
- MongoDB (running locally or via Docker)

### Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/anubhav-auth/messagingPrac.git
   cd messagingPrac
   ```

2. Configure the application properties (`src/main/resources/application.properties`):
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/chat_db
   ```

3. Build and run the server:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## GraphQL API Schema

```graphql
enum MessageStatus {
    UNSENT
    SENT
    DELIVERED
    READ
}

type Message {
    id: String!
    topic: String!
    content: String!
    sender: String!
    receiver: String!
    status: MessageStatus!
    sentAt: String!
    deliveredAt: String!
    readAt: String!
}

type MessageStatusUpdates {
    id: String!
    receiver: String!
    status: MessageStatus!
    deliveredAt: String!
    readAt: String!
}

type Query {
    syncMessages(topic: String): [Message!]!
}

type Mutation {
    sendMessage(id: String!, topic: String!, content: String!, sender: String!, sentAt: String!): Message!
    statusUpdate(messageId: String!, topic: String!, status: MessageStatus!, deliveredAt: String!, readAt: String!): MessageStatusUpdates!
}

type Subscription {
    messageAdded(topic: String!): Message!
    messageUpdates(topic: String!): MessageStatusUpdates!
}
```

## Example Query (Fetching Messages)
```graphql
query GetMessages($topic: String!) {
  syncMessages(topic: $topic) {
    id
    content
    sender
    receiver
    status
    sentAt
    deliveredAt
    readAt
  }
}
```

## Running with Docker

1. Build the Docker image:
   ```bash
   docker build -t chat-backend .
   ```

2. Run the container:
   ```bash
   docker run -p 8080:8080 --name chat-backend chat-backend
   ```

## Contributing

1. Fork the repository.
2. Create a new branch (`feature/new-feature` or `bugfix/fix-issue`).
3. Commit your changes with meaningful messages.
4. Push to your branch and create a **Pull Request**.

## License

This project is licensed under the **MIT License**. See the `LICENSE` file for details.

---

### Contact
For any queries, feel free to reach out:
- **Email:** [anubhavauth@gmail.com](mailto:anubhavauth@gmail.com)
- **GitHub:** [anubhav-auth](https://github.com/anubhav-auth)

Happy Coding! 🚀


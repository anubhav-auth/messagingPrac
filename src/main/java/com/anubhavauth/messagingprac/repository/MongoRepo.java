package com.anubhavauth.messagingprac.repository;

import com.anubhavauth.messagingprac.models.Message;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRepo extends MongoRepository<Message, ObjectId> {
}

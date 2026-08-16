package com.Harsh.SocialMedia.connection_service.repository;

import com.Harsh.SocialMedia.connection_service.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person,Long> {
    Optional<Person> getByName(String name);

    @Query("MATCH (personA:Person) -[:CONNECTED_TO]-(personB:Person) WHERE personA.userId= $userId RETURN personB")
    List<Person> getFirstDegreeConnections(@Param("userId") Long userId);

    @Query("MATCH (me:Person {userId: $userId})-[:CONNECTED_TO*2]-(fof:Person) " +
            "WHERE fof.userId <> $userId " +
            "  AND NOT (me)-[:CONNECTED_TO]-(fof) " +
            "RETURN DISTINCT fof")
    List<Person> getSecondDegreeConnections(@Param("userId") Long userId);

    @Query("MATCH (me:Person {userId: $userId})-[:CONNECTED_TO*3]-(degree3:Person) " +
            "WHERE degree3.userId <> $userId " +
            "  AND NOT (me)-[:CONNECTED_TO*1..2]-(degree3) " +
            "RETURN DISTINCT degree3")
    List<Person> getThirdDegreeConnections(@Param("userId") Long userId);

    @Query("Match (p1:Person)-[r:REQUESTED_TO]->(p2:Person)"+"WHERE p1.userId = $senderId AND p2.userId=$receiverId"+"RETURN count(r)>0")
    Boolean connectionRequestExists(Long senderId,Long receiverId);

    @Query("MATCH (p1:Person)-[r:CONNECTED_TO]-(p2:Person)"+"WHERE p1.userId=$senderId AND p2.userId = $receiverId"+"RETURN count(r)>0")
    boolean alreadyConnected(Long senderId,Long receiverId);

    @Query("MATCH (p1:Person),(p2:Person)"+"WHERE p1:userId=$senderID AND p2.userId=$receiverId"+"CREATE (p1)-[:REQUESTED_TO]->(p2)")
    void addConnectionRequest(Long senderId,Long receiverId);

    @Query("MATCH (p1:Person)-[r:REQUESTED_TO]->(p2:Person)"+"WHERE p1.userId=$senderId AND p2.userId=$receiverId"+"DELETE r"+"CREATE (p1)-[:CONNECTED_TO]->(p2)")
    void acceptConnectionRequest(Long senderId,Long receiverId);

    @Query("MATCH (p1:Person)-[r:REQUESTED_TO]->(p2:Person)"+"WHERE p1.userId=$senderId AND p2.userId=$receiverId"+"DELETE r")
    void rejectConnectionRequest(Long senderId,Long receiverId);
}

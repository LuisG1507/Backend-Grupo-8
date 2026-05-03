package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.conversationDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Conversation;

import java.util.List;

@Repository
public interface IConversationRepository extends JpaRepository<Conversation, Integer> {

    @Query("SELECT e.title as title, COUNT(c.idConversation) as conversationCount " +
            "FROM Conversation c JOIN c.estate e " +
            "GROUP BY e.title")
    List<Object[]> getConversationCountPerEstate();

    @Query(value = "SELECT e.title, e.city, COUNT(c.id_conversation) AS total_conversaciones\n" +
            "FROM conversation c\n" +
            "INNER JOIN estate e ON c.id_estate = e.id_estate\n" +
            "WHERE e.id_estate NOT IN (\n" +
            "    SELECT id_estate FROM contract WHERE status = true\n" +
            ")\n" +
            "GROUP BY e.id_estate, e.title, e.city\n" +
            "ORDER BY total_conversaciones DESC\n",nativeQuery = true)
    List<Object[]> findEstatesWithConversationsButNoContract();


    @Query(value = "SELECT u.name, u.last_name, COUNT(c.id_conversation) AS total_conversaciones\n" +
            "FROM conversation c\n" +
            "INNER JOIN users u ON c.id_user1 = u.id_user\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "ORDER BY total_conversaciones DESC",nativeQuery = true)
    List<Object[]> findMostActiveInitiators();

    @Query(value = "SELECT e.id_estate, e.title, e.city, e.monthly_price\n" +
            "FROM estate e\n" +
            "LEFT JOIN conversation c ON e.id_estate = c.id_estate\n" +
            "WHERE c.id_conversation IS NULL\n",nativeQuery = true)
    List<Object[]> findEstatesWithNoConversations();

    @Query(value = "SELECT e.city,\n" +
            "       COUNT(c.id_conversation) AS total_conversaciones,\n" +
            "       COUNT(DISTINCT e.id_estate) AS total_inmuebles,\n" +
            "       ROUND(COUNT(c.id_conversation) * 1.0 / COUNT(DISTINCT e.id_estate), 2) AS promedio\n" +
            "FROM estate e\n" +
            "LEFT JOIN conversation c ON e.id_estate = c.id_estate\n" +
            "GROUP BY e.city\n" +
            "ORDER BY promedio DESC\n",nativeQuery = true)
    List<Object[]> findAverageConversationsPerEstateByCity();

}
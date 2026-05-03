package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.messagesDTOS.MessagesInactiveUserDTO;
import pe.edu.pe.smartrent_backend.DTOS.messagesDTOS.MessagesStatusDistributionDTO;
import pe.edu.pe.smartrent_backend.DTOS.messagesDTOS.MessagesUrgentConversationDTO;
import pe.edu.pe.smartrent_backend.DTOS.messagesDTOS.MessagesUrgentUserDTO;
import pe.edu.pe.smartrent_backend.Entities.Messages;

import java.util.List;

@Repository
public interface IMessagesRepository extends JpaRepository <Messages, Integer> {

    //QuerySimple
    List<Messages> findByStatus(String status);

    //QueryTomaDecisiones
    @Query(value = "SELECT u.name, u.last_name, COUNT(m.id_message) AS mensajes_urgentes\n" +
            "FROM messages m\n" +
            "INNER JOIN users u ON m.id_user = u.id_user\n" +
            "WHERE m.status = 'URGENTE'\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "ORDER BY mensajes_urgentes DESC",nativeQuery = true)
    List<Object[]> findUsersWithMostUrgentMessages();


    @Query(value = "SELECT status,\n" +
            "       COUNT(*) AS total,\n" +
            "       ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM messages), 2) AS porcentaje\n" +
            "FROM messages\n" +
            "GROUP BY status\n" +
            "ORDER BY total DESC",nativeQuery = true)
    List<Object[]> findMessageDistributionByStatus();

    @Query(value = "SELECT c.id_conversation,\n" +
            "       e.title AS inmueble,\n" +
            "       COUNT(m.id_message) AS mensajes_urgentes\n" +
            "FROM messages m\n" +
            "INNER JOIN conversation c ON m.id_conversation = c.id_conversation\n" +
            "INNER JOIN estate e ON c.id_estate = e.id_estate\n" +
            "WHERE m.status = 'URGENTE'\n" +
            "GROUP BY c.id_conversation, e.title\n" +
            "ORDER BY mensajes_urgentes DESC",nativeQuery = true)
    List<Object[]> findConversationsWithMostUrgentMessages();

    @Query(value = "SELECT u.id_user, u.name, u.last_name, u.created_date\n" +
            "FROM users u\n" +
            "LEFT JOIN messages m ON u.id_user = m.id_user\n" +
            "WHERE m.id_message IS NULL\n" +
            "ORDER BY u.created_date DESC",nativeQuery = true)
    List<Object[]> findUsersWithNoMessages();


    List<Messages> findByConversation_IdConversation(Integer conversationId);

}

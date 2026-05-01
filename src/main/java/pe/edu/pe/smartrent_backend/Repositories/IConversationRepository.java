package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.Conversation;
import pe.edu.pe.smartrent_backend.DTOS.conversationDTOS.EstateConversationCountDTO;
import java.util.List;

@Repository
public interface IConversationRepository extends JpaRepository<Conversation, Integer> {

    @Query("SELECT e.title as title, COUNT(c.idConversation) as conversationCount " +
            "FROM Conversation c JOIN c.estate e " +
            "GROUP BY e.title")
    List<Object[]> getConversationCountPerEstate();
}
package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.Conversation;
import java.util.List;

@Repository
public interface IConversationRepository extends JpaRepository<Conversation, Integer> {

    @org.springframework.data.jpa.repository.Query("SELECT new pe.edu.pe.smartrent_backend.DTOS.conversationDTOS.EstateConversationCountDTO(e.title, COUNT(c.idConversation)) " +
            "FROM Conversation c JOIN c.estate e " +
            "GROUP BY e.title")
    List<pe.edu.pe.smartrent_backend.DTOS.conversationDTOS.EstateConversationCountDTO> getConversationCountPerEstate();
}
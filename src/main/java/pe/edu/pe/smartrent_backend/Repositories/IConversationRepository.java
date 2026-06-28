package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.conversationDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Conversation;

import java.util.List;

@Repository
public interface IConversationRepository extends JpaRepository<Conversation, Integer> {



}
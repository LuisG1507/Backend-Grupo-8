package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.DTOS.conversationDTOS.EstateConversationCountDTO;
import pe.edu.pe.smartrent_backend.Entities.Conversation;
import java.util.List;

public interface IConversationService {
    public void insert(Conversation conversation);
    public List<Conversation> list();
    public void delete(Integer id);
    public Conversation listId(Integer id);
    public void update(Conversation conversation);
    public List<EstateConversationCountDTO> getConversationCountPerEstate();
}
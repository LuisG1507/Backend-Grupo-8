package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.DTOS.conversationDTOS.EstateConversationCountDTO;
import pe.edu.pe.smartrent_backend.Entities.Conversation;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IConversationService;
import pe.edu.pe.smartrent_backend.Repositories.IConversationRepository; // <-- IMPORTANTE: Ahora tiene la 'I'

import java.util.List;

@Service
public class ConversationServiceImplements implements IConversationService {

    @Autowired
    private IConversationRepository cR; // <-- IMPORTANTE: Ahora usa la Interfaz correcta

    @Override
    public void insert(Conversation conversation) {
        cR.save(conversation);
    }

    @Override
    public List<Conversation> list() {
        return cR.findAll();
    }

    @Override
    public void delete(Integer id) {
        cR.deleteById(id);
    }

    @Override
    public Conversation listId(Integer id) {
        return cR.findById(id).orElse(new Conversation());
    }

    @Override
    public void update(Conversation conversation) {
        cR.save(conversation);
    }

    @Override
    public List<EstateConversationCountDTO> getConversationCountPerEstate() {
        return cR.getConversationCountPerEstate();
    }
}
package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.Conversation;
import pe.edu.pe.smartrent_backend.Repositories.ConversationRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IConversationService;

import java.util.List;

@Service
public class ConversationServiceImplements implements IConversationService {

    @Autowired
    private ConversationRepository cR;

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
        return cR.findById(id).orElse(null);
    }

    @Override
    public void update(Conversation conversation) {
        cR.save(conversation);
    }
}
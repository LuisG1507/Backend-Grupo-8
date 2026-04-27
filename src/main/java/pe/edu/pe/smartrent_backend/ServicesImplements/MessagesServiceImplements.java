package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.Messages;
import pe.edu.pe.smartrent_backend.Repositories.IMessagesRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IMessages;

import java.util.List;
import java.util.Optional;

@Service
public class MessagesServiceImplements implements IMessages {
    @Autowired
    private IMessagesRepository mR;
    @Override
    public Messages Registrar(Messages messages) {
        return mR.save(messages);
    }
    @Override
    public void Update(Messages messages) {
    mR.save(messages);
    }
    @Override
    public List<Messages> list() {
        return mR.findAll();
    }
    @Override
    public Optional<Messages> listId(int id) {
        return mR.findById(id);
    }
    @Override
    public void Delete(Integer id) {
        mR.deleteById(id);
    }
    @Override
    public List<Messages> findByStatus(String status) {
        return mR.findByStatus(status);
    }
    @Override
    public List<Messages> findUrgentMessagesWithUserJPQL() {
        return mR.findUrgentMessagesWithUserJPQL();
    }

}

package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.Messages;
import pe.edu.pe.smartrent_backend.Repositories.IMessagesRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IMessages;

import java.util.List;

@Service
public class MessagesServiceImplements implements IMessages {
    @Autowired
    private IMessagesRepository mR;

    @Override
    public void Registrar(Messages messages) {
        mR.save(messages);
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
    public Messages listId(Integer id) {
        return mR.findById(id).orElse(null);
    }

    @Override
    public void Delete(Integer id) {
        mR.deleteById(id);

    }
}

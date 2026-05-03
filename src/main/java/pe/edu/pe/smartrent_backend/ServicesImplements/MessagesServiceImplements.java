package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.DTOS.messagesDTOS.MessagesDateActivityDTO;
import pe.edu.pe.smartrent_backend.Entities.Messages;
import pe.edu.pe.smartrent_backend.Repositories.IConversationRepository;
import pe.edu.pe.smartrent_backend.Repositories.IMessagesRepository;
import pe.edu.pe.smartrent_backend.Repositories.IUserRepository;
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
    public List<Object[]> findUsersWithMostUrgentMessages() {
        return mR.findUsersWithMostUrgentMessages();
    }

    @Override
    public List<Object[]> findMessageDistributionByStatus() {
        return mR.findMessageDistributionByStatus();
    }

    @Override
    public List<Object[]> findConversationsWithMostUrgentMessages() {
        return mR.findConversationsWithMostUrgentMessages();
    }

    @Override
    public List<Object[]> findUsersWithNoMessages() {
        return mR.findUsersWithNoMessages();
    }

    @Override
    public List<Messages> findByStatus(String status) {
        return mR.findByStatus(status);
    }

    @Override
    public List<MessagesDateActivityDTO> findMessagesActivityByDate() {
        List<Object[]> filaLista = mR.findMessagesActivityByDate();
        List<MessagesDateActivityDTO> dtoLista = new java.util.ArrayList<>();

        for (Object[] columna : filaLista) {
            MessagesDateActivityDTO dto = new MessagesDateActivityDTO();
            dto.setFecha(columna[0].toString());
            dto.setCantidadMensajes((Long) columna[1]);
            dtoLista.add(dto);
        }
        return dtoLista;

    }


}

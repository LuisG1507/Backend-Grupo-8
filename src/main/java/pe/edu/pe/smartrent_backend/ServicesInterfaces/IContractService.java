import java.util.List;
import java.util.Optional;

public interface IContractService {
    List<Contract> list();
    Contract insert(Contract c);
    Optional<Contract> listId(int id);
    void update(Contract c);
    void delete(int id);
}
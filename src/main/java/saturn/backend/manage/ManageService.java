package saturn.backend.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageService {

    @Autowired
    ManageRepository manageRepository;

    public Manage retrieveManage(String tableName){
        Manage manage = manageRepository.findByTableName(tableName).orElse(null);
        return manage;
    }
}

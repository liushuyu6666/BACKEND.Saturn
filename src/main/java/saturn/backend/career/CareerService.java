package saturn.backend.career;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saturn.backend.manage.Manage;
import saturn.backend.manage.ManageRepository;
import saturn.backend.security.jwt.JwtUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CareerService {

    @Autowired
    CareerRepository careerRepository;

    @Autowired
    ManageRepository manageRepository;

    @Autowired
    JwtUtils jwtUtils;

    public Career createCareer(Career newCareer, String username){
        newCareer.setActive(true);
        newCareer.setApplied(false);
        newCareer.setCreateBy(username);
        newCareer.setLastModify(username);
        newCareer = careerRepository.save(newCareer);
        Integer count = careerRepository.findAll().size();
        Manage careerManage = manageRepository.findByTableName("career").orElse(null);
        assert careerManage != null;
        careerManage.setAmountOfEntries(count);
        manageRepository.save(careerManage);
        return newCareer;
    }

    public Career retrieveCareer(String careerId){
        return careerRepository.findById(careerId).orElse(null);
    }

    public List<Career> listCareer(String jwt, Pageable paging, Boolean isActive, Boolean isApplied){
        List<Career> response = new ArrayList<>();
        for(Career c : careerRepository.findAllByIsActiveAndIsAppliedOrderByModifiedAtDesc(paging, isActive, isApplied)){
            Career newCareer = new Career();
            if(jwt.length() > 8 && jwtUtils.validateJwtToken(jwt.substring(7)))
                newCareer.setLink(c.getLink());
            newCareer.setId(c.getId());
            newCareer.setCompanyName(c.getCompanyName());
            newCareer.setPosition(c.getPosition());
            newCareer.setCity(c.getCity());
            newCareer.setDeadline(c.getDeadline());
            newCareer.setActive(c.getActive());
            newCareer.setApplied(c.getApplied());
            newCareer.setModifiedAt(c.getModifiedAt());
            response.add(newCareer);
        }
        return response;
    }

    public Career updateCareer(Career updateCareer, String username){
        Career targetCareer = careerRepository.findById(updateCareer.getId()).orElse(null);
        if(targetCareer != null){
            targetCareer.setLastModify(username);
            targetCareer.setCompanyName(updateCareer.getCompanyName());
            targetCareer.setContent(updateCareer.getContent());
            targetCareer.setDeadline(updateCareer.getDeadline());
            targetCareer.setCity(updateCareer.getCity());
            targetCareer.setLink(updateCareer.getLink());
            targetCareer.setPosition(updateCareer.getPosition());
            targetCareer.setStart(updateCareer.getStart());
            targetCareer.setActive(updateCareer.getActive());
            targetCareer.setApplied(updateCareer.getApplied());
            targetCareer = careerRepository.save(targetCareer);
            return targetCareer;
        }
        else return null;
    }

    public Career deactivateCareer(String careerId){
        Career career = careerRepository.findById(careerId).orElse(null);
        if(career == null){
            return null;
        }
        career.setActive(false);
        return careerRepository.save(career);
    }

    public Career activateCareer(String careerId){
        Career career = careerRepository.findById(careerId).orElse(null);
        if(career == null){
            return null;
        }
        career.setActive(true);
        return careerRepository.save(career);
    }

    public Career appliedCareer(String careerId){
        Career career = careerRepository.findById(careerId).orElse(null);
        if(career == null){
            return null;
        }
        career.setApplied(true);
        return careerRepository.save(career);
    }

    public Career deAppliedCareer(String careerId){
        Career career = careerRepository.findById(careerId).orElse(null);
        if(career == null){
            return null;
        }
        career.setApplied(false);
        return careerRepository.save(career);
    }

    public Integer countListCareer(Boolean isActive, Boolean isApplied){
        return careerRepository.findAllByIsActiveAndIsAppliedOrderByModifiedAtDesc(isActive, isApplied).size();
    }
}

package saturn.backend.career;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CareerService {

    @Autowired
    CareerRepository careerRepository;

    public Career createCareer(Career newCareer, String username){
        newCareer.setActive(true);
        newCareer.setApplied(false);
        newCareer.setCreateBy(username);
        newCareer.setLastModify(username);
        return careerRepository.save(newCareer);
    }

    public Career retrieveCareer(String careerId){
        return careerRepository.findById(careerId).orElse(null);
    }

    public List<Career> listCareer(){
        List<Career> response = new ArrayList<>();
        for(Career c : careerRepository.findAll()){
            Career newCareer = new Career();
            newCareer.setLink(c.getLink());
            newCareer.setId(c.getId());
            newCareer.setCompanyName(c.getCompanyName());
            newCareer.setPosition(c.getPosition());
            newCareer.setCity(c.getCity());
            newCareer.setDeadline(c.getDeadline());
            newCareer.setActive(c.getActive());
            newCareer.setApplied(c.getApplied());
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
            targetCareer.setActive(targetCareer.getActive());
            targetCareer.setApplied(targetCareer.getApplied());
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

}

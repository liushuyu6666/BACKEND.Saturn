package saturn.backend.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saturn.backend.career.Career;
import saturn.backend.manage.Manage;
import saturn.backend.manage.ManageRepository;
import saturn.backend.user.User;
import saturn.backend.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    ManageRepository manageRepository;

    @Autowired
    UserRepository userRepository;

    public Language createLanguage(Language newLanguage, String userId){
        newLanguage.setCreateBy(userId);
        if(newLanguage.getPrivate() == null) newLanguage.setPrivate(false);
        if(newLanguage.getActive() == null) newLanguage.setActive(true);
        newLanguage = languageRepository.save(newLanguage);
        Integer count = languageRepository.findAll().size();
        Manage careerManage = manageRepository.findByTableName("language").orElse(null);
        assert careerManage != null;
        careerManage.setAmountOfEntries(count);
        manageRepository.save(careerManage);
        return newLanguage;
    }

    public Language retrieveLanguage(String languageId, String userId){
        Language retrievedLanguage = languageRepository.findByIdAndCreateBy(languageId, userId).orElse(null);
        if(retrievedLanguage != null){
            User createBy = userRepository.findById(retrievedLanguage.getCreateBy()).orElse(null);
            String username = (createBy != null)? (createBy.getUsername()):(null);
            retrievedLanguage.setCreateBy(username);
        }
        return retrievedLanguage;
    }

    public List<Language> listLanguage(Pageable paging){
        List<Language> response = new ArrayList<>();
        for(Language l : languageRepository.findAllByOrderByModifiedAtDesc(paging)){
            if(l.getActive()){
                Language newLanguage = new Language();
                newLanguage.setId(l.getId());
                newLanguage.setKeywords(l.getKeywords());
                newLanguage.setContent(l.getContent());
//                newLanguage.setCreateBy(l.getCreateBy());
                User createBy = userRepository.findById(l.getCreateBy()).orElse(null);
                String username = (createBy != null)? (createBy.getUsername()):(null);
                newLanguage.setCreateBy(username);
                newLanguage.setModifiedAt(l.getModifiedAt());
                response.add(newLanguage);
            }
        }
        return response;
    }

    public Language updateLanguage(Language updateLanguage, String userId){
        Language targetLanguage = languageRepository.findByIdAndCreateBy(updateLanguage.getId(), userId).orElse(null);
        if(targetLanguage != null){
            targetLanguage.setKeywords(updateLanguage.getKeywords());
            targetLanguage.setContent(updateLanguage.getContent());
            targetLanguage.setPrivate(updateLanguage.getPrivate());
            targetLanguage.setActive(true);
            targetLanguage = languageRepository.save(targetLanguage);
            return targetLanguage;
        }
        else return null;
    }

    public Language deactivateLanguage(String languageId){
        Language language = languageRepository.findById(languageId).orElse(null);
        if(language == null){
            return null;
        }
        language.setActive(false);
        return languageRepository.save(language);
    }
}

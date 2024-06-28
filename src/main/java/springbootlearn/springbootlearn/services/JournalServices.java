package springbootlearn.springbootlearn.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springbootlearn.springbootlearn.entity.JournalEntry;
import springbootlearn.springbootlearn.entity.User;
import springbootlearn.springbootlearn.repository.JournalRepositiory;

@Service
@Component
public class JournalServices {

    @Autowired
    private JournalRepositiory journalRepositiory;

    @Autowired
    private UserServices userServices;



    @Transactional
    public boolean saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userServices.findByUserName(userName);

            JournalEntry entry = journalRepositiory.save(journalEntry);
            user.getJournalEntries().add(entry);
            // user.setUserName(null);
            userServices.saveJournalInUser(user);
            return true;

        } catch (Exception e) {
          
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }

    }

    public List<JournalEntry> getAll() {
        return journalRepositiory.findAll();
    }

    public Optional<JournalEntry> getOne(String id) {
        return journalRepositiory.findById(id);

    }

    @Transactional
    public boolean deleteOne(String id, String userName) {
        boolean removed = false;
        try {

            User user = userServices.findByUserName(userName);

            removed = user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
            if (removed) {
                userServices.save(user);
                journalRepositiory.deleteById(id);
            }
            return removed;
        } catch (Exception e) {

            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }

    }

    public boolean updateOne(String id, JournalEntry newEntry) {
        try {

            JournalEntry oldEntry = journalRepositiory.findById(id).orElse(null);
            if (oldEntry != null) {

                oldEntry.setContent(
                        newEntry.getContent() == null ||
                                newEntry.getContent().equals("") ? oldEntry.getContent()
                                        : newEntry.getContent());

                oldEntry.setTitle(newEntry.getTitle() != null &&
                        !newEntry.getTitle().equals("") ? newEntry.getTitle()
                                : oldEntry.getTitle());

                oldEntry.setDate(new Date());
                oldEntry = journalRepositiory.save(oldEntry);
                return true;
            }

            return false;
        } catch (Exception e) {

            throw new RuntimeException("An error occurred while updating the entry.", e);
        }

    }

}

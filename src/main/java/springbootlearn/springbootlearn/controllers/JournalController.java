package springbootlearn.springbootlearn.controllers;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springbootlearn.springbootlearn.entity.JournalEntry;
import springbootlearn.springbootlearn.entity.User;
import springbootlearn.springbootlearn.services.JournalServices;
import springbootlearn.springbootlearn.services.UserServices;

@RestController
@RequestMapping("/api/v1/journal")
public class JournalController {

   @Autowired
   private JournalServices journalServices;
   @Autowired
   private UserServices userServices;

   @GetMapping("/user")
   public ResponseEntity<List<JournalEntry>> getAlJournalEntriesOfUser() {
      Authentication authentication = 
      SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();

      User user = userServices.findByUserName(userName);

      if (user != null) {
         List<JournalEntry> allEntries = user.getJournalEntries();
         if (allEntries.size() > 0) {
            return new ResponseEntity<List<JournalEntry>>(allEntries, HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

   @PostMapping("/create")
   public ResponseEntity<?> create(@RequestBody JournalEntry journalEntry) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();
      try {
         return new ResponseEntity<>(journalServices.saveEntry(journalEntry, userName), HttpStatus.CREATED);
      } catch (Exception e) {
         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
   }

   @GetMapping("/{id}")

   public ResponseEntity<JournalEntry> getOne(@PathVariable String id) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();

      User user = userServices.findByUserName(userName);

      List<JournalEntry> collect = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id))
            .collect(Collectors.toList());

      if (!collect.isEmpty()) {
         Optional<JournalEntry> entry = journalServices.getOne(id);
         return new ResponseEntity<JournalEntry>(entry.get(), HttpStatus.OK);

      }

      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<?> delete(@PathVariable String id) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();
      try {
         return new ResponseEntity<Boolean>(journalServices.deleteOne(id, userName), HttpStatus.OK);
      } catch (Exception e) {
         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
   }

   @PutMapping("/update/{id}")
   public ResponseEntity<?> update(@PathVariable String id, @RequestBody JournalEntry journalEntry) {

      try {
         boolean isUpdated = journalServices
               .updateOne(id, journalEntry);

         if (isUpdated) {
            return new ResponseEntity<Boolean>(isUpdated, HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } catch (Exception e) {
         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
   }

}

// ? <------------------------>
// * <?> this means optional
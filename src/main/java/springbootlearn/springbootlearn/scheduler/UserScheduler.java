package springbootlearn.springbootlearn.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import springbootlearn.springbootlearn.cache.AppCache;
import springbootlearn.springbootlearn.emuns.Sentiment;
import springbootlearn.springbootlearn.entity.JournalEntry;
import springbootlearn.springbootlearn.entity.User;
import springbootlearn.springbootlearn.services.EmailService;

import springbootlearn.springbootlearn.services.UserServices;

@Component

public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserServices userServices;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    // @Scheduled(cron = "0 * * ? * * ")
    public void fetchUsersAndSendSentimentAnalysisMail() {
        List<User> userForSA = userServices.getUserForSentimentAnalysis();
        for (User user : userForSA) {
            List<JournalEntry> journalEntries = user.getJournalEntries();

            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getLocalDateTime().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();

            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;

            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {

                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();

                }

                if (mostFrequentSentiment != null) {
                    emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days",
                            "Sentiment: " + mostFrequentSentiment.toString() + maxCount);
                }
            }

        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearCache() {
        appCache.initialize();
    }
}

// 0 0 9 * * SUN
// sec min hours day of the month month week
package com.koreait.schedulStat;

import com.koreait.models.board.BoardDao;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BoardScheduler {
    private final BoardDao boardDao;

    private final DailyCount dailyCount;

    @Scheduled(cron = "0 0 1 * * *")
    public void process(){
        Long count = boardDao.getTotal();
        System.out.println("dCount = " + count);

        dailyCount.total(count);
    }
}
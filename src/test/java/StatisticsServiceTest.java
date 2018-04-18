import com.homework.entity.Transaction;
import com.homework.service.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.DoubleSummaryStatistics;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class StatisticsServiceTest {

    @TestConfiguration
    static class FuelConsumptionServiceImplContextConfiguration {

        @Bean
        public StatisticsService StatisticsService() {
            return new StatisticsService();
        }
    }

    @Autowired
    StatisticsService statisticsService;

    @Test
    public void testStatisticsResults() {

        statisticsService.addTransaction(new Transaction(100.59D, System.currentTimeMillis()));
        statisticsService.addTransaction(new Transaction(200.34D, System.currentTimeMillis()));
        statisticsService.addTransaction(new Transaction(300.99D, System.currentTimeMillis()));
        statisticsService.addTransaction(new Transaction(435.23D, System.currentTimeMillis()));
        statisticsService.addTransaction(new Transaction(542.55D, System.currentTimeMillis()));

        statisticsService.addTransaction(new Transaction(100D, System.currentTimeMillis() - 70_000));

        DoubleSummaryStatistics summaryStatistics = statisticsService.getStatistics();

        assertEquals(summaryStatistics.getCount(), 5);
        assertEquals(summaryStatistics.getSum(), 1579.7D, 0D);
        assertEquals(summaryStatistics.getMin(), 100.59D, 0D);
        assertEquals(summaryStatistics.getMax(), 542.55D, 0D);
        assertEquals(summaryStatistics.getAverage(), 315.94D, 0D);
    }
}

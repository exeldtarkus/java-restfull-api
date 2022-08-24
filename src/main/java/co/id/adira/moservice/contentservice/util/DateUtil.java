package co.id.adira.moservice.contentservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DateUtil {

	public long getDifferenceDays(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

  public Date datePlus(Date d1, int totalDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d1);
    calendar.add(Calendar.DATE, totalDate);
    Date todayPlus = calendar.getTime();
		return todayPlus;
	}

}

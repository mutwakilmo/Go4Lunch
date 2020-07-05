package com.mutwakilandroiddev.go4lunch;





import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;


public class Go4lunchUnitTest {




    @Test
    public void validateDateParsing() throws Exception{
        // Date format for the date you get from the API.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        // Give a sample date as a String because you get date as a String from the API
        // to SimpleDateFormat you created above and parse it.
        Date date = simpleDateFormat.parse("20120-11-27T20:13:24+0330");

        // If this is successful, then the Date object will not be null.
        // If it fails, then it will be null.
        Assert.assertNotNull(date);
    }


    @Test
    public void timeLunchChat() {
        String expectedSENDDate = "20201127";

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.DAY_OF_MONTH, 27);
        String mFormat = "yyyyMMdd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mFormat, Locale.FRANCE);
        String actualEndDate = simpleDateFormat.format(calendar.getTime());

        assertEquals(expectedSENDDate, actualEndDate);
    }




}

package com.example.student1.todolist;

import com.example.student1.todolist.validators.Validator;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void checkInheritance() throws Exception {
        Task task = new Task();
        assertEquals(true, task.getStatus() == TaskObject.TaskStatus.NEW);
        assertEquals(false, task.isDone());
        assertEquals(true, task.isExpired());
    }

    @Test
    public void checkTimeSimple() throws Exception {
        Task task = new Task();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        calendar.add(Calendar.SECOND, 1);
        task.setExpireDate(calendar.getTime());
        assertEquals("7 days", task.getLeftTime());
    }

    @Test
    public void checkTimeExtends() throws Exception {
        Task task = new Task();
        Calendar calendar = Calendar.getInstance();

        task.setExpireDate(calendar.getTime());
        assertEquals("expired", task.getLeftTime());

        calendar.add(Calendar.MINUTE, 2);
        task.setExpireDate(calendar.getTime());
        assertEquals("1 minute", task.getLeftTime());

        calendar.add(Calendar.MINUTE, 2);
        task.setExpireDate(calendar.getTime());
        assertEquals("3 minutes", task.getLeftTime());

        calendar.add(Calendar.HOUR, 1);
        task.setExpireDate(calendar.getTime());
        assertEquals("1 hour", task.getLeftTime());

        calendar.add(Calendar.HOUR, 2);
        task.setExpireDate(calendar.getTime());
        assertEquals("3 hours", task.getLeftTime());

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        task.setExpireDate(calendar.getTime());
        assertEquals("1 day", task.getLeftTime());

        calendar.add(Calendar.DAY_OF_MONTH, 9);
        task.setExpireDate(calendar.getTime());
        assertEquals("10 days", task.getLeftTime());
    }

    @Test
    public void testNumberValidator() throws Exception{

        Validator<Integer> validator =  new Validator.NumberValidatorBuilder()
                .setMaxValue(1)
                .setMinValue(10)
                .build();

        Validator<Double> doubleValidator = new Validator.NumberValidatorBuilder<Double>()
                .setRange(1.0, 10.0)
                .build();

        System.out.println(validator.validate(12, "Integer"));
        System.out.println(doubleValidator.validate(5.0, "Double"));

        Assert.assertEquals(false, validator.validate(12, "Integer"));
        Assert.assertEquals(true, doubleValidator.validate(5.0, "Double"));
    }

    @Test
    public void testValidatorForDate(){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        Validator<Date> validator = new Validator.DateValidatorBuilder()
                .setDate(calendar.getTime())
                .setDate(calendar.getTimeInMillis())
                .build();

        Assert.assertEquals(false, validator.validate(calendar.getTime()));

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Assert.assertEquals(true, validator.validate(calendar.getTime()));
    }
}
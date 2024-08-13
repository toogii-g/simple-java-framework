package framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Define the @Scheduled annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Scheduled {
    long fixedRate() default -1; // Interval in milliseconds
    long fixedDelay() default -1; // Delay after the last execution
    String cron() default ""; // Cron expression for complex schedules
}

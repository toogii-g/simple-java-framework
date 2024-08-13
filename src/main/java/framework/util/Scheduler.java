package framework.util;
import framework.annotation.Scheduled;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    public void schedule(Object bean) {
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Scheduled.class)) {
                Scheduled scheduled = method.getAnnotation(Scheduled.class);

                if (!scheduled.cron().isEmpty()) {
                    long interval = parseCronExpression(scheduled.cron());
                    if (interval > 0) {
                        executorService.scheduleAtFixedRate(() -> invokeMethod(bean, method), 0, interval, TimeUnit.SECONDS);
                    }
                } else if (scheduled.fixedRate() > 0) {
                    executorService.scheduleAtFixedRate(() -> invokeMethod(bean, method), 0, scheduled.fixedRate(), TimeUnit.MILLISECONDS);
                } else if (scheduled.fixedDelay() > 0) {
                    executorService.scheduleWithFixedDelay(() -> invokeMethod(bean, method), 0, scheduled.fixedDelay(), TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    private long parseCronExpression(String cron) {
        String[] parts = cron.split(" ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid cron expression. Expected format: \"<seconds> <minutes>\"");
        }
        int seconds = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return minutes * 60 + seconds;
    }

    private void invokeMethod(Object bean, Method method) {
        try {
            method.setAccessible(true);
            method.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}

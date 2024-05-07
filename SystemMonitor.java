import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.logging.Logger;

public class SystemMonitor {
    private static final Logger logger = Logger.getLogger(SystemMonitor.class.getName());

    public static void logSystemMemory() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        logger.info("Heap Memory: " + heapMemoryUsage.getUsed() + " / " + heapMemoryUsage.getMax());
        logger.info("Non-Heap Memory: " + nonHeapMemoryUsage.getUsed() + " / " + nonHeapMemoryUsage.getMax());
    }

    public static void logThreadCount() {
        int threadCount = ManagementFactory.getThreadMXBean().getThreadCount();
        logger.info("Current thread count: " + threadCount);
    }
}

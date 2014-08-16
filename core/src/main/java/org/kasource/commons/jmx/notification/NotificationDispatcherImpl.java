package org.kasource.commons.jmx.notification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

import org.kasource.commons.jmx.annotation.JmxAttribute;
import org.kasource.commons.jmx.annotation.JmxBean;
import org.kasource.commons.jmx.annotation.JmxNotification;


@JmxBean(objectName="Ka-Commons:name=JmxNotificationDispatcher",
         description="Shared JMX Notification Dispatcher that sends notifications "
                     +"on behalf of other beans. The default executor is "
                     +"a ThreadPool sending asyncrouns Notifications ")
public class NotificationDispatcherImpl implements NotificationDispatcher  {
    
    public static final int CORE_POOL_SIZE = 1;
    public static final int MAX_POOL_SIZE = 10;
    public static final long KEEP_ALIVE_TIME = 2L;
    public static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    
    private final NotificationBroadcasterSupport mbs;
    private final Map<Class<? extends Notification>, MBeanNotificationInfo> notificationInfo = new ConcurrentHashMap<Class<? extends Notification>, MBeanNotificationInfo>();
    private boolean autoRegisterNotifications = true;
    private final int corePoolSize = CORE_POOL_SIZE;
    private final int maxPoolSize = MAX_POOL_SIZE;
    private final long keepAliveTime = KEEP_ALIVE_TIME;
    private final TimeUnit keepAliveTimeUnit = KEEP_ALIVE_TIME_UNIT;
    
    public NotificationDispatcherImpl() {
        Executor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, 
                                          MAX_POOL_SIZE, 
                                          KEEP_ALIVE_TIME, 
                                          KEEP_ALIVE_TIME_UNIT, 
                                          new LinkedBlockingQueue<Runnable>());
        mbs = new NotificationBroadcasterSupport(executor);
    }
    
    public NotificationDispatcherImpl(Executor executor) {
        mbs = new NotificationBroadcasterSupport(executor);
    }

   
    @Override
    public void addNotificationListener(NotificationListener notificationlistener,
                NotificationFilter notificationfilter, Object obj) {
        mbs.addNotificationListener(notificationlistener, notificationfilter, obj);
    }

   
    @Override
    public void removeNotificationListener(NotificationListener notificationlistener) throws ListenerNotFoundException {
        mbs.removeNotificationListener(notificationlistener);
    }

    
    @Override
    public void removeNotificationListener(NotificationListener notificationlistener,
                NotificationFilter notificationfilter, Object obj) throws ListenerNotFoundException {
        mbs.removeNotificationListener(notificationlistener, notificationfilter, obj);
    }

   
    
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        MBeanNotificationInfo[] info = new MBeanNotificationInfo[notificationInfo.size()];
        notificationInfo.values().toArray(info);
        return info;
    }

  
    public void registerNotification(Class<? extends Notification> notificationClass) {
        String name = notificationClass.getSimpleName();
        String description = "";
        JmxNotification jmxNotification = notificationClass.getAnnotation(JmxNotification.class);
        if (jmxNotification != null) {
            if (!jmxNotification.name().isEmpty()) {
                name = jmxNotification.name(); 
            }
            description = jmxNotification.description();
        }
        MBeanNotificationInfo info = new MBeanNotificationInfo(new String[]{notificationClass.getName()}, name, description);
        notificationInfo.put(notificationClass, info);
    }
    
    @Override
    public void sendNotification(Notification notification) {
        if (autoRegisterNotifications && !notificationInfo.containsKey(notification.getClass())) {
            registerNotification(notification.getClass());
        }
        mbs.sendNotification(notification);
    }

    /**
     * Sets autoRegisterNotifications.
     *  
     * @param autoRegisterNotifications true to register notification information automatically when sending, else false.
     */
    public void setAutoRegisterNotifications(boolean autoRegisterNotifications) {
        this.autoRegisterNotifications = autoRegisterNotifications;
    }

    /**
     * Returns true to register notification information automatically when sending, else false.
     * 
     * @return true to register notification information automatically when sending
     */
    @JmxAttribute(description = "True to register notification information automatically when sending, esle false")
    public boolean isAutoRegisterNotifications() {
        return autoRegisterNotifications;
    }

    /**
     * The core pool size of the thread pool that dispatches notifications
     * 
     * @return the corePoolSize
     */
    @JmxAttribute(description="The core pool size of the thread pool that dispatches notifications")
    public int getCorePoolSize() {
        return corePoolSize;
    }

    /**
     * @return the maxPoolSize
     */
    @JmxAttribute(description="The max pool size of the thread pool that dispatches notifications")
    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    /**
     * @return the keepAliveTime
     */
    @JmxAttribute(description="The keep alive time of threads in the thread pool that dispatches notifications")
    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    /**
     * @return the keepAliveTimeUnit
     */
    @JmxAttribute(description="The keep alive time time unit of the thread pool that dispatches notifications")
    public TimeUnit getKeepAliveTimeUnit() {
        return keepAliveTimeUnit;
    }
    
    
}

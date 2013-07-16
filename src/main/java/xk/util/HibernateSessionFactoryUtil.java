package xk.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;


/**Hibernate4以上全新配置
 * 
 */
public class HibernateSessionFactoryUtil {
	private static final SessionFactory sf;
	static {
		try {
			Configuration cfg = new Configuration().configure();
			sf = cfg.buildSessionFactory(new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry());
		} catch (Throwable ex) {
			/*
			 * 需要 捕获Throwable对象, 否则捕获不到 Error及其子类和NoClassDefFoundError类型的错误
			 */
			throw new ExceptionInInitializerError(ex);
		}
	}

	private HibernateSessionFactoryUtil() {
	}

	public static SessionFactory getSessionFactory() {
		return sf;
	}
}
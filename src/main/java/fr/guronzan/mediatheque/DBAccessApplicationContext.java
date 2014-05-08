package fr.guronzan.mediatheque;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class DBAccessApplicationContext {
	private static ApplicationContext context = null;

	private DBAccessApplicationContext() {
		context = new ClassPathXmlApplicationContext("spring.xml");
	}

	@SuppressWarnings({ "unchecked" })
	public static <T> T getBean(final String clazz) {
		if (context == null) {
			new DBAccessApplicationContext();
		}
		return (T) context.getBean(clazz);
	}

	public static <T> T getBean(final Class<T> clazz) {
		if (context == null) {
			new DBAccessApplicationContext();
		}
		return context.getBean(clazz);
	}
}

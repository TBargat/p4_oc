package com.dummy.myerp.testbusiness.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.TransactionManager;

/**
 * Spring beans registry
 */
public final class SpringRegistry {

	/** Logger Log4j for the class */
	private static final Logger LOGGER = LogManager.getLogger(SpringRegistry.class);

	/** Singleton Design Pattern to have a single instance */
	private static final SpringRegistry INSTANCE = new SpringRegistry();

	/** Application context files */
	private static final String CONTEXT_APPLI_LOCATION = "classpath:/bootstrapContext.xml";

	/** Spring Context */
	private ApplicationContext contextAppli;

	// ==================== Spring Beans' ID ====================

	/**
	 * Constructor.
	 */
	private SpringRegistry() {
		super();
		SpringRegistry.LOGGER.debug("[DEBUT] SpringRegistry() - Initialisation du contexte Spring");
		this.contextAppli = new ClassPathXmlApplicationContext(SpringRegistry.CONTEXT_APPLI_LOCATION);
		SpringRegistry.LOGGER.debug("[FIN] SpringRegistry() - Initialisation du contexte Spring");
	}

	/**
	 * Return the singleton
	 *
	 * @return SpringRegistry
	 */
	protected static final SpringRegistry getInstance() {
		return SpringRegistry.INSTANCE;
	}

	/**
	 * Initialize and load the context
	 *
	 * @return ApplicationContext
	 */
	public static final ApplicationContext init() {
		// Call the static initialization and thus, the context loading
		return getInstance().contextAppli;
	}

	/**
	 * Bean gotten with Spring.
	 *
	 * @param pBeanId ID of the bean
	 * @return Object
	 */
	protected static Object getBean(String pBeanId) {
		SpringRegistry.LOGGER.debug("[DEBUT] SpringRegistry.getBean() - Bean ID : " + pBeanId);
		Object vBean = SpringRegistry.getInstance().contextAppli.getBean(pBeanId);
		SpringRegistry.LOGGER.debug("[FIN] SpringRegistry.getBean() - Bean ID : " + pBeanId);
		return vBean;
	}

	/**
	 * Return the instance of {@link BusinessProxy}
	 *
	 * @return {@link BusinessProxy}
	 */
	public static BusinessProxy getBusinessProxy() {
		return (BusinessProxy) SpringRegistry.getBean("BusinessProxy");
	}

	/**
	 * Return the instance of {@link TransactionManager}
	 *
	 * @return {@link TransactionManager}
	 */
	public static TransactionManager getTransactionManager() {
		return (TransactionManager) SpringRegistry.getBean("TransactionManager");
	}
}

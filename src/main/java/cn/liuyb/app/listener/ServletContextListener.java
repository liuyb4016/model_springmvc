package cn.liuyb.app.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServletContextListener implements javax.servlet.ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(ServletContextListener.class);

	public void contextInitialized(ServletContextEvent sce) {
		logger.debug("initializing spring context...");
		
        ServletContext servletContext = sce.getServletContext();
        setServletContext(servletContext);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}
	
	
    public static void setServletContext(ServletContext context) {
        ApplicationContext ctx = 
            WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        logger.debug("===================================");
        logger.info("Count of beans in spring = " + beanDefinitionNames.length);
        for(int i = 0 ; i < beanDefinitionNames.length;i++) {           
        	logger.debug("definitionBean"+i+"  = "  + beanDefinitionNames[i]);           
        }
        logger.debug("===================================");
        /** 
         //初始化其他数据

        */
        logger.debug("initialization of spring context finished!");
    }

}

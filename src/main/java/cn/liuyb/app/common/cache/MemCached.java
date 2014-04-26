package cn.liuyb.app.common.cache;

import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import cn.liuyb.app.common.utils.Slf4jLogUtils;

public class MemCached {
    private static Logger logger = Slf4jLogUtils.getLogger(MemCached.class);
  
    public static MemCached INSTANCE = new MemCached();

    @Resource(name="memcachedClient")
    private XMemcachedClient memcachedClient;
    
    @Value("${xmemcached.expiry}")
    private Integer expiry=0;
    @Value("${xmemcached.servers}")
    public String servers;
    
    private MemCached(){}
    
    public Object get(String key) {
    	
    	if(StringUtils.isNotBlank(key)){
    		key=key.replace(" ", "_");
    	}
        logger.debug("get('{}')", key);
        try {
			return memcachedClient.get(key);
		} catch (TimeoutException e) {
			logger.error("TimeoutException get('{}')", key);
		} catch (InterruptedException e) {
			logger.error("InterruptedException get('{}')", key);
		} catch (MemcachedException e) {
			logger.error("MemcachedException get('{}')", key);
		} catch (Exception e){
			logger.error("Exception key={}, e={}", key, e);
		}
        return null;
    }

    public void set(String key, Object value) {

    	if(StringUtils.isNotBlank(key)){
    		key=key.replace(" ", "_");
    	}
        logger.debug("set('{}', '{}')", key, value);
        try {
        	memcachedClient.set(key, this.expiry, value);
		} catch (TimeoutException e) {
			logger.error("TimeoutException set('{}', '{}')", key, value+", "+this.expiry);
		} catch (InterruptedException e) {
			logger.error("InterruptedException set('{}, '{}')", key, value+", "+this.expiry);
		} catch (MemcachedException e) {
			logger.error("MemcachedException set('{}, '{}')", key, value+", "+this.expiry);
		} catch (Exception e){
			logger.error("Exception key={}, e={}", key, e);
		}
    }

    public void set(String key, Object value,Long expiry) {
    	if(StringUtils.isNotBlank(key)){
    		key=key.replace(" ", "_");
    	}
        logger.debug("set('{}', '{}')", key, value+", "+expiry);
        try {
        	memcachedClient.set(key, expiry.intValue(), value);
		} catch (TimeoutException e) {
			logger.error("TimeoutException set('{}', '{}')", key, value+", "+expiry.intValue());
		} catch (InterruptedException e) {
			logger.error("InterruptedException set('{}, '{}')", key, value+", "+expiry.intValue());
		} catch (MemcachedException e) {
			logger.error("MemcachedException set('{}, '{}')", key, value+", "+expiry.intValue());
		} catch (Exception e){
			logger.error("Exception key={}, e={}", key, e);
		}
    }


    public void delete(String key) {
    	if(StringUtils.isNotBlank(key)){
    		key=key.replace(" ", "_");
    	}
        logger.debug("delete('{}')", key);
        try {
        	memcachedClient.delete(key);
		} catch (TimeoutException e) {
			logger.error("TimeoutException delete('{}')", key);
		} catch (InterruptedException e) {
			logger.error("InterruptedException delete('{}')", key);
		} catch (MemcachedException e) {
			logger.error("MemcachedException delete('{}')", key);
		} catch (Exception e){
			logger.error("Exception key={}, e={}", key, e);
		}
    }
    
   /* private XMemcachedClient client = null;
    
    @Value("${memcached.servers}")
    public String servers;
    
    @Value("${memcached.weights}")
    private String weights;
    
    @Value("${memcached.expiry}")
    private Integer expiry=0;
    
    @Value("${memcached.open}")
    private Boolean open;
    
    @PostConstruct
    void init() {
    	if(open!=null && !open){
    		return ;
    	}
        try {
            doInit();
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
    }
    
    private void doInit() throws IOException {
    	if(open!=null && !open){
    		return ;
    	}
        String[] servers = this.servers.split(",");
        String[] ws = this.weights.split(",");
        Integer[] weights = new Integer[ws.length];
        for (int i = 0; i < ws.length; i++) {
            weights[i] = Integer.valueOf(ws[i]);
        }
        client = new XMemcachedClient(AddrUtil.getAddresses(this.servers.replace(",", " ")));
        for (int i = 0; i < ws.length; i++) {
        	client.setServerWeight(servers[i], Integer.valueOf(ws[i]));
        }
        client.setPrimitiveAsString(false);
    }

    public Object get(String key) {
    	if(open!=null && !open){
    		return null;
    	}
    	
    	if(StringUtils.isNotBlank(key)){
    		key=key.replace(" ", "_");
    	}
        logger.debug("get('{}')", key);
        try {
			return client.get(key);
		} catch (TimeoutException e) {
			logger.error("TimeoutException get('{}')", key);
		} catch (InterruptedException e) {
			logger.error("InterruptedException get('{}')", key);
		} catch (MemcachedException e) {
			logger.error("MemcachedException get('{}')", key);
		} catch (Exception e){
			logger.error("Exception key={}, e={}", key, e);
		}
        return null;
    }

    public void set(String key, Object value) {
    	if(open!=null && !open){
    		return ;
    	}
    	if(StringUtils.isNotBlank(key)){
    		key=key.replace(" ", "_");
    	}
        logger.debug("set('{}', '{}')", key, value);
        try {
			client.set(key, this.expiry, value);
		} catch (TimeoutException e) {
			logger.error("TimeoutException set('{}', '{}')", key, value+", "+this.expiry);
		} catch (InterruptedException e) {
			logger.error("InterruptedException set('{}, '{}')", key, value+", "+this.expiry);
		} catch (MemcachedException e) {
			logger.error("MemcachedException set('{}, '{}')", key, value+", "+this.expiry);
		} catch (Exception e){
			logger.error("Exception key={}, e={}", key, e);
		}
    }

    public void set(String key, Object value,Long expiry) {
    	if(open!=null && !open){
    		return ;
    	}
    	
    	if(StringUtils.isNotBlank(key)){
    		key=key.replace(" ", "_");
    	}
        logger.debug("set('{}', '{}')", key, value+", "+expiry);
        try {
			client.set(key, expiry.intValue(), value);
		} catch (TimeoutException e) {
			logger.error("TimeoutException set('{}', '{}')", key, value+", "+expiry.intValue());
		} catch (InterruptedException e) {
			logger.error("InterruptedException set('{}, '{}')", key, value+", "+expiry.intValue());
		} catch (MemcachedException e) {
			logger.error("MemcachedException set('{}, '{}')", key, value+", "+expiry.intValue());
		} catch (Exception e){
			logger.error("Exception key={}, e={}", key, e);
		}
    }


    public void delete(String key) {
    	if(open!=null && !open){
    		return ;
    	}
    	
    	if(StringUtils.isNotBlank(key)){
    		key=key.replace(" ", "_");
    	}
        logger.debug("delete('{}')", key);
        try {
			client.delete(key);
		} catch (TimeoutException e) {
			logger.error("TimeoutException delete('{}')", key);
		} catch (InterruptedException e) {
			logger.error("InterruptedException delete('{}')", key);
		} catch (MemcachedException e) {
			logger.error("MemcachedException delete('{}')", key);
		} catch (Exception e){
			logger.error("Exception key={}, e={}", key, e);
		}
    }*/
    
    /*
	
    private static Logger logger = Slf4jLogUtils.getLogger(MemCachedDangaImpl.class);
    private MemCachedDangaImpl(){};
    public static final MemCachedDangaImpl INSTANCE = new MemCachedDangaImpl();
    
    private final MemCachedClient client = new MemCachedClient();
    
    @Value("${memcached.servers}")
    public String servers;
    
    @Value("${memcached.weights}")
    private String weights;
    
    @Value("${memcached.initConn}")
    private String initConn;
    
    @Value("${memcached.minConn}")
    private String minConn;
    
    @Value("${memcached.maxConn}")
    private String maxConn;
    
    @Value("${memcached.maxIdle}")
    private String maxIdle;
    
    @Value("${memcached.maintSleep}")
    private String maintSleep;

    @Value("${memcached.maintSleep}")
    private String nagle;
    
    @Value("${memcached.maintSleep}")
    private String socketTO;
    
    @Value("${memcached.maintSleep}")
    private String socketConnectTO;
    
    @PostConstruct
    void init() {
        try {
            doInit();
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
    }
    
    private void doInit() {
        // server list and weights
        String[] servers = this.servers.split(",");
        String[] ws = this.weights.split(",");
        
        Integer[] weights = new Integer[ws.length];
        
        for (int i = 0; i < ws.length; i++) {
            weights[i] = Integer.valueOf(ws[i]);
        }

        // grab an instance of our connection pool
        SockIOPool pool = SockIOPool.getInstance();

        // set the servers and the weights
        pool.setServers(servers);
        pool.setWeights(weights);

        // set some basic pool settings
        // 5 initial, 5 min, and 250 max conns
        // and set the max idle time for a conn
        // to 6 hours
        pool.setInitConn(Integer.parseInt(initConn));
        pool.setMinConn(Integer.parseInt(minConn));
        pool.setMaxConn(Integer.parseInt(maxConn));
        pool.setMaxIdle(Integer.parseInt(maxIdle));

        // set the sleep for the maint thread
        // it will wake up every x seconds and
        // maintain the pool size
        pool.setMaintSleep(Integer.parseInt(maintSleep));

        // set some TCP settings
        // disable nagle
        // set the read timeout to 3 secs
        // and don’t set a connect timeout
        pool.setNagle(Boolean.parseBoolean(nagle));
        pool.setSocketTO(Integer.parseInt(socketTO));
        pool.setSocketConnectTO(Integer.parseInt(socketConnectTO));

        // initialize the connection pool
        pool.initialize();
        
    }

    public Object get(String key) {
        logger.debug("get('{}')", key);
        return client.get(key);
    }

    public void set(String key, Object value) {
        logger.debug("set('{}', '{}')", key, value);
        client.set(key, value);      
    }

    public void set(String key, Object value,Long expiry) {
        logger.debug("set('{}', '{}')", key, value+", "+expiry);
        client.set(key, value, new Date(expiry)); 
    }


    public void delete(String key) {
        logger.debug("delete('{}')", key);
        client.delete(key);
    }
    
     */
}

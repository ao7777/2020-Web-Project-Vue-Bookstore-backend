package org.reins.orm;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletFilterConfig {
    @Bean
    public  StrutsPrepareAndExecuteFilter strutsPrepareAndExecuteFilter(){
        return new StrutsPrepareAndExecuteFilter();
    }
}

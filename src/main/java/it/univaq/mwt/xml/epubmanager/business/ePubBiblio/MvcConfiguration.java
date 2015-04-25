package it.univaq.mwt.xml.epubmanager.business.ePubBiblio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.xslt.XsltView;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

@Configuration
@ComponentScan(basePackages="net.codejava.spring")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{
    
    @Bean
    public ViewResolver getXSLTViewResolver(){
         
        XsltViewResolver xsltResolover = new XsltViewResolver();
        xsltResolover.setOrder(1);
        xsltResolover.setSourceKey("EpubBiblio");
         
        xsltResolover.setViewClass(XsltView.class);
        xsltResolover.setViewNames(new String[] {"EpubXSLT"});
        xsltResolover.setPrefix("/WEB-INF/views/xsl/");
        xsltResolover.setSuffix(".xsl");
         
        return xsltResolover;
    }
     
    @Bean
    public ViewResolver getJSPViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(2);
        return resolver;
    }
     
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}

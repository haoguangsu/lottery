package com.hgs.caipiao.config;

/**
 * @author ：206612
 * @date ：Created in 2021/9/29 16:53
 * @description：DataSourceConfig
 */

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ServletComponentScan
@MapperScan(basePackages = "com.hgs.caipiao.dao", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {
    /**
     * 创建SqlSessionTemplate。
     *
     * @param sqlSessionFactory 依赖注入
     * @return 生成接口模板
     */
    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") final SqlSessionFactory sqlSessionFactory)
    {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.SIMPLE);
    }

    /**
     * 依赖注入外部数据源。
     *
     * @param dataSource
     * @return SqlSessionFactory
     */
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("druidDataSource") final DataSource dataSource) throws Exception
    {
        VFS.addImplClass(SpringBootVFS.class);
        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 引入xml文件
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:xml/*.xml"));
        bean.setTypeAliasesPackage("com.hgs.caipiao");
        return bean.getObject();
    }

    /**
     * @return 返回阿里druid数据源
     */
    @Bean(name = "druidDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource druidDataSource()
    {
        return new DruidDataSource();
    }

    @Bean(name = "txManager")
    public PlatformTransactionManager txManager(DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }

}

package config;


import model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.print.attribute.standard.PrinterURI;
import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    // configuring "Step" and "Job" for the "Spring Batch" to work on
    @Autowired
    private StepBuilderFactory sbf;
    @Autowired
    private JobBuilderFactory jbf;

    // creating the "Job" to return us the "job"
    @Bean
    public Job job() {
        return jbf.get("job1")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    // creating "Step" to return the step
    @Bean
    public Step step() {
        return sbf.get("step1")
                .<Product, Product>chunk(3)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    // Spring Batch - "itemReader" using "Spring Batch module"
    @Bean
    public ItemReader<Product> reader() {
        //    here <Product> is the type of data it is returning, here a model object.

        FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
        // helps in reading the files
        // the type of returning object here is "Product"

        reader.setResource(new ClassPathResource("products.csv"));
        // setting the path of the that is to be read

        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();
        // line mapper will read thro the file line-by-line

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("id", "name", "description", "price");

        BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        // this will take the values from "lineTokenizer" and set them to the Bean
        fieldSetMapper.setTargetType(Product.class);
        // "fieldSetMapper" requires only the target class type, here setting the target class type to "Product.class"

        // setting the "lineTokenizer" and "fieldSetMapper" to "lineMapper"
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        // setting the "lineWrapper" to "reader"
        reader.setLineMapper(lineMapper);

        return reader;
    }

    // Spring Batch - "itemProcessor" using "Spring Batch Module"
    @Bean
    public ItemProcessor<Product, Product> processor() {
        // using Lambda expression function to return the price of the product at a 10% discount
        return (p) -> {
            p.setPrice(p.getPrice()-p.getPrice()*10/100);
            return p;
        };
    }

    // Spring Batch - "itemWriter" using "Spring Batch Module"
    @Bean
    public ItemWriter<Product> writer() {

        // creating a new "JDBC Batch ItemWriter"
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter();

        // setting the "dataSource - Bean" on the writer here
        writer.setDataSource(dataSource());

        // defining the data source from where it can get the data
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());

        // setting the SQL which will be used to insert the data into the database
        writer.setSql("INSERT INTO PRODUCT (ID, NAME, DESCRIPTION, PRICE) VALUES {:id, :name, :description, :price}");

        return writer;
    }

    // configuring the data source, so that it has the info about how to connect to the database
    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // setting the properties of the data source
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
        dataSource.setUsername("root");
        dataSource.setPassword("MySQL@#0012");
        return dataSource;
    }

}

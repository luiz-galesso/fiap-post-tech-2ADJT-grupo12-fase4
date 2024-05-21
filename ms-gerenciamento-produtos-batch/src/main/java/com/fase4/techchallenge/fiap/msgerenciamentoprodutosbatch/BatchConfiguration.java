package com.fase4.techchallenge.fiap.msgerenciamentoprodutosbatch;

import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Configuration
public class BatchConfiguration
{
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BatchConfiguration.class);
    @Value("${filebatch.volume}")
    private String volume;

    @Value("${filebatch.name}")
    private String fileName;

    @Bean
    Job inserirProdutos(JobRepository jobRepository, Step step) {
        return new JobBuilder("inserirProdutos", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    Step step(JobRepository jobRepository,
              PlatformTransactionManager transactionManager,
              ItemReader<Produto> itemReader,
              ItemProcessor<Produto, Produto> itemProcessor,
              ItemWriter<Produto> itemWriter) {
        return new StepBuilder("step", jobRepository)
                .<Produto, Produto>chunk(20, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    ItemReader<Produto> itemReader() {
        BeanWrapperFieldSetMapper<Produto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Produto.class);

        Resource resource = new FileSystemResource(volume + "/" + fileName);

        return new FlatFileItemReaderBuilder<Produto>()
                .name("produtoReader")
                .resource(resource)
                .delimited()
                .names("descricaoProduto", "marca", "categoria", "quantidade","valorUnitario")
                .linesToSkip(1)
                .fieldSetMapper(fieldSetMapper)
                .build();
    }

    @Bean
    ItemProcessor<Produto, Produto> itemProcessor() {
        return new ProdutoProcessor();
    }

    @Bean
    ItemWriter<Produto> itemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Produto>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .sql("INSERT INTO tb_produto (cod_produto, descricao_produto, marca, categoria, quantidade, valor_unitario, data_atualizacao) " +
                        "VALUES (NEXTVAL('produto_sequence'), :descricaoProduto, :marca, :categoria, :quantidade, :valorUnitario, :dataAtualizacao)")
                .build();
    }

}

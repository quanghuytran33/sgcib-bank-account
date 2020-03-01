package com.sgcib.bank.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SqlGroup(@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/clear.sql"))
public class AbstractBankAccountControllerIntegrationTest {

  @LocalServerPort
  protected int port;

  protected String uri;

  protected ObjectMapper mapper = new ObjectMapper();

  @PostConstruct
  protected void init() {
    uri = "http://localhost:" + port + "/api";
  }

}

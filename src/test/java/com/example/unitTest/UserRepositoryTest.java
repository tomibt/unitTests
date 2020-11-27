package com.example.unitTest;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.unitTest.entity.User;
import com.example.unitTest.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.flyway.enabled=false"
})
public class UserRepositoryTest {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private UserRepository userRepository;

	@Test
	void whenSaved_thenFindsByName() {
		User user = new User();
		user.setId(2L);
		user.setName("Dimitar");
		user.setEmail("jdimitar@gmail.com");
		userRepository.save(user);
		
		assertThat(userRepository.findByName("Dimitar")).isNotNull();
	}
	
	@Test
	void whenSaved_thenFindsByNameWithCustomQuery() {
		User user = new User();
		user.setId(2L);
		user.setName("Dimitar");
		user.setEmail("jdimitar@gmail.com");
		userRepository.save(user);
		
		assertThat(userRepository.findByNameCustomQuery("Dimitar")).isNotNull();
	}
	
	@Test
	void whenSaved_thenFindsByNameWithNativeQuery() {
		User user = new User();
		user.setId(2L);
		user.setName("Dimitar");
		user.setEmail("jdimitar@gmail.com");
		userRepository.save(user);
		
		assertThat(userRepository.findByNameNativeQuery("Dimitar")).isNotNull();
	}

	@Test
	void injectedComponentsAreNotNull() {
		assertThat(dataSource).isNotNull();
		assertThat(jdbcTemplate).isNotNull();
		assertThat(entityManager).isNotNull();
		assertThat(userRepository).isNotNull();
	}

}

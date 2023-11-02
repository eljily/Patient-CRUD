package net.sidibrahim.Hospital;

import net.sidibrahim.Hospital.entities.Patient;
import net.sidibrahim.Hospital.repository.PatientRepository;
import net.sidibrahim.Hospital.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.util.pattern.PathPattern;

import java.util.Date;

@SpringBootApplication
public class HospitalApplication implements CommandLineRunner {
	@Autowired
	private PatientRepository patientRepository;

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
///**/En utilisant design pattern builder grace a lombok
		/*Patient patient3 = Patient.builder()
				.nom("Khaled")
				.id(null)
				.score(890)
				.dateNaissance(new Date())
				.malade(false)
				.build();*/
		patientRepository.save(new Patient(null,"Sidahmed",new Date(),true,500));
		patientRepository.save(new Patient(null,"Oumar",new Date(),false,654));
		patientRepository.save(new Patient(null,"Khaled",new Date(),false,236));
		patientRepository.save(new Patient(null,"ZeinDine",new Date(),true,500));
		System.out.println("Nice it's working");
	}
	@Bean
	CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
		PasswordEncoder passwordEncoder=passwordEncoder();
		return args -> {
			jdbcUserDetailsManager
					.createUser(User
					.withUsername("user11")
					.password(passwordEncoder.encode("1234"))
					.roles("USER").build());
			jdbcUserDetailsManager
					.createUser(User
					.withUsername("user22")
					.password(passwordEncoder.encode("1234"))
					.roles("USER").build());
			jdbcUserDetailsManager
					.createUser(User
					.withUsername("ADMIN2")
					.password(passwordEncoder.encode("1234"))
					.roles("USER","ADMIN").build());
		};
	}
	//@Bean
	public CommandLineRunner commandLineRunnerUsersDetails(AccountService accountService){
		return args -> {
			accountService.addNewRole("USER");
			accountService.addNewRole("ADMIN");
			accountService.addNewUser("user111","1234","user@gmail.com","1234");
			accountService.addNewUser("user222","1234","user2@gmail.com","1234");
			accountService.addNewUser("admin333","1234","admin@gmail.com","1234");
			accountService.addRoleToUser("user111","USER");
			accountService.addRoleToUser("user222","USER");
			accountService.addRoleToUser("admin333","USER");
			accountService.addRoleToUser("admin333","ADMIN");
		};
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}

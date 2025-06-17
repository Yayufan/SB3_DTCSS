package tw.com.dtcss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan("tw.com.dtcss")
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class Dtcss2025Applicatioin {
	public static void main(String[] args) {
		SpringApplication.run(Dtcss2025Applicatioin.class, args);
	}
}

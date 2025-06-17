package tw.com.dtcss.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectPropertiesConfig {

	/**
	 * 網路協議,盡量使用https://
	 */
	private String protocol;
	
	/**
	 * 域名 或 子域名 ,例如：zhongfu-pr.com.tw
	 */
	private String domain;
	
	/**
	 * 活動名稱, 例如：topbs2025、ticbcs2025
	 */
	private String title;
	
	/**
	 * 分組數量 也是 單日寄信的上限，一般來說200就好，不要動
	 */
	private int groupSize;

}

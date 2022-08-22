package com.udemy.section14csv2db;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Section14csv2dbApplicationTests {

	// to test the Batching, these two things are important "JobLauncher" and "Job"
	@Autowired
	private JobLauncher launcher;
	@Autowired
	private Job job;

	// test case for - testing the "Spring Batching"
	@Test
	public void testBatch() {

		try {
			launcher.run(job, new JobParametersBuilder().toJobParameters());
		} catch (JobExecutionAlreadyRunningException e) {
			throw new RuntimeException(e);
		} catch (JobRestartException e) {
			throw new RuntimeException(e);
		} catch (JobInstanceAlreadyCompleteException e) {
			throw new RuntimeException(e);
		} catch (JobParametersInvalidException e) {
			throw new RuntimeException(e);
		}
	}

}

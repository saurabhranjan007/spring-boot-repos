package com.udemy.section14;

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

// NOTE: Unit Test vs Integration Test.
// Integration Test - The test cases that are defined here are not unit test cases but the integration one,
//			   		  meaning the test cases are mostly checking for the integration flow of the application rather than
//		 			  testing the individual units..
// Unit Test - "pure unit tests", tests the single component by mocking them out which means if we are testing a certain
//			   controller the call would not go to the controller itself but the mocked version of it. When we do unit tests
//			   we mock functions, classes and all, and then test for the expected values of functionalities.

@SpringBootTest
class Section14ApplicationTests {

//	getting job launcher to launch the job
	@Autowired
	JobLauncher launcher;

//	invoking "Job" itself
	@Autowired
	Job job;

//	testing Spring Batch
	@Test
	public void testBatch() {

//		launching the job
		JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
		try {
			launcher.run(job, jobParameters);
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

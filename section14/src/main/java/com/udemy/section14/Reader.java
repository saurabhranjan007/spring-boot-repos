package com.udemy.section14;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

//NOTE:
//  #. A process are a bunch of tasks that are required for a project, these tasks could be anything like copying data from
//     one file to another one or from a file to a database. or it COULD be transferring of files across.
//  #. "Job" is the main task in a Spring Bat, and that job consists of multiple smaller tasks or steps.
//  #. To configure Batch - we need to configure Step (ItemReader, ItemProcessor, ItemWriter) first, then configure Job (Job Listener)


//  creating an "itemReader" - this can fetch data from a file system or a database. Here using a simple here..
public class Reader implements ItemReader<String> {

    private String[] courses = {"Java Web", "Complete Project", "MERN Stack"};
    private int count;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

//        Reading through the courses array here.
        if(count< courses.length) {
            return courses[count++];
        } else {
            count = 0;
        }
        return null;
    }
}

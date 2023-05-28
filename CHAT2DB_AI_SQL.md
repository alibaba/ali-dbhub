# Chat2db AI SQL Function Instructions 
 Chat2DB contains a series of AI SQL usage functions based on Chatgpt, mainly including SQL, SQL interpretation, SQL optimization and SQL conversion. Using these AI functions, you can check the SQL cost of natural language, not just SQL to query the pseudo code; you can interpret SQL as a natural language to help users understand complex SQL; Improve query efficiency; can convert different database types of SQL language, reduce database migration difficulty, and so on. 
 ## Use configuration 
 ### Click Settings
<a><img src="https://img.alicdn.com/imgextra/i2/O1CN01hecdtO1acLegtiP9k_!!6000000003350-2-tps-2400-1600.png" width="100%"/></a>
### Configure Openai_api_key 
 Before using the ChatSQL function
<a><img src="https://img.alicdn.com/imgextra/i2/O1CN01mkVmEd1FTY7SBG6Lq_!!6000000000488-0-tps-1212-788.jpg" width="100%"/></a>

## Natural language to SQL 
 ### function description 
 Enter the natural language, select and pass the inquiry related table structure information, and return the corresponding SQL 
 -Enter a sample example: query the scores of students Xiaoming's subjects, and introduce Course, Score, Student, Student_course 
 -Outage sample: 
 `` `SQL 
 Select score.score 
 From score 
 Inner job state_course on score.course_id = student_course.course_id_id 
 Inner Join Student on Student_Course.Student_id = Student.id 
 Where Student.name = 'Xiao Ming' 
 `` ` 
 ## SQL Explanation 
 ### function description 
 Enter SQL, select and pass the additional information. The additional information can add information to explain which aspect of "Enter SQL". If SQL executes the target, it will return this SQL additional information related explanation 
 -Enter a sample: Select department_name from departments where department_id in (select department_id from embipeees group by department_id having count (*)> 10) 
 -Outing sample: This SQL query statement means: select the department_name column from the departments table, where the value of the department_id column is in the value group of the department_id column in the Emplayees table, and if the number of records in the group is greater than 10, it returns to this. department_id. In other words, this query statement returns the name of more than 10 employees in the Employees table. 
 ## SQL Optimization 
 ### function description 
 Enter the SQL, select and pass the optimization additional information. The additional information can add the aspect and requirements for specific optimization, and it will return this strip SQL additional information related optimization suggestions 
 -Enter sample example: Select * from department order by gmt_create desc 
 -Outage sample: 
   1. Try to avoid using Select *, you should clearly specify columns that need to be queried to reduce unnecessary data transmission and improve query efficiency. 
   2. For the Order By statement, if the results of the query are relatively large, you can consider adding the number of rows returned to the Limit limit to reduce the amount of sorting. 
   3. For the GMT_CREATE field, you can consider adding indexes to improve the efficiency of sorting. 
   4. If the results of the query are often accessed, you can consider using cache technology, such as Redis to reduce the number of database access. 
 ## SQL conversion 
 ### function description 
 Enter the SQL, select the execution and pass the target SQL type and additional information. You can add the custom requirements of conversion to the additional information, which will return the target SQL type and meet the additional requirements of the SQL language. 
 -Enter sample example: Select ifnull (null, "w3schools.com"), the target SQL type is Oracle SQL 
 -Outage sample: select nvl (null, 'w3Schools.com') from dual; 
 ## Chatbot 
 ### function description 
 Enter any Prompt, click the Chatrobot button to return the corresponding results according to the Prompt. Here is the default support context of the chat and dialogue. 
 -Enter sample example: give specific implementation methods for the second optimization suggestion 
 -Outage sample: For the second optimization suggestion, you can consider adding an index in the Student table to speed up the query speed. The specific implementation method is as follows: 

 1. View whether there are already indexes in the Student table. You can use the following command to view: 

    `` `SQL 
    Show index from student; 
    `` ` 

    If you already have indexes, you can skip the next step. 

 2. Add indexes to the Student table. You can use the following command to add: 

    `` `SQL 
    Alter Table Student add index name_index (name); 
    `` ` 

    The name_index here is the name of the index, and name is the name of the index. 

    Note: If the amount of data in the Student table is large, it may take some time to add an index. 

 3. Re -execute the original SQL statement and check whether the query speed has improved. 

    `` `SQL 
    Select score.score from score inner join Student on score.Student_id = Student.id WHERE Student.name = 'Xiao Ming'; 
    `` ` 

    If the query speed is improved, the index is successful.
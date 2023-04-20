# Chat2DB AI SQL功能使用说明
Chat2DB包含一系列基于ChatGPT的AI SQL使用功能，主要包括自然语言转SQL、SQL解释、SQL优化和SQL转换。 使用这些AI功能，可以将自然语言转换成本地查询SQL，而不仅仅是SQL查询伪代码；可以将SQL解释成自然语言，帮助用户理解复杂的SQL；可以针对慢SQL提供全方位的优化建议，提升查询效率；可以转换不同数据库类型的SQL语言，降低数据库迁移难度等等。
## 自然语言转SQL
### 功能描述
输入自然语言，选中执行并传入查询相关的表结构信息，则会返回相应的SQL
- 输入样例：查询学生小明的各科目成绩，并传入关联数据表course,score,student,student_course
- 输出样例：
```sql
SELECT score.score
FROM score
INNER JOIN student_course ON score.course_id = student_course.course_id
INNER JOIN student ON student_course.student_id = student.id
WHERE student.name = '小明'
```
## SQL解释
### 功能描述
输入SQL，选中执行并传入附加信息，附加信息可以添加希望解释《输入SQL》哪方面的信息，如SQL执行目标，则会返回此条SQL附加信息相关的解释
- 输入样例：SELECT department_name FROM departments WHERE department_id IN (SELECT department_id FROM employees GROUP BY department_id HAVING COUNT(*) > 10)
- 输出样例：这个SQL查询语句的意思是：从departments表中选择department_name列，其中department_id列的值在employees表中department_id列的值分组后，如果该组中的记录数大于10，则返回该department_id。换句话说，这个查询语句返回的是在employees表中有超过10个员工的部门名称。
## SQL优化
### 功能描述
输入SQL，选中执行并传入优化附加信息，附加信息可以添加希望具体优化方面和要求，则会返回此条SQL附加信息相关的优化建议
- 输入样例：SELECT * FROM department ORDER BY gmt_create DESC
- 输出样例：
  1. 尽量避免使用SELECT *，应该明确指定需要查询的列，以减少不必要的数据传输和提高查询效率。
  2. 对于ORDER BY语句，如果查询的结果集比较大，可以考虑添加LIMIT限制返回的行数，以减少排序的开销。
  3. 对于gmt_create字段，可以考虑添加索引以提高排序的效率。
  4. 如果查询的结果集经常被访问，可以考虑使用缓存技术，如Redis等，以减少数据库的访问次数。
## SQL转换
### 功能描述
输入SQL，选中执行并传入目标SQL类型和附加信息，附加信息中可以添加转换的自定义要求，则会返回目标SQL类型且符合附加要求的SQL语言
- 输入样例：SELECT IFNULL(NULL, "W3Schools.com")，目标SQL类型为Oracle SQL
- 输出样例：SELECT NVL(NULL, 'W3Schools.com') FROM dual;

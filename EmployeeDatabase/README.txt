This project is Java implementation of employee database.

Every record of employee consist of four fields LastName, FirstName, Department and Job.

User can make use of 4 commands:
 1) Add
    Syntax: Add LastName FirstName Department Job
    -> LastName and FirstName fields are mandatory. If Department or Job is left blank, its value is set unassigned.

 2) Delete
    Syntax: Delete LastName FirstName Department Job
    -> If all four fields are set to *, like Delete * * * *, then all employee records will be deleted.
    -> If the command is ‘Delete * Peter * *’, it implies delete all employees whose FirstName is Peter.
    -> All possible combinations of ‘*’s works in a same way accordingly.

 3) Modify
    Syntax: Modify FieldName MatchValue NewValue.
    -> ‘Modify department IT Accounting’ command will assign ‘Accounting’ as department names to all employees whose current department is ‘IT’.

 4) Read
    Syntax: Read filename MatchValue
    -> ‘Read Job Developer’ command will display employee records whose Job title is ‘Developer’.
    -> ‘Read * *’ will display all employee records.


GUI IMPLEMNENTATION:
 -> The command must be entered in the textField and the output will be displayed in textArea.
 -> The save button saves the all employee records in .dat file and the load button loads .dat file. 
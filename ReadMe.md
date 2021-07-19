#INSTRUCTIONS:
1. Unzip the votingsystem.1.0.0.zip file.
2. Open Cmd, navigate to the extracted location.
3. Run "java -jar votingsystem.1.0.0.jar"

#PreRequisites:
Java1.8 must be installed
Application runs on port 8888

#Tools and Techologies:
Java8, SpringBoot, PostMan, H2 Database

##Documentation:
Swagger: http://localhost:8888/swagger-ui.html

##About application:
-> Speaker with the name "speaker/itsme" created by default.
-> Used basic authentication for the Rest API. Username and password must required
-> By default few users are created(user1/user1, user2/user2, user3/user3)
-> Speaker created with ADMIN role can access admin related end points, Users has USER role limited to some end points only
-> Users who has ADMIN role can acces the admin end points
-> Application contains three tables Bill(Stores bill information), Member(Stores Gov Members information), Vote(Stores votes information with bills and users information)

##End Points:
#Memebers:
/api/getMembers - Get all created members from the database
/api/createMember - To create a member
#Bills:
/api/createBill - Create new bill (ADMIN)
/api/getAllBills - Get all created bill from the database (ADMIN)
/api/getBills/live - Get all bills which are in middle of voting (ADMIN/USER)
/api/getBill/{id} - Get bill information (ADMIN/USER)
/api/startVoting - Voting will starts for the created bill (ADMIN)
/api/closeVoting - Voting will be closed. It allows to close if the voting passed 15mins only (ADMIN)
/api/restartVoting - Voting will be restarted (ADMIN)
/api/vote - To Vote (ADMIN/USER)
/api/getVoteResult/{id} - To get the voting result (ADMIN)
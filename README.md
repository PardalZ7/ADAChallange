# Java Challange for ADAChallange - By Alexandre Cardoso
This is a card game backend. The user can get a question (compose by 2 movies) and need to answer wich one has the 
greater score according the IMDB (score = imdb.score * imdb.votes) 

## Descripton
1. The auth method is JWT
2. There are two user roles: ADMIN and USER
3. It was implemented a user CRUD, a movie CRUD and the Match endpoints.
4. Only ADMINs can use the movie CRUD endpoints
5. Only ADMINs can use the CREATE, UPDATE and DELETE user endpoints.
6. After the login the user/admin can use the match/nextRound EP to get a new question
7. After get a new question, the user can use the match/answer/{A/B} EP to answer the
question
8. The user can get a nes question after ansew the current one.
9. After 3 wrong answers, the match is closed. If the user try to get a new question,
a new match will be open.


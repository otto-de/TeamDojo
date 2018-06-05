# TeamDojo

An application for improving (application and project) skills of your teams through gamification. 
It allows teams to self assess their skills and checks if they are reaching a specified ability level.
If they reach a new ability level, they will be rewarded with a cool new Team Avatar, Level Rewards - 
like a virtual belt -  and topic specific Badges.
TeamDojo also calculates scores, based on specific Skill, Level and Badge ranking/difficulty and ranks the teams by
the amount of their reached scores.   


##Usage

###Cloning
    
    ToDo
    git clone https://github.com/otto-de/...
    cd ...

###Docker
First build a docker image by running:

    ./gradlew bootWar -Pprod buildDocker

Then run:

    docker-compose -f src/main/docker/app.yml up -d
   
The application will available on [http://localhost:8080](http://localhost:8080)
    

###Organization, Levels, Skills and Badges

TeamDojo comes prefilled with some demo data.
Log in with the default admin credentials: _admin/teamdojo_ under 
[http://localhost:8080/#/admin](http://localhost:8080/#/admin) and change your __organization__ _Entity - Organization_.
It will be your navigation root node.

Next you would like to create some __Dimensions__ your teams want to reaches skills under _Entity - Dimensions_. 
Examples could be _Quality Assurance_, _Security_, _Operations_, _Architecture_, ...

Now you can specify maturity or ability __Levels__ for these dimensions: _Entity - Level_.
Each Level should consist of _n_ Skills. 
 

###Scoring System



##Security


## Development

[Here](DEVELOPMENT.md) you can find the dev documentation. 

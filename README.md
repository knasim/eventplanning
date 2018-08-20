# Event Planning

#### Intro:


The problem of seating guests has deep implications within Computer Science and falls under the NP category of problems. Meaning there is no best solution available for problems classfied as such.  One can come up with a best approximate soution
that comes close.

#### Approach:  
  Recursive Algorithm that attempts to accommodate all of the reservations.
  Algorithm applies a divide/conquer strategy with a greedy approach.  The
  reservations are separated into 2 Queues i.e. one having no dislike constraints
  and the other queue having dislike constraint.  Algorithm uses 2 sweeps to
  process the queues respectively with goal of trying to seat all parties at the same table.
  The greedy strategy keeps track of the remaining table seats and uses this information to
  accomodate as many parties as possible.
  
#### Note to reviewer:
  There is no guarantee that in certain edges cases this approach shall not fail.
  Due to shortage of time, all such cases could not be fully vetted.


#### Improvement
   An improvement would be to create a numerical quantity that serves as a threshold parameter.
   This shall halt execution of the program and throw an error when this parameter value is reached.

#### Running the code
    From the test package - Run unit ReservationManagerImplTest.
    Project built using gradle and shall open in IDEs with Gradle support.
  

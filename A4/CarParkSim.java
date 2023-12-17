import java.util.Scanner;
/**
 * The CarParkSim class contains the main car park simulation method.
 * It creates and manipulates the main objects, and handles user I/O.
 *
 * @author Stephan Jamieson and ...
 * @version 14/7/2019
 */
public class CarParkSim {
        
    public static void main(final String[] args) {
        final Scanner keyboard = new Scanner(System.in);
        Clock clock = new Clock(new Time("00:00:00"));
        Register register = new Register(); 
       TariffTable tariffTable = new TariffTable(10);
       final Currency currency = new Currency("R", "ZAR", 100);
       String[] costs = {"R10","R15","R20","R30","R40","R50","R60","R70","R90","R100"};
       final TimePeriod pOne = new TimePeriod(new Duration("minute", 0), new Duration("minute", 30)); 
       tariffTable.addTariff(pOne, new Money(costs[0], currency));
        int time = 30;
        for (int i=1; i<costs.length; i++) {  
         if  (i==1) {
           time+=30;
           final TimePeriod pTwo = new TimePeriod(new Duration("minute", time-30), new Duration("minute", (time)));
           tariffTable.addTariff(pTwo, new Money(costs[i], currency));         
            }
         else if(i<=5) {
          if (i!=2) {
          time+=60;
          final TimePeriod pThree = new TimePeriod(new Duration("minute", time-60), new Duration("minute", (time)));
          tariffTable.addTariff(pThree, new Money(costs[i], currency));
          }
          else {
           time+=120;
           final TimePeriod pThree = new TimePeriod(new Duration("minute", time-120), new Duration("minute", (time)));
           tariffTable.addTariff(pThree, new Money(costs[i], currency));
          }
         }
         else if (i<=8) {
          time+=120;
          final TimePeriod pFour = new TimePeriod(new Duration("minute", time-120), new Duration("minute", (time)));
          tariffTable.addTariff(pFour, new Money(costs[i], currency));
         }
         else { 
          final TimePeriod pFive = new TimePeriod(new Duration("minute", time), new Duration("minute", (time+=(12*60))));
          tariffTable.addTariff(pFive, new Money(costs[i], currency));
         }
       }

        // Declare variables to store a Clock and a Register object, create the relevant objects and assign them. 
        
        System.out.println("Car Park Simulator");
        Time currentTime = clock.examine();
        System.out.println("The current time is "+currentTime.toString()+".");
        // Print current time.
        System.out.println("Commands: tariffs, advance {minutes}, arrive, depart, quit.");
        System.out.print(">");
        String input = keyboard.next().toLowerCase();
        while (!input.equals("quit")) {
            if (input.equals("advance")) {
                int advance = keyboard.nextInt();
                clock.advance(new Duration("minute",advance ));
                System.out.println("The current time is "+ clock.examine().toString()+".");
                // Advance the clock, print the current time.
            }
            else if (input.equals("arrive")) {
                Ticket ticket = new Ticket(clock.examine());
                System.out.println("Ticket issued: "+ticket.toString()+".");
                register.add(ticket);
                // Create a new ticket, add it to the register, print details of ticket issued.
            }
            else if (input.equals("depart")) {
                String id  = keyboard.nextLine().trim();
                if (register.contains(id)==true){
                 Ticket retrievedTicket = register.retrieve(id);
                 System.out.println("Ticket details: "+retrievedTicket.toString()+".");
                 System.out.println("Current time: "+clock.examine()+".");
                 Duration d = retrievedTicket.age(clock.examine());
                 System.out.println("Duration of stay: "+Duration.format(d,"minute")+".");
                 System.out.println("Cost of stay : "+tariffTable.getTariff(d)+".");
                }
                else {
                 System.out.println("Invalid ticket ID.");
                }
                // Determine if ticket is valid, i.e. in the register.
                // If not, print error message.
                // If yes, retreive it, calculate duration of stay and print it.
            }
            else if (input.equals("tariffs")) {
             System.out.println(tariffTable);
            }
            else {
                System.out.println("That command is not recognised.");
                System.out.println("Commands: advance <minutes>, arrive, depart, quit.");
            }            
            System.out.print(">");
            input = keyboard.next().toLowerCase();
        }            
        System.out.println("Goodbye.");
    }

}

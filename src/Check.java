import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

//Task1
public  class Check {
    public static void main(String[] args) {
        Scanner plane = new Scanner(System.in);
        System.out.println("Welcome to the Plane Management application");

        char[][] seats = new char[4][];


        seats[0] = new char[14];
        seats[1] = new char[12];
        seats[2] = new char[12];
        seats[3] = new char[14];

        for (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (int coloumnIndex = 0; coloumnIndex < seats[rowIndex].length; coloumnIndex++) {
                seats[rowIndex][coloumnIndex] = '0';
            }
            System.out.println();
        }
        System.out.println(Arrays.deepToString(seats));

        //creating a ticket array
        Ticket[] tickets = new Ticket[52];


//Task2
        int option = -1;
        while (option != 0) {
            System.out.println("""
                    ************************************
                    *          MENU OPTIONS            *
                    ************************************""");
            System.out.println("""
                    1) Buy a seat
                    2) Cancel a Seat
                    3) Find first available seat
                    4) Show seating plan
                    5) Print tickets information and total sales
                    6) Search ticket
                    0) Quit
                    ************************************\s""");
            boolean correctOption = false;
            while (!correctOption) {
                try {
                    System.out.println("Please select an option: ");
                    option = plane.nextInt();
                    correctOption = true;
                    plane.nextLine();

                    switch (option) {
                        case 1:
                            buy_seat(seats, tickets);
                            System.out.println(Arrays.deepToString(seats));
                            break;
                        case 2:
                            cancel_seat(seats, tickets);
                            System.out.println(Arrays.deepToString(seats));
                            System.out.println(Arrays.toString(tickets));

                            break;
                        case 3:
                            find_first_available(seats);
                            break;
                        case 4:
                            show_seating_plane(seats);
                            break;
                        case 5:
                            print_tickets_info(tickets);
                            break;
                        case 6:
                            //search_ticket():
                            break;
                        case 0:
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid option!!");
                            correctOption = false;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid option type! Please enter a numeric value.");
                    correctOption = false;
                    plane.nextLine();  // to prevent the infinite loop
                }
            }
        }
    }

    private static char promptRowLabel() {
        Scanner plane = new Scanner(System.in);
        System.out.println("Row Letter[A,B,C or D] \n Enter a Row Letter :");
        char rowLabel = Character.toUpperCase(plane.next().charAt(0));

        while (rowLabel != 'A' && rowLabel != 'B' && rowLabel != 'C' && rowLabel != 'D') {
            System.out.println("Invalid Row Letter. Please enter a Row Letter: ");
            rowLabel = Character.toUpperCase(plane.next().charAt(0));
        }
        return rowLabel;
    }

    private static int promptSeatNum(char rowLet) {
        Scanner plane = new Scanner(System.in);
        System.out.println("Seat Number [1-14]\n Enter a seat number: ");
        int seatNumber;

        while (true) {
            try {
                seatNumber = plane.nextInt();
                if ((rowLet == 'A' || rowLet == 'D') && (seatNumber < 1 || seatNumber > 14)) {
                    System.out.println("Invalid Seat Number.Please enter a Seat Number: ");
                    seatNumber = plane.nextInt();
                } else {
                    break;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input type.Please enter a numeric value.");
                plane.nextLine();
            }
        }
        return seatNumber;
    }

    //Task 3
    public static void buy_seat(char[][] array, Ticket[] tickets) {
        Scanner plane = new Scanner(System.in);
        char rowLet = promptRowLabel();
        int seatNum = promptSeatNum(rowLet);
        System.out.println("Valid Seat ");
        double price = ticketPrice(seatNum);
        //System.out.println(price);


        char[] seatArray;
        switch (rowLet) {
            case 'A':
                seatArray = array[0];
                break;
            case 'B':
                seatArray = array[1];
                break;
            case 'C':
                seatArray = array[2];
                break;
            case 'D':
                seatArray = array[3];
                break;
            default:
                return;
        }


        if (seatArray[seatNum - 1] == '0') {
            seatArray[seatNum - 1] = '1';
            System.out.println("Enter your Name:");
            String name = plane.next();
            System.out.println("Enter your Surname:");
            String surName = plane.next();
            System.out.println("Enter your Email ID:");
            String email = plane.next();
            // should i have to check if the email is valid
            Person person1 = new Person(name, surName, email);
            Ticket ticket1 = new Ticket(person1, rowLet, seatNum);
            ticket1.setPrice(price);

            for (int ticketsIndex = 0; ticketsIndex < tickets.length; ticketsIndex++) {
                if (tickets[ticketsIndex] == null) {
                    tickets[ticketsIndex] = ticket1;
                    break;
                } else {
                    continue;
                }
            }
            System.out.println(Arrays.toString(tickets));

            System.out.println("Seat Booked");
        } else {
            System.out.println("Sorry! Seat Sold");
        }

    }

    //Task 4
    public static void cancel_seat(char[][] array, Ticket[] tickets) {
        char rowLet = promptRowLabel();
        int seatNum = promptSeatNum(rowLet);
        System.out.println("Valid Seat ");

        char[] seatArray;
        switch (rowLet) {
            case 'A':
                seatArray = array[0];
                break;
            case 'B':
                seatArray = array[1];
                break;
            case 'C':
                seatArray = array[2];
                break;
            case 'D':
                seatArray = array[3];
                break;
            default:
                return;
        }

        if (seatArray[seatNum - 1] == '1') {
            seatArray[seatNum - 1] = '0';
            //Ticket ticket1 = new Ticket(rowLet, seatNum);

            for (int ticketsIndex = 0; ticketsIndex < tickets.length; ticketsIndex++) {
                if (tickets[ticketsIndex] != null) {
                    if (tickets[ticketsIndex].getRowLabel() == rowLet) {
                        if (tickets[ticketsIndex].getSeatNumber() == seatNum) {
                            tickets[ticketsIndex] = null;
                            System.out.println("Ok! The seat booked is cancelled. ");
                        }
                        break;
                    }
                } else {
                    continue;
                }
            }

        } else {
            System.out.println("Sorry,Seat haven't booked yet!");
        }
    }

    public static void find_first_available(char[][] array) {
        char searchValue = '0';
        int seatNum = 0;

        for (int rowLet = 0; rowLet < array.length; rowLet++) {
            char rowName = (char) ('A' + rowLet);
            while (seatNum < array[rowLet].length && array[rowLet][seatNum] != searchValue) {
                seatNum++;
            }
            if (seatNum == array[rowLet].length - 1) {
                System.out.println("Sorry! All seats a booked in" + rowName);
            } else {
                System.out.println("First seat available:" + rowName + (seatNum + 1));
                return;
            }
            seatNum = 0;
        }
    }

    public static void show_seating_plane(char[][] array) {
        for (char[] row : array) {
            for (char seat : row) {
                if (seat == '0') {
                    seat = 'O';
                } else {
                    seat = 'X';
                }
                System.out.print(seat + " ");
            }
            System.out.println();
        }

    }

    //pricing management
    public static int ticketPrice(int seatNum) {
        if (seatNum >= 1 && seatNum <= 5) {
            return 200;
        } else if (seatNum >= 6 && seatNum <= 9) {
            return 150;
        } else if (seatNum >= 10 && seatNum <= 14) {
            return 180;
        } else
            return 0;
    }

    public static void print_tickets_info(Ticket[] tickets) {

        System.out.println("Sold tickets during this session in sold order:");
        double totalPrice = 0;
        int firstTicket;
        for (int index1 = 0; index1 < tickets.length - 1; index1++) {
            firstTicket = index1;

            while (firstTicket < tickets.length && tickets[firstTicket] == null) {
                firstTicket++;
            }

            if (firstTicket >= tickets.length) {
                break;
            }
            for (int index2 = index1 + 1; index2 < tickets.length; index2++) {
                if (tickets[index2] != null) {
                    if (tickets[index2].getRowLabel() < tickets[firstTicket].getRowLabel()) {
                        firstTicket = index2;
                    } else if ((tickets[index2].getRowLabel() == tickets[firstTicket].getRowLabel() && tickets[index2].getSeatNumber() < tickets[firstTicket].getSeatNumber())) {
                        firstTicket = index2;
                    }
                }
            }
            if (firstTicket != index1 && tickets[firstTicket] != null) {
                Ticket temp = tickets[index1];
                tickets[index1] = tickets[firstTicket];
                tickets[firstTicket] = temp;
            }
        }


        for (Ticket oneTicket : tickets) {
            if (oneTicket != null) {
                System.out.println("Ticket:");
                System.out.println("Seat:" + oneTicket.getRowLabel() + oneTicket.getSeatNumber());
                totalPrice = totalPrice + oneTicket.getPrice();
                System.out.println("Price:" + "£" + oneTicket.getPrice());
            }
        }
        System.out.println("Total amount of the tickets sold: " + "£" + totalPrice);



    }


    //public static void print_tickets_info(Ticket[] tickets) {

        //System.out.println("Sold tickets during this session in sold order:");
       // double totalPrice = 0;
      //  for (Ticket oneTicket : tickets) {
      //      if (oneTicket != null) {
           ////     System.out.println("Ticket:");
           ////     System.out.println("Seat:" + oneTicket.getTicketRowLet() + oneTicket.getTicketSeatNum());
           //     totalPrice = totalPrice + oneTicket.getTicketPrice();
           //     System.out.println("Price:" + "£" + oneTicket.getTicketPrice());

     //       } else
       //         continue;
    //    }
     //   System.out.println("Total amount of the tickets sold: " + "£" + totalPrice);

  //  }

}




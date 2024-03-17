import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

//Task1
public class PlaneManagement {
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
                            search_ticket(seats, tickets);
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
        System.out.println("Enter a Row Letter [A,B,C or D] : ");
        char rowLabel = Character.toUpperCase(plane.next().charAt(0));

        while (rowLabel != 'A' && rowLabel != 'B' && rowLabel != 'C' && rowLabel != 'D') {
            System.out.println("Invalid Row Letter. Please enter a Row Letter: ");
            rowLabel = Character.toUpperCase(plane.next().charAt(0));
        }
        return rowLabel;
    }

    private static int promptSeatNumber(char rowLabel) {
        Scanner plane = new Scanner(System.in);
        System.out.println("Enter a seat number [1-14] : ");
        int seatNumber = -1;
        boolean isValidSeat = false;
        while (!isValidSeat) {
            try {
                seatNumber = plane.nextInt();
                if ((rowLabel == 'A' || rowLabel == 'D') && (seatNumber < 1 || seatNumber > 14)) {
                    System.out.println("Invalid Seat Number.Please enter a Seat Number: ");
                } else {
                    isValidSeat = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type.Please enter a numeric value.");
                plane.nextLine();
            }
        }
        return seatNumber;
    }

    //Task 3
    public static void buy_seat(char[][] allSeats, Ticket[] tickets) {

        Scanner plane = new Scanner(System.in);
        char rowLabel = promptRowLabel();
        int seatNumber = promptSeatNumber(rowLabel);
        //System.out.println("Valid Seat ");
        double price = ticketPrice(seatNumber);
        //System.out.println(price);


        char[] rowArray;
        switch (rowLabel) {
            case 'A':
                rowArray = allSeats[0];
                break;
            case 'B':
                rowArray = allSeats[1];
                break;
            case 'C':
                rowArray = allSeats[2];
                break;
            case 'D':
                rowArray = allSeats[3];
                break;
            default:
                return;
        }


        if (rowArray[seatNumber - 1] == '0') {
            rowArray[seatNumber - 1] = '1';

            System.out.println("Enter your Name:");
            String name = plane.next();
            System.out.println("Enter your Surname:");
            String surName = plane.next();
            System.out.println("Enter your Email ID:");
            String email = plane.next();
            // should i have to check if the email is valid
            Person person = new Person(name, surName, email);
            Ticket ticket = new Ticket(person, rowLabel, seatNumber);
            ticket.setPrice(price);

            for (int index = 0; index < tickets.length; index++) {
                if (tickets[index] == null) {
                    tickets[index] = ticket;
                    break;
                }
            }
            System.out.println("Your seat has been confirmed\n");
            ticket.save();
            System.out.println("Your ticket has been purchased\n");
            System.out.println(Arrays.toString(tickets));
        } else {
            System.out.println("Sorry! The seat has been taken");
        }

    }

    //Task 4
    public static void cancel_seat(char[][] allSeats, Ticket[] tickets) {
        char rowLabel = promptRowLabel();
        int seatNumber = promptSeatNumber(rowLabel);
        Ticket ticket = new Ticket(rowLabel, seatNumber);

        char[] rowArray;
        switch (rowLabel) {
            case 'A':
                rowArray = allSeats[0];
                break;
            case 'B':
                rowArray = allSeats[1];
                break;
            case 'C':
                rowArray = allSeats[2];
                break;
            case 'D':
                rowArray = allSeats[3];
                break;
            default:
                return;
        }

        if (rowArray[seatNumber - 1] == '1') {
            rowArray[seatNumber - 1] = '0';

            for (int index = 0; index < tickets.length; index++) {
                if (tickets[index] != null) {
                    if (tickets[index].getRowLabel() == rowLabel) {
                        if (tickets[index].getSeatNumber() == seatNumber) {
                            tickets[index] = null;
                            System.out.println("We have successfully cancelled your seat booking");
                        }
                        break;
                    }
                } else {
                    continue;
                }
            }
        } else {
            System.out.println("We don't see a reservation for this seat.Cancellation is only for booked seats");
        }

        ticket.delete();

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
                System.out.println("The First available seat:" + rowName + (seatNum + 1));
                return;
            }
            seatNum = 0;
        }
    }

    public static void show_seating_plane(char[][] array) {

        for (char[] row : array) {
            for (char col : row) {
                if (col == '0') {
                    col = 'O';
                } else {
                    col = 'X';
                }
                System.out.print(col + " ");

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

    //print tickets

    public static void print_tickets_info(Ticket[] tickets) {

        System.out.println("Sold tickets during this session in sold order:");
        double totalPrice = 0;
        //selection sort
        int initialIndex;
        for (int currentIndex = 0; currentIndex < tickets.length - 1; currentIndex++) {
            initialIndex = currentIndex;

            while (initialIndex < tickets.length && tickets[initialIndex] == null) {
                initialIndex++;
            }

            if (initialIndex >= tickets.length) {
                break;
            }
            for (int nextIndex = currentIndex + 1; nextIndex < tickets.length; nextIndex++) {
                if (tickets[nextIndex] != null) {
                    if (tickets[nextIndex].getRowLabel() < tickets[initialIndex].getRowLabel()) {
                        initialIndex = nextIndex;
                    } else if ((tickets[nextIndex].getRowLabel() == tickets[initialIndex].getRowLabel() &&
                            tickets[nextIndex].getSeatNumber() < tickets[initialIndex].getSeatNumber())) {
                        initialIndex = nextIndex;
                    }
                }
            }
            if (initialIndex != currentIndex && tickets[initialIndex] != null) {
                Ticket temp = tickets[currentIndex];
                tickets[currentIndex] = tickets[initialIndex];
                tickets[initialIndex] = temp;
            }
        }


        for (Ticket ticket : tickets) {
            if (ticket != null) {
                System.out.println("Ticket:");
                System.out.println("Seat:" + ticket.getRowLabel() + ticket.getSeatNumber());
                totalPrice = totalPrice + ticket.getPrice();
                System.out.println("Price:" + "£" + ticket.getPrice());
            }
        }
        System.out.println("Total amount of the tickets sold: " + "£" + totalPrice);
    }


    public static void search_ticket(char[][] array, Ticket[] tickets) {

        char rowLet = promptRowLabel();
        int seatNum = promptSeatNumber(rowLet);

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
            for (Ticket oneTicket : tickets) {
                if (oneTicket != null) {
                    if (oneTicket.getRowLabel() == rowLet && oneTicket.getSeatNumber() == seatNum) {
                        oneTicket.printTicketInfo();
                        break;
                    } else
                        continue;
                }
            }
        } else
            System.out.println("This seat is available");
    }
}



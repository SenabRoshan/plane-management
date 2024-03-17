import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private Person person;
    private char rowLabel;
    private int seatNumber;
    private double price;

    public Ticket(Person classPerson, char tRow, int tSeat) {
        this.person = classPerson;
        this.rowLabel = tRow;
        this.seatNumber = tSeat;
        // this.ticketPrice = tPrice;
    }

    public Ticket(char tRow, int tSeat) {
        //this.person = classPerson;
        this.rowLabel = tRow;
        this.seatNumber = tSeat;
        // this.ticketPrice = tPrice;
    }

    public Person getPerson() {
        return person;
    }

    //whats the use of these getters ?
    public char getRowLabel() {
        return rowLabel;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double tPrice) {
        this.price = tPrice;
    }

    public void printTicketInfo() {
        person.printPersonalInfo();
        System.out.println("Ticket Information");
        System.out.println("Seat: " + rowLabel + seatNumber);
        System.out.println("Ticket Price:" + "£" + price);
    }

    public void save() {
        File ticketDirectory = new File("soldTickets");
        boolean isDirectoryCreated = ticketDirectory.mkdirs();
        if (isDirectoryCreated) {
            System.out.println("Sold Tickets Directory successfully created" + ticketDirectory.getAbsolutePath());
        } else if (ticketDirectory.exists()) {
            System.out.println("There's another Directory with same name");
        }
        String fileName = getFileName();

        try {
            File ticketFile = new File("soldTickets", fileName);
            boolean isFileCreated = ticketFile.createNewFile();
            if (isFileCreated) {
                System.out.println("File successfully created " + ticketFile.getName());
            } else if (ticketFile.exists()) {
                System.out.println("There's another file with same file name");
            }

            FileWriter ticketWriter = new FileWriter("soldTickets" + File.separator + fileName);

            ticketWriter.write("Personal Information\n");
            ticketWriter.write("Name: " + person.getUserName() + "\n");
            ticketWriter.write("Surname: " + person.getUserSurName() + "\n");
            ticketWriter.write("Email: " + person.getUserEmail() + "\n");
            ticketWriter.write("Ticket Information\n");
            ticketWriter.write("Seat: " + rowLabel + seatNumber + "\n");
            ticketWriter.write("Ticket Price:" + "£" + price + "\n");

            ticketWriter.close();


        } catch (IOException e) {
            System.out.println("Error occurred while creating and writing the file");
        }
    }

    public void delete() {
        String fileName = getFileName();
        File ticketFile = new File("soldTickets", fileName);
        if (ticketFile.exists()) {
            boolean isDeleted = ticketFile.delete();
            if (isDeleted) {
                System.out.println(ticketFile.getName() + "file deleted");
            } else {
                System.out.println(ticketFile.getName() + "file doesn't exist");

            }
        }
    }

    private String getFileName() {
        return String.valueOf(rowLabel) + String.valueOf(seatNumber) + ".txt";
    }
}


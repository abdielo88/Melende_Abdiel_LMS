/*Abdiel Melendez, CEN 3024C-31950, 05/19/2024
Class Name: LMS
The main class uses a switch method that allows the user to either: display the books of a
text file that they uploaded to the application, add a new book to that list or remove it, or exit
the program.
*/
import java.io.*;
import java.util.*;

public class LMS
{
    private static final String EXIT_OPTION = "4"; //This will be used in the "Exit" case #4
    private List<Book> books;

    public LMS()
    {books = new ArrayList<>();}

    public static void main(String[] args)
    {
        LMS lms = new LMS();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the path to the text file containing books: ");
        String filePath = scanner.nextLine();
        lms.loadBooksFromFile(filePath);

        while (true)
        {
            System.out.println("\nPick a function by typing the corresponding number: ");
            System.out.println("1. Display all books");
            System.out.println("2. Add a book");
            System.out.println("3. Remove a book");
            System.out.println("4. Exit");
            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1":
                    lms.displayBooks();
                    break;
                case "2":
                    lms.addBook(scanner);
                    break;
                case "3":
                    lms.removeBook(scanner);
                    break;
                case EXIT_OPTION:
                    lms.saveBooksToFile("books.txt");
                    System.out.println("You have exited the program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid number; try again.");
            }
        }
    }

    private void loadBooksFromFile(String fileName)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\. ", 2);
                int id = Integer.parseInt(parts[0]);
                String[] titleAuthor = parts[1].split(", ", 2);
                String title = titleAuthor[0];
                String author = titleAuthor[1];
                books.add(new Book(id, title, author));
                /*Lines 61-65 will make sure that once the object of the book is created in line 67, the information
                will be in the format of ID, Book Title, Author.*/
            }
        } catch (IOException e)
        {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    private void saveBooksToFile(String fileName)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            for (Book book : books)
            {
                writer.write(book.toString());
                writer.newLine();
                //This ensures that the once the users is done with the program, the changes are saved in the file.
            }
        } catch (IOException e)
        {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }

    private void displayBooks()
    {
        for (Book book : books)
        {System.out.println(book);}
        //A for loop that displays all the books in the list.
    }

    private void addBook(Scanner scanner)
    {
        System.out.print("Enter the new (unique) ID: ");
        int newId = Integer.parseInt(scanner.nextLine());
        if (books.stream().anyMatch(book -> book.getId() == newId))
        {
            System.out.println("The new ID is not unique; try again.");
            return;
            //This makes sure that the number ID is not repeated.
        }
        System.out.print("Enter the tile of the new book: ");
        String title = scanner.nextLine();

        System.out.print("Enter the author of the new book: ");
        String author = scanner.nextLine();

        books.add(new Book(newId, title, author));
        //This adds the new book to the text file.
    }

    private void removeBook(Scanner scanner)
    {
        System.out.print("Enter the ID of the book you wish to remove: ");
        int id = Integer.parseInt(scanner.nextLine());
        books.removeIf(book -> book.getId() == id);
        //The user will be asked to input the number ID of the book that they want to remove.
    }
}

class Book
    //This class creates the objects for each book in the file.
{
    private int id;
    private String title;
    private String author;

    public Book(int id, String title, String author)
    {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {return id;}
    public String getTitle() {return title;}
    public String getAuthor() {return author;}

    @Override
    public String toString() {return id + ". " + title + ", " + author;}
}

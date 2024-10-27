package fr.gildasfedevieille.models;

public class Book implements IBook {

    private String ISBN;
    private String title;
    private String description;
    private String author;
    private double price;

    public Book(String ISBN, String title, String description, String author, double price) {
        this.ISBN = ISBN;
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new StringBuilder("Book{")
                .append("ISBN='").append(ISBN).append('\'')
                .append(", title='").append(title).append('\'')
                .append(", description='").append(description).append('\'')
                .append(", author='").append(author).append('\'')
                .append(", price=").append(price)
                .append('}')
                .toString();
    }

    @Override
    public String toShortString() {
        return new StringBuilder("Book{")
                .append("ISBN='").append(ISBN).append('\'')
                .append(", title='").append(title).append('\'')
                .append('}')
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return ISBN.equals(book.ISBN);
    }

    @Override
    public int hashCode() {
        return ISBN.hashCode();
    }
}

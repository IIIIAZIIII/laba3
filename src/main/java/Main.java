import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Gson gson = new Gson();
        List<Poseti> posetiList = null;
        try (FileReader reader = new FileReader("books.json")) {
            Type posetiListType = new TypeToken<List<Poseti>>() {
            }.getType();
            posetiList = gson.fromJson(reader, posetiListType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (posetiList != null) {
            System.out.println("#1");
            posetiList.stream()
                    .map(poseti -> poseti.getName() + ' ' + poseti.getSurname())
                    .forEach(System.out::println);
            System.out.println("Всего посетителей: " + posetiList.size());
            System.out.println("\n#2");
            List<Book> uniqueBooks = posetiList.stream()
                    .flatMap(poseti -> poseti.getFavoriteBooks().stream())
                    .distinct().toList();
            uniqueBooks.forEach(System.out::println);
            System.out.println("Всего уникальных книг: " + uniqueBooks.size());
            System.out.println("\n#3");
            uniqueBooks.stream().sorted(Comparator.comparing(Book::getPublishingYear)).forEach(System.out::println);
            System.out.println("\n#4");
            String authorName = "Jane Austen";
            boolean hasAuthor = uniqueBooks.stream()
                    .anyMatch(book -> authorName.equalsIgnoreCase(book.getAuthor()));
            if (hasAuthor) {
                System.out.println("Книга этого автора есть");
            } else {
                System.out.println("Книги этого автора нет");
            }
            System.out.println("\n#5");
            int maxFavBooks = posetiList.stream()
                    .map(poseti -> poseti.getFavoriteBooks().size())
                    .max(Comparator.naturalOrder())
                    .orElse(0);
            System.out.println("Максимальное кол-во книг в избранном: " + maxFavBooks);
            System.out.println("\n#6");
            double avgBook = posetiList.stream()
                    .mapToInt(poseti -> poseti.getFavoriteBooks().size())
                    .average().orElse(0);
            List<Message> msg = posetiList.stream().filter(Poseti::isSubscribed)
                    .map(poseti -> {
                        int favoriteBooksCount = poseti.getFavoriteBooks().size();
                        String message;
                        if (favoriteBooksCount>avgBook)
                            message = "You are a bookworm";
                        else if (favoriteBooksCount< avgBook)
                            message = "Read more";
                        else
                            message = "Fine!";
                        return new Message(poseti.getPhone(),message);
                    }).toList();
            msg.forEach(System.out::println);
        }
        else
        {
            System.out.println("Нет такого файла");
        }
    }
}

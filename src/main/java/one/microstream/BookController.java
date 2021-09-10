package one.microstream;

import java.util.List;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;


@Controller("/books")
public class BookController
{
	@Get("/create")
	public HttpResponse<String> createBooks()
	{
		Book book = new Book("9783732542215", "Origin");
		Book book2 = new Book("9783732542215", "Diabolus");
		Book book3 = new Book("978-3-7341-0742-9", "Die Suche");
		Book book4 = new Book("978-3-7341-0522-7", "Die Erscheinung");
		
		DB.root.getBooks().addAll(CollectionUtils.setOf(book, book2, book3, book4));
		DB.storageManager.store(DB.root.getBooks());
		
		return HttpResponse.ok("Books successfully created!");
	}
	
	@Get
	public List<Book> getBook()
	{
		return DB.root.getBooks();
	}
}

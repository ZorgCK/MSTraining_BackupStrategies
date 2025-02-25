package one.microstream;

import java.util.List;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
@Controller("/books")
public class BookController
{
	@Inject
	public DB db;
	
	@Get("/create")
	public HttpResponse<String> createBooks()
	{
		Book book = new Book("9783732542215", "Origin");
		Book book2 = new Book("9783732542215", "Diabolus");
		Book book3 = new Book("978-3-7341-0742-9", "Die Suche");
		Book book4 = new Book("978-3-7341-0522-7", "Die Erscheinung");
		
		db.root.getBooks().addAll(CollectionUtils.setOf(book, book2, book3, book4));
		db.getStorageManager().store(db.root.getBooks());
		
		return HttpResponse.ok("Books successfully created!");
	}
	
	@Get
	public List<Book> getBook()
	{
		return db.root.getBooks();
	}
}

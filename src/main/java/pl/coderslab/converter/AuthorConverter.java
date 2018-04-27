package pl.coderslab.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.coderslab.app.dao.AuthorDao;
import pl.coderslab.app.entity.Author;

// dont forget to add converter to the register
public class AuthorConverter implements Converter<String, Author> {

	@Autowired
	private AuthorDao authors;

	@Override
	public Author convert(String source) {
		System.out.println("-------------------CONVERTER: Author converter is called with " + source);
		Author author = authors.findById(Long.parseLong(source));
		System.out.println("-------------------CONVERTER: Returned Author is " + author);
		return author;
	}

}

package pl.coderslab.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.coderslab.app.dao.PublisherDao;
import pl.coderslab.app.entity.Publisher;

public class PublisherConverter implements Converter<String, Publisher> {

	
	@Autowired
	private PublisherDao publisherDao;
	
	@Override
	public Publisher convert(String source) {
		System.out.println("-------------------CONVERTER: Publisher converter is called with " + source);
		Publisher publisher = publisherDao.findById(Long.parseLong(source));
		System.out.println("-------------------CONVERTER: Returned Publisher is " + publisher);
		return publisher;
	}

}

package com.urlShortener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.urlShortener.shortener.UrlShortener.encode;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlShortenerTest {

	@Test
	public void encode_1() {
		assertThat(encode(1)).isEqualTo("b");
	}

	@Test
	public void encode_0() {
		assertThat(encode(0)).isEqualTo("");
	}

}

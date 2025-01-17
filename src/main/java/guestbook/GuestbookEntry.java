/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package guestbook;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.springframework.util.Assert;

/**
 * A guestbook entry. An entity as in the Domain Driven Design context. Mapped onto the database using JPA annotations.
 *
 * @author Paul Henke
 * @author Oliver Drotbohm
 * @see https://en.wikipedia.org/wiki/Domain-driven_design#Building_blocks
 */
@Entity
class GuestbookEntry {

	private @Id @GeneratedValue Long id;
	private final String name, text;
	private final LocalDateTime date;
	private final LocalDate birth;

	/**
	 * Creates a new {@link GuestbookEntry} for the given name and text.
	 *
	 * @param name must not be {@literal null} or empty
	 * @param text must not be {@literal null} or empty
	 */
	public GuestbookEntry(String name, String text, String birth) {

		Assert.hasText(name, "Name must not be null or empty!");
		Assert.hasText(text, "Text must not be null or empty!");
		Assert.hasText(birth, "Birth must not be null or empty!");

		this.name = name;
		this.text = text;
		this.date = LocalDateTime.now();
		this.birth = formatBirth(birth);
	}

	@SuppressWarnings("unused")
	public GuestbookEntry() {
		this.name = null;
		this.text = null;
		this.date = null;
		this.birth = null;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getText() {
		return text;
	}

	public LocalDate getBirth() {
		return birth;
	}

	private LocalDate formatBirth(String birthString) {
		String[] date = birthString.split("[.\\-]");
		if (date.length != 3)
			return LocalDate.of(1899, 1, 1);
		int day, month, year;
		LocalDate result;
		try {
			day = Integer.parseInt(date[0]);
			month = Integer.parseInt(date[1]);
			year = Integer.parseInt(date[2]);

			result = LocalDate.of(year, month, day);
		}
		catch (Exception e) {
			return LocalDate.of(1899, 1, 1);
		}
		return result;
	}
}

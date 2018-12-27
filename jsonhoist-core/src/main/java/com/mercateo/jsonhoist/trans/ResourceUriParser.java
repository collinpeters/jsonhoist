/*
 * Copyright © 2018 Mercateo AG (http://www.mercateo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mercateo.jsonhoist.trans;

import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.Getter;
import lombok.NonNull;

/**
 * @author nilslindemann
 *
 */
@Getter
public class ResourceUriParser {

	private final String fileName;

	private final String type;

	public ResourceUriParser(@NonNull URI uri) {
		try {
			Path ressourcePath = Paths.get(validateUri(uri));
			fileName = ressourcePath.getFileName().toString();
			type = ressourcePath.getParent().getFileName().toString();

		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private String validateUri(URI uri) throws MalformedURLException {
		String externalForm = uri.toURL().toExternalForm();

		int lastIndexOf = externalForm.lastIndexOf('!');
		if (lastIndexOf != -1) {
			externalForm = externalForm.substring(lastIndexOf);
		}

		if (externalForm.chars().mapToObj(i -> (char) i).filter(c -> c.equals('/')).count() < 2) {
			throw new IllegalArgumentException("Cannot determine type from given URI : " + uri.toString());
		}
		return externalForm;
	}
}
package extended_parser_domain;

import java.util.Iterator;
import java.util.List;

public class Schema extends RequestBodySchema {

	private final String type, name;
	private final List<APIProperty> properties;

	public Schema(String type, String name, List<APIProperty> properties) {
		this.type = type;
		this.name = name;
		this.properties = properties;
	}

	public boolean isRef() {
		return type.contains("$");
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public List<APIProperty> getProperties() {
		return properties;
	}

	public APIProperty getProperty(String name) {
		Iterator<APIProperty> it = properties.iterator();
		APIProperty next, found = null;

		while (it.hasNext() && found == null) {
			next = it.next();
			if (next.getName().equals(name))
				found = next;
		}

		return found;
	}

	public String toString() {
		StringBuilder s = new StringBuilder("[ ");

		if (properties.isEmpty())
			s.append(name).append(": ").append(type);
		else
			for (APIProperty p : properties)
				s.append(p.toString());

		return s + "\n]";
	}

}

package extended_parser_domain;

public class APIProperty {

	private String name, type, pattern, format, itemsType;
	private int min, max;
	private boolean isCollection, required, gen;

	public APIProperty(String name, String type, String pattern, String format, String itemsType, int minimum, int maximum, boolean isCollection, boolean required, boolean gen) {
		this.name = name;
		this.type = type;
		this.pattern = pattern;
		this.format = format;
		this.itemsType = itemsType;
		this.min = minimum;
		this.max = maximum;
		this.isCollection = isCollection;
		this.required = required;
		this.gen = gen;
	}

	public boolean isRequired() {
		return required;
	}
	
	public boolean gen() {
		return gen;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getFormat() {
		return format;
	}
	
	public int getMin () {
		return min;
	}
	
	public int getMax () {
		return max;
	}
	
	public String getItemType() {
		return itemsType;
	}
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public boolean isCollection() {
		return isCollection;
	}
	
	public String toString () {		
		StringBuilder str = new StringBuilder("\n        " + name + ":\n           type: " + type + ", required: "
				+ required + ", gen: " + gen + ", ");

		switch (type) {
			case "string" -> {
				if (pattern == null || pattern.equals(""))
					str.append("pattern: empty");
				else
					str.append("pattern: ").append(pattern);
			}

			case "integer" -> str.append("min: ").append(min).append(", max: ")
					.append(max).append(", format: ").append(format);

			case "array" -> str.append("isCollection: ").append(isCollection);
		}

		return str.toString();
	}
}

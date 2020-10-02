import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.Iterator;
/**
 * Outputs several simple data structures in "pretty" JSON format where newlines
 * are used to separate elements and nested elements are indented using tabs.
 *
 * Warning: This class is not thread-safe. If multiple threads access this class
 * concurrently, access must be synchronized externally.
 *
 * @author CS 212 Software Development
 * @author University of San Francisco
 * @version Fall 2020
 */
public class SimpleJsonWriter {
	public static int count = 0;
	
	/**
	 * Writes the elements as a pretty JSON array.
	 *
	 * @param elements the elements to write
	 * @param writer the writer to use
	 * @param level the initial indent level
	 * @throws IOException if an IO error occurs
	 */
	public static void asArray(Collection<Integer> elements, Writer writer, int level) throws IOException {
		// TODO Fill in using iteration (not replace/split/join methods).
		// TODO Optional: Avoid repeated code and hard-coding the indent level.

		Integer head = elements.stream().findFirst().orElse(null);

		
		Consumer<Integer> writeEntries = elem -> {
			try {
				writer.write(",\n");
				indent(elem, writer, level + 1);
			}
			
			catch (Exception e) {
			
			}
		};
	
		
	
		
		//Start array
		//Case: Head
		writer.write("[");
		
		//Case: All other values
		
		if (head != null) {
			writer.write("\n");
			indent(head, writer, level + 1);
		}
		
		
		
		try {
			elements.stream().skip(1).forEach(writeEntries);
		}
		
		catch (Exception e) {
			System.out.println("o no");
		}
		
		//Case: Tail/After all elements

		writer.write("\n");
		indent(writer, level);
		writer.write("]");

	}

	/**
	 * Writes the elements as a pretty JSON object.
	 *
	 * @param elements the elements to write
	 * @param writer the writer to use
	 * @param level the initial indent level
	 * @throws IOException if an IO error occurs
	 */
	public static void asObject(Map<String, Integer> elements, Writer writer, int level) throws IOException {
		
		String head = elements.keySet().stream().findFirst().orElse(null);
		
		
		
		Consumer<String> writeEntries = key -> {
				try {
					writer.write(",\n");
					indent(key, writer, level + 1);
					writer.write(": ");
					writer.write(elements.get(key).toString());
				}
				
				catch (Exception e) {
					System.out.println("What");
			}
			
		
		};
		
		//Case: Start of obj/head
	
		writer.write("{");
		
		if (head != null) {
			writer.write("\n");
			indent(head, writer, level + 1);
			writer.write(": ");
			writer.write(elements.get(head).toString());
		}
		
		// Case: All values
		try {
			elements.keySet().stream().skip(1).forEach(writeEntries);
		}
		catch(Exception e) {
			System.out.println("Error again - asObject");
		}

		
		//Case: Tail/end of obj
		writer.write("\n}");

	}

	/**
	 * Writes the elements as a pretty JSON object with a nested array. The
	 * generic notation used allows this method to be used for any type of map
	 * with any type of nested collection of integer objects.
	 *
	 * @param elements the elements to write
	 * @param writer the writer to use
	 * @param level the initial indent level
	 * @throws IOException if an IO error occurs
	 */
	public static void asNestedArray(Map<String, ? extends Collection<Integer>> elements, Writer writer, int level)
			throws IOException {
		
		
		//Case: Start of obj/head
		
		String headKey = elements.keySet().stream().findFirst().orElse(null);
	
		Consumer<String> writeEntries = key -> {
			try {
				writer.write(",\n");
				indent(key, writer, level + 1);
				writer.write(": ");
				asArray(elements.get(key), writer, level + 1);
			}
			
			catch (Exception e) {
				System.out.println("Error - headKey");
			}
		};
		
		
		/*
		//writeEntry
		writer.write("\n");
		indent(key, writer, level);
		writer.write(": ");
		asArray(values, writer, level);
		*/
		
		writer.write("{");
		if (headKey != null) {
			writer.write("\n");
			indent(headKey, writer, level + 1);
			writer.write(": ");
			asArray(elements.get(headKey), writer, level + 1);
		}
		
		try {
			elements.keySet().stream().skip(1).forEach(writeEntries);
		}
		
		catch (Exception e) {
			System.out.println("Error - as nestedArray");
		}
		/*
		//Case: Other values
		while (it.hasNext()) {
			writer.write(",");
			String key = it.next();
			writeEntry(key, elements.get(key), writer, level + 1);
		}
		*/
		//Case: Tail/end of obj
		writer.write("\n}");
	}


	/*
	 * TODO: You are encouraged to include helper methods below. Here are a few
	 * that you might find useful. Consider adding others as well.
	 */

	/**
	 * Indents using a tab character by the number of times specified.
	 *
	 * @param writer the writer to use
	 * @param times the number of times to write a tab symbol
	 * @throws IOException if an IO error occurs
	 */
	public static void indent(Writer writer, int times) throws IOException {
		for (int i = 0; i < times; i++) {
			writer.write('\t');
		}
	}

	/**
	 * Indents and then writes the integer element.
	 *
	 * @param element the element to write
	 * @param writer the writer to use
	 * @param times the number of times to indent
	 * @throws IOException if an IO error occurs
	 *
	 * @see #indent(Writer, int)
	 */
	public static void indent(Integer element, Writer writer, int times) throws IOException {
		indent(writer, times);
		writer.write(element.toString());
	}

	/**
	 * Indents and then writes the text element surrounded by {@code " "}
	 * quotation marks.
	 *
	 * @param element the element to write
	 * @param writer the writer to use
	 * @param times the number of times to indent
	 * @throws IOException if an IO error occurs
	 *
	 * @see #indent(Writer, int)
	 */
	public static void indent(String element, Writer writer, int times) throws IOException {
		indent(writer, times);
		writer.write('"');
		writer.write(element);
		writer.write('"');
	}

	/**
	 * Writes a map entry in pretty JSON format.
	 *
	 * @param entry the nested entry to write
	 * @param writer the writer to use
	 * @param level the initial indentation level
	 * @throws IOException if an IO error occurs
	 */
	public static void writeEntry(Entry<String, Integer> entry, Writer writer, int level) throws IOException {
		writer.write('\n');
		indent(entry.getKey(), writer, level);
		writer.write(": ");
		writer.write(entry.getValue().toString());
	}
	
	/**
	 * Writes a map entry in pretty JSON format. Use for nested objects.
	 * @param key key of map entry
	 * @param values value of the map entry, which is a collection of integers
	 * @param writer the writer to use
	 * @param level initial indent level
	 * @throws IOException if IO error occurs
	 */
	public static void writeEntry(String key, Collection<Integer> values, Writer writer, int level) throws IOException {
		writer.write("\n");;
		indent(key, writer, level);
		writer.write(": ");
		asArray(values, writer, level);
	}

	/*
	 * These methods are provided for you. No changes are required.
	 */

	/**
	 * Writes the elements as a pretty JSON array to file.
	 *
	 * @param elements the elements to write
	 * @param path the file path to use
	 * @throws IOException if an IO error occurs
	 *
	 * @see #asArray(Collection, Writer, int)
	 */
	public static void asArray(Collection<Integer> elements, Path path) throws IOException {
		// THIS CODE IS PROVIDED FOR YOU; DO NOT MODIFY
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			asArray(elements, writer, 0);
		}
	}

	/**
	 * Returns the elements as a pretty JSON array.
	 *
	 * @param elements the elements to use
	 * @return a {@link String} containing the elements in pretty JSON format
	 *
	 * @see #asArray(Collection, Writer, int)
	 */
	public static String asArray(Collection<Integer> elements) {
		// THIS CODE IS PROVIDED FOR YOU; DO NOT MODIFY
		try {
			StringWriter writer = new StringWriter();
			asArray(elements, writer, 0);
			return writer.toString();
		}
		catch (IOException e) {
			return null;
		}
	}

	/**
	 * Writes the elements as a pretty JSON object to file.
	 *
	 * @param elements the elements to write
	 * @param path the file path to use
	 * @throws IOException if an IO error occurs
	 *
	 * @see #asObject(Map, Writer, int)
	 */
	public static void asObject(Map<String, Integer> elements, Path path) throws IOException {
		// THIS CODE IS PROVIDED FOR YOU; DO NOT MODIFY
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			asObject(elements, writer, 0);
		}
	}

	/**
	 * Returns the elements as a pretty JSON object.
	 *
	 * @param elements the elements to use
	 * @return a {@link String} containing the elements in pretty JSON format
	 *
	 * @see #asObject(Map, Writer, int)
	 */
	public static String asObject(Map<String, Integer> elements) {
		// THIS CODE IS PROVIDED FOR YOU; DO NOT MODIFY
		try {
			StringWriter writer = new StringWriter();
			asObject(elements, writer, 0);
			return writer.toString();
		}
		catch (IOException e) {
			return null;
		}
	}

	/**
	 * Writes the elements as a nested pretty JSON object to file.
	 *
	 * @param elements the elements to write
	 * @param path the file path to use
	 * @throws IOException if an IO error occurs
	 *
	 * @see #asNestedArray(Map, Writer, int)
	 */
	public static void asNestedArray(Map<String, ? extends Collection<Integer>> elements, Path path) throws IOException {
		// THIS CODE IS PROVIDED FOR YOU; DO NOT MODIFY
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			asNestedArray(elements, writer, 0);
		}
	}

	/**
	 * Returns the elements as a nested pretty JSON object.
	 *
	 * @param elements the elements to use
	 * @return a {@link String} containing the elements in pretty JSON format
	 *
	 * @see #asNestedArray(Map, Writer, int)
	 */
	public static String asNestedArray(Map<String, ? extends Collection<Integer>> elements) {
		// THIS CODE IS PROVIDED FOR YOU; DO NOT MODIFY
		try {
			StringWriter writer = new StringWriter();
			asNestedArray(elements, writer, 0);
			return writer.toString();
		}
		catch (IOException e) {
			return null;
		}
	}


	/**
	 * A simple main method that demonstrates this class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {

		// MODIFY AS NECESSARY TO DEBUG YOUR CODE

		TreeSet<Integer> elements = new TreeSet<>();
		System.out.println("Empty:");
		System.out.println(asArray(elements));

		elements.add(65);
		System.out.println("\nSingle:");
		System.out.println(asArray(elements));

		elements.add(66);
		elements.add(67);
		System.out.println("\nSimple:");
		System.out.println(asArray(elements));
		
		Map<String, Integer> map = new HashMap<>();
		
		for (int i = 65; i < 91; i++) {
			map.put(Character.toString((char)i), i);
		}
		
		System.out.println("\nObject:");
		System.out.println(asObject(map));
		
		

		
		
	}
}

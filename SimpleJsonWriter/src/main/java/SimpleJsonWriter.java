import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

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
		
		//Start array
		Iterator<Integer> it = elements.iterator();
		
		//Case: Head
		writer.write("[");
		if (it.hasNext()) {
			writer.write("\n");
			indent(it.next(), writer, level + 1);
		}
			
		
		//Case: Other values
		while (it.hasNext()) {
			writer.write(",\n");
			indent(it.next(), writer, level + 1);
		}
		
		//Case: Tail/After all elements
		writer.write("\n]");

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
		
		//Case: Start of obj/head
		Set<Map.Entry<String,Integer>> entries = elements.entrySet();
		Iterator<Map.Entry<String, Integer>> it = entries.iterator();
		
		writer.write("{");
		if (it.hasNext()) {
			writeEntry(it.next(), writer, level + 1);
		}
		
		
		//Case: Other values
		while (it.hasNext()) {
			writer.write(",");
			writeEntry(it.next(), writer, level + 1);
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
		// TODO Fill in using iteration (not replace/split/join methods).
		// TODO Optional: Avoid repeated code and hard-coding the indent level.

		/*
		 * TODO Read then delete this comment.
		 *
		 * The parameter syntax for elements looks like:
		 *
		 * Map<String, ? extends Collection<Integer>> elements
		 *
		 * The syntax above makes this method directly useful for your project.
		 * However, you may not know how to interpret this syntax yet. It behaves
		 * as if it were this instead:
		 *
		 * HashMap<String, HashSet<Integer>> elements
		 *
		 * You may want to use the "var" keyword here to make dealing with the syntax
		 * a little bit easier.
		 */
		
		//Case: Start of obj/head
		Set<?> entries = elements.entrySet();
		Iterator<?> it = entries.iterator();
		
		while (it.hasNext()) {
			System.out.println(it.next())
		}
		/**
		writer.write("{");
		if (it.hasNext()) {
			writeEntry(it.next(), writer, level + 1);
		}
		
		
		//Case: Other values
		while (it.hasNext()) {
			writer.write(",");
			writeEntry(it.next(), writer, level + 1);
		}
		
		//Case: Tail/end of obj
		writer.write("\n}");
	}
		

		throw new UnsupportedOperationException("Not yet implemented.");
		*/
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
	 * Adds an element to a JSON Array
	 * 
	 * @param element the elment to add
	 * @param times default number of times to indent
	 * @param addComma an integer: 0 or 1, that decides whether JSONWriter will add a comma at the end of the entry
	 * @param Writer the writer to use
	 * @throws IOException 
	 */
	
	private void addArrayEntry(Object element, int times, int addComma, Writer writer) throws IOException  {
		String[] commaTable = new String[] {"", ","};
		
		indent(writer, times);
		writer.write(commaTable[addComma]);
		writer.write("\n");
		
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
		
		
		Map<String, ? extends Collection<Integer>> map = new HashMap<>();
		
		Collection<Integer>[] a = new ArrayList[5];
		
		for (int i = 0; i < a.length; i++) {
			a[i] = new ArrayList<>();
			
			for (int j = 0; j < 6; j++) {
				a[i].add(j);
			}
		}
		
		
		map.put("1", a[0]);
		map.add("2", a[1]);
		}
		Collection<Integer> b = new ArrayList<>();
		
		
		
		
	}
}

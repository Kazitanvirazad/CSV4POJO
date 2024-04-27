<h2><span>CSV4POJO library for Java application - Parse CSV file data
	to existing Pojo and vice-versa.</span></h2>
<h5><span>This utility is a lightweight and easy-to-use library that
	allows you to perform various operations with CSV files and Pojos.</span></h5>
<h4>Features</h4>
<ul>
	<li>Create CSV file data from existing Pojo, using annotations.</li>
	<li>Generate empty CSV file from Pojo definitions, using annotations.</li>
	<li>Create Pojo list from CSV file data, using annotations.</li>
	<li>Validation of the CSV file data against the Pojo definitions, using annotations.</li>
	<li>Custom CSV column name support.</li>
	<li>15 Supported DataTypes:
		<ol>
			<li>Integer</li>
			<li>Boolean</li>
			<li>Float</li>
			<li>Double</li>
			<li>Long</li>
			<li>Character</li>
			<li>String</li>
			<li>Integer Array</li>
			<li>String Array</li>
			<li>Boolean Array</li>
			<li>Float Array</li>
			<li>Double Array</li>
			<li>Long Array</li>
			<li>Character Array</li>
			<li>Class</li>
		</ol>
	</li>
</ul>
<h4>Configuring Java class instance variables with CSV4POJO FieldType annotations:</h4>
<ol>
<li><span>Any field with missing @FieldType annotation will be ignored by CSV4POJO</span></li>
<li><span>For any field with missing optional attribute 'csvColumnName' of @FieldType annotation, original field name will be used in CSV column name</span></li>
<li><span>CSV file headers sequence must match with the annotated instance variable sequence of the mapped classes</span></li>
<li><span>Mapped classes must have a public default constructor</span></li>
<li><span>Static fields are not guaranteed, using instance fields are recommended</span></li>
<li><span>Using separate DTO class for csv mapping is recommended</span></li>
<li><span>For empty element in csv InputStream will be stored as null value in the Java object variable</span></li>
<li><span>For null value in the Java object variable will be written as empty element in the csv OutputStream</span></li>
<li><span>Primitives are not supported, use Wrappers for mapping fields with FieldType annotations</span></li>
<li><span>(Optional) Set environment variable "CHAR_BUFFER_SIZE" to set custom input and output buffer size. Fallback buffer size is 8192</span></li>
</ol>
<h3>Interface CSVReader</h3>
<h4>Methods</h4>
<pre>
createPojoListFromCSVInputStream(Class<T> clazz, InputStream inputStream) : List

params:
	clazz: Class type of the pojo
	inputStream: csv file inputStream.

return:
	List

Creates and returns List of Java objects mapped with FieldType annotation from CSV InputStream
</pre>
<h4>Implementing Class</h4>
<pre>
CSVReader csvReader = new CSVReaderImpl();
</pre>
<h3>Interface CSVWriter</h3>
<h4>Methods</h4>
<pre>
writeCSVOutputStreamFromPojoList(Class<T> clazz, List<T> pojo, OutputStream outputStream) : void

params:
	clazz: Class type of the pojos
	pojo: List of pojos of clazz type which will be written in the CSV file
	outputStream: csv file OutputStream

return:
	void

Writes all the pojos annotated field values with FieldType annotation in the CSV file OutputStream
</pre>
<pre>
writeEmptyCSVOutputStreamFromClass(Class<T> clazz, OutputStream outputStream) : void

params:
	clazz: Class type of the pojo
	outputStream: csv file OutputStream

return:
	void

Writes an empty csv file with all the headers mapped with the given java class fields annotated with FieldType annotation in the CSV file OutputStream
</pre>
<h4>Implementing Class</h4>
<pre>
CSVWriter csvWriter = new CSVWriterImpl();
</pre>
<h4>Example use case:</h4>
<ul>
<li><span>Product.java</span>
<pre>
import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

	public class Product {
	
        @FieldType(dataType = Type.STRING, csvColumnName = "product_name")
		private String name;
	
		@FieldType(dataType = Type.STRING, csvColumnName = "product_color")
		private String color;
	
		@FieldType(dataType = Type.CLASSTYPE)
		private Inventory inventory;
	
		@FieldType(dataType = Type.FLOAT)
		private float price;
	
		@FieldType(dataType = Type.CLASSTYPE)
		private Category category;

		public Product() {
		}
	
		public Product(String name, String color, Inventory inventory, float price, Category category) {
			this.name = name;
			this.color = color;
			this.inventory = inventory;
			this.price = price;
			this.category = category;
		}
	
		@Override
		public String toString() {
			return "Product{" +
					"name='" + name + '\'' +
					", color='" + color + '\'' +
					", inventory=" + inventory +
					", price=" + price +
					", category=" + category +
					'}';
		}
	
	}
</pre>
</li>
<li><span>Inventory.java</span>
<pre>
import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

	public class Inventory {
		@FieldType(dataType = Type.INT, csvColumnName = "inventory_id")
		private int inventoryId;
	
		@FieldType(dataType = Type.STRING)
		private String location;
	
		@FieldType(dataType = Type.INT, csvColumnName = "items_count")
		private int itemsCount;
	
		@FieldType(dataType = Type.INTEGER_ARRAY, csvColumnName = "skus")
		private Integer[] skus;

		public Inventory() {
		}
	
		public Inventory(int inventoryId, String location, int itemsCount, Integer[] skus) {
			this.inventoryId = inventoryId;
			this.location = location;
			this.itemsCount = itemsCount;
			this.skus = skus;
		}
	
		@Override
		public String toString() {
			return "Inventory{" +
					"inventoryId=" + inventoryId +
					", location='" + location + '\'' +
					", itemsCount=" + itemsCount +
					", skus=" + Arrays.toString(skus) +
					'}';
		}
	}
</pre>
</li>
<li><span>Category.java</span>
<pre>
import org.csv4pojoparser.annotation.FieldType;
import org.csv4pojoparser.annotation.Type;

	public class Category {

		@FieldType(dataType = Type.STRING, csvColumnName = "category_name")
		private String categoryName;
	
		@FieldType(dataType = Type.STRING_ARRAY)
		private String[] tags;

		public Category(String categoryName, String[] tags) {
			this.categoryName = categoryName;
			this.tags = tags;
		}
	
		public Category() {
		}
	
		@Override
		public String toString() {
			return "Category{" +
					"categoryName='" + categoryName + '\'' +
					", tags=" + Arrays.toString(tags) +
					'}';
		}

	}
</pre>
</li>
</ul>
<h4>Usage:</h4>
<ol>
<li><span>Create an instance of CSVWriter</span>
<pre>
CSVWriter csvWriter = new CSVWriterImpl();
</pre>
</li>
<li><span>Write Pojo List data to a CSV file</span>
<pre>
		
        Integer[] skus1 = {2301, 1421, 456, 3423};
        Integer[] skus2 = {4509, 3456, 9254, 2352};
        Integer[] skus3 = {9876, 9458, 9243, 8746};

        String[] tags1 = {"Gadgets", "Electronics", "Wireless"};
        String[] tags2 = {"Technology", "AMoLED", "Bluetooth"};
        String[] tags3 = {"Charging", "Wireless Charging", "Type C Charging"};

        List<Product> products = new ArrayList<Product>() {
            private static final long serialVersionUID = 6801078320304556337L;

            {
                add(new Product("Oneplus Headphone", "Black",
                        new Inventory(101, "Bangalore", 234, skus1), 1499.34f,
                        new Category("Wireless Earphone", tags1)));
                add(new Product("Samsung Mobile", "White",
                        new Inventory(103, "Mumbai", 456, skus2), 31879.00f,
                        new Category("Smartphone", tags2)));
                add(new Product("Mi Powerbank", "Blue",
                        new Inventory(104, "Delhi", 167, skus3), 1129.65f,
                        new Category("Wireless Charging", tags3)));
            }
        };

        csvWriter.writeCSVOutputStreamFromPojoList(Product.class, products, outputStream);
</pre>
<h4>CSV output file data:</h4>
<pre>
product_name,product_color,inventory_id,location,items_count,skus,price,category_name,tags
Oneplus Headphone,Black,101,Bangalore,234,"2301,1421,456,3423",1499.34,Wireless Earphone,"Gadgets,Electronics,Wireless"
Samsung Mobile,White,103,Mumbai,456,"4509,3456,9254,2352",31879.0,Smartphone,"Technology,AMoLED,Bluetooth"
Mi Powerbank,Blue,104,Delhi,167,"9876,9458,9243,8746",1129.65,Wireless Charging,"Charging,Wireless Charging,Type C Charging"
</pre>
</li>
<li><span>Creating an empty CSV file with Pojo definition</span>
<pre>
csvWriter.writeEmptyCSVOutputStreamFromClass(Product.class, outputStream);
</pre>
<h4>CSV output file data:</h4>
<pre>
product_name,product_color,inventory_id,location,items_count,skus,price,category_name,tags
</pre>
</li>
<li><span>Create an instance of CSVReader</span>
<pre>
CSVReader csvReader = new CSVReaderImpl();
</pre>
<li><span>Create list of Java objects from CSV file data</span>
<pre>
List<Product> productList = csvReader.createPojoListFromCSVInputStream(Product.class, inputStream);
</pre>
<h4>Output data using toString() method :</h4>
<pre>
Product{name='Oneplus Headphone', color='Black', inventory=Inventory{inventoryId=101, location='Bangalore', itemsCount=234, skus=[2301, 1421, 456, 3423]}, price=1499.34, category=Category{categoryName='Wireless Earphone', tags=[Gadgets, Electronics, Wireless]}}
Product{name='Samsung Mobile', color='White', inventory=Inventory{inventoryId=103, location='Mumbai', itemsCount=456, skus=[4509, 3456, 9254, 2352]}, price=31879.0, category=Category{categoryName='Smartphone', tags=[Technology, AMoLED, Bluetooth]}}
Product{name='Mi Powerbank', color='Blue', inventory=Inventory{inventoryId=104, location='Delhi', itemsCount=167, skus=[9876, 9458, 9243, 8746]}, price=1129.65, category=Category{categoryName='Wireless Charging', tags=[Charging, Wireless Charging, Type C Charging]}}
</pre>
</li>
</ol>


<h4>Contribution</h4>
<span>You are welcome to contribute to this project by improving performance, or adding new features. Feel free to fork
the repository, create pull requests, or open issues.</span>

<h4>Feedback</h4>
<span>I would love to hear your feedback and suggestions on how to improve
this utility. Please share it with your network and let me know what you
think. Thank you for your support and interest.</span>

<h4>Comment</h4>
<span>This utility tool is free to use for any purpose. This tool can also be used for commercial purposes for free. The code in this repository
is free and open for all.</span>
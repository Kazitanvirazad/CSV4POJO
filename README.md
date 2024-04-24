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
			<li>int</li>
			<li>boolean</li>
			<li>float</li>
			<li>double</li>
			<li>long</li>
			<li>char</li>
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

<h3>Interface CSVReader</h3>
<h4>Methods</h4>
<pre>
createPojoListFromCSVInputStream(Class<T> clazz, InputStream inputStream) : List

params:
	clazz: Class type of the pojo
	inputStream: csv file inputStream.

return:
	List

Reads the given file and returns a list of pojo with CSV data.
</pre>
<h4>Implementing Class</h4>
<pre>
CSVReader csvReader = new CSVReaderImpl();
</pre>
<h3>Interface CSVWriter</h3>
<h4>Methods</h4>
<pre>
createCSVOutputStreamFromPojoList(Class<T> clazz, List<T> pojoList, Writer writer) : void

params:
	clazz: Class type of the pojos
	pojoList: List of pojos of clazz type which will be written in the CSV file
	writer: csv file writer

Writes all the pojos annotated fields in the CSV file.
</pre>
<pre>
createEmptyCSVOutputStreamFromClass(Class<T> clazz, Writer writer) : void

params:
	clazz: Class type of the pojo
	writer: csv file writer

Creates an empty csv file with all the headings mapped with the given java class annotated fields
</pre>
<h4>Implementing Class</h4>
<pre>
CSVWriter csvWriter = new CSVWriterImpl();
</pre>
<h4>Configuring Java class instance variables with CSV4POJO FieldType annotations</h4>
<span><b>**Any field with missing @FieldType annotation will be ignored by CSV4POJO</b></span></br>
<span><b>**For any field with missing optional attribute 'csvColumnName' of @FieldType annotation, original field name will be used in CSV column name</b></span></br>
<span><b>**CSV file headers sequence must match with the annotated instance variable sequence of the mapped classes</b></span>
<ul>
<li><span>Vehicle.java</span>
<pre>
import com.csv4pojo.annotation.FieldType;
import com.csv4pojo.annotation.Type;

public class Vehicle {

	@FieldType(dataType = Type.STRING, csvColumnName = "vahicle_name")
	private String name;

	@FieldType(dataType = Type.BOOLEAN)
	private boolean isElectric;

	@FieldType(dataType = Type.CLASSTYPE)
	private Byke byke;

	public Vehicle(String name, boolean isElectric, Byke byke) {
		this();
		this.name = name;
		this.isElectric = isElectric;
		this.byke = byke;
	}

	public Vehicle() {
		super();
	}

	@Override
	public String toString() {
		return "Vehicle [name=" + name + ", isElectric=" + isElectric + ", byke=" + byke + "]";
	}

}
</pre>
</li>
<li><span>Byke.java</span>
<pre>
import com.csv4pojo.annotation.FieldType;
import com.csv4pojo.annotation.Type;

public class Byke {

	@FieldType(dataType = Type.STRING, csvColumnName = "byke_brandName")
	private String brandName;

	@FieldType(dataType = Type.STRING, csvColumnName = "byke_modelName")
	private String modelName;

	@FieldType(dataType = Type.STRING, csvColumnName = "byke_color")
	private String color;

	@FieldType(dataType = Type.CLASSTYPE)
	private Tyre tyre;

	@FieldType(dataType = Type.CLASSTYPE)
	private Engine engine;

	@FieldType(dataType = Type.INT)
	private int kerbWeight;

	private float price;

	public Byke(String brandName, String modelName, String color, Tyre tyre, Engine engine, int kerbWeight,
			float price) {
		this();
		this.brandName = brandName;
		this.modelName = modelName;
		this.color = color;
		this.tyre = tyre;
		this.engine = engine;
		this.kerbWeight = kerbWeight;
		this.price = price;
	}

	public Byke() {
		super();
	}

	@Override
	public String toString() {
		return "Byke [brandName=" + brandName + ", modelName=" + modelName + ", color=" + color + ", tyre=" + tyre
				+ ", engine=" + engine + ", kerbWeight=" + kerbWeight + ", price=" + price + "]";
	}

}
</pre>
</li>
<li><span>Tyre.java</span>
<pre>
import com.csv4pojo.annotation.FieldType;
import com.csv4pojo.annotation.Type;

public class Tyre {

	@FieldType(dataType = Type.STRING, csvColumnName = "tyre_brandName")
	private String tyreBrand;

	private int tyreWidth;

	private int tyreBreadth;

	@FieldType(dataType = Type.INT, csvColumnName = "tyre_selfLife")
	private int selfLife;

	public Tyre(String tyreBrand, int tyreWidth, int tyreBreadth, int selfLife) {
		super();
		this.tyreBrand = tyreBrand;
		this.tyreWidth = tyreWidth;
		this.tyreBreadth = tyreBreadth;
		this.selfLife = selfLife;
	}

	public Tyre() {
		super();
	}

	@Override
	public String toString() {
		return "Tyre [tyreBrand=" + tyreBrand + ", tyreWidth=" + tyreWidth + ", tyreBreadth=" + tyreBreadth
				+ ", selfLife=" + selfLife + "]";
	}

}
</pre>
</li>
<li><span>Engine.java</span>
<pre>
import com.csv4pojo.annotation.FieldType;
import com.csv4pojo.annotation.Type;

public class Engine {

	@FieldType(dataType = Type.FLOAT)
	private float cc;

	@FieldType(dataType = Type.FLOAT, csvColumnName = "engine_horsepower")
	private float bhp;

	@FieldType(dataType = Type.INT, csvColumnName = "engine_cylinders")
	private int cylinders;

	public Engine() {
		super();
	}

	public Engine(float cc, float bhp, int cylinders) {
		this();
		this.cc = cc;
		this.bhp = bhp;
		this.cylinders = cylinders;
	}

	@Override
	public String toString() {
		return "Engine [cc=" + cc + ", bhp=" + bhp + ", cylinders=" + cylinders + "]";
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
List<Vehicle> vehicles = new ArrayList<>() {
	{
		add(new Vehicle("KTM MotorCycle", false, new Byke("KTM", "Duke 250", "Ebony Black", new Tyre("MRF", 150, 70, 5), new Engine(249.9f, 28, 1), 170, 292000f)));
		add(new Vehicle("Kawasaki MotorCycle", false, new Byke("Kawasaki", "z900", "Ninja green", new Tyre("Bridgestone", 180, 90, 2), new Engine(899.9f, 128, 4), 190, 1092000f)));	
		add(new Vehicle("Yamaha MotorCycle", false, new Byke("Yamaha", "mt09", "White", new Tyre("Pirelli", 180, 90, 2), new Engine(900.0f, 120, 4), 180, 1192000f)));	
	}
};
csvWriter.createCSVOutputStreamFromPojoList(Vehicle.class, vehicles, writer);
</pre>
<h4>CSV output file data:</h4>
<pre>
vahicle_name,isElectric,byke_brandName,byke_modelName,byke_color,tyre_brandName,tyre_selfLife,cc,engine_horsepower,engine_cylinders,kerbWeight
KTM MotorCycle,false,KTM,Duke 250,Ebony Black,MRF,5,249.9,28.0,1,170
Kawasaki MotorCycle,false,Kawasaki,z900,Ninja green,Bridgestone,2,899.9,128.0,4,190
Yamaha MotorCycle,false,Yamaha,mt09,White,Pirelli,2,900.0,120.0,4,180
</pre>
</li>
<li><span>Creating an empty CSV file with Pojo definition</span>
<pre>
csvWriter.createEmptyCSVOutputStreamFromClass(Vehicle.class, writer);
</pre>
<h4>CSV output file data:</h4>
<pre>
vahicle_name,isElectric,byke_brandName,byke_modelName,byke_color,tyre_brandName,tyre_selfLife,cc,engine_horsepower,engine_cylinders,kerbWeight
</pre>
</li>
<li><span>Create an instance of CSVReader</span>
<pre>
CSVReader csvReader = new CSVReaderImpl();
</pre>
<li><span>Create list of Java pojo from CSV file data</span>
<pre>
csvReader.createPojoListFromCSVInputStream(Vehicle.class, inputStream);
</pre>
<h4>Output data using toString() method :</h4>
<pre>
Vehicle [name=KTM MotorCycle, isElectric=false, byke=Byke [brandName=KTM, modelName=Duke 250, color=Ebony Black, tyre=Tyre [tyreBrand=MRF, tyreWidth=0, tyreBreadth=0, selfLife=5], engine=Engine [cc=249.9, bhp=28.0, cylinders=1], kerbWeight=170, price=0.0]]
Vehicle [name=Kawasaki MotorCycle, isElectric=false, byke=Byke [brandName=Kawasaki, modelName=z900, color=Ninja green, tyre=Tyre [tyreBrand=Bridgestone, tyreWidth=0, tyreBreadth=0, selfLife=2], engine=Engine [cc=899.9, bhp=128.0, cylinders=4], kerbWeight=190, price=0.0]]
Vehicle [name=Yamaha MotorCycle, isElectric=false, byke=Byke [brandName=Yamaha, modelName=mt09, color=White, tyre=Tyre [tyreBrand=Pirelli, tyreWidth=0, tyreBreadth=0, selfLife=2], engine=Engine [cc=900.0, bhp=120.0, cylinders=4], kerbWeight=180, price=0.0]]
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

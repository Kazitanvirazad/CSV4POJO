<h2><span>CSVUtil library for Java application - Parse CSV file data
	to existing Java beans and vice-versa.</span></h2>
<h5><span>This utility is a lightweight and easy-to-use library that
	allows you to perform various operations with CSV files and Java beans.</span></h5>
<h4>Features</h4>
<ul>
	<li>Create CSV file data from existing Java beans, using
		annotations and reflection.</li>
	<li>Generate empty CSV file from Java bean definitions, using
		annotations and reflection.</li>
	<li>Create Java beans from CSV file data, using annotations and
		reflection.</li>
	<li>Validation of the CSV file data against the Java bean
		definitions, using annotations and reflection.</li>
	<li>Custom CSV column name support.</li>
</ul>

<h3>Class CSVFileReaderUtil</h3>
<h4>Methods</h4>
<pre>
static getCSVFileReader() : CSVFileReader

return:
	CSVFileReader
	
Creates and returns an instance of CSVFileReader
</pre>
<pre>
createBeansFromCSV(Class clazz, String path, String fileName) : List

params:
	clazz: Class type of the bean
	path: Directory path location of the file.
	fileName: Name of the file

return:
	CSVUtil

Reads the given file and returns a list of beans with csv data.
</pre>

<h3>Class CSVFileWriterUtil</h3>
<h4>Methods</h4>
<pre>
static getCSVFileWriter() : CSVFileWriter

return:
	CSVFileWriter
	
Creates and returns an instance of CSVFileWriter
</pre>
<pre>
writeBeansToCSV(Class clazz, List beanList, String path, String fileName) : void

params:
	clazz: Class type of the beans
	path: Directory path location where the file will be created and written.
	beanList: List of beans of clazz type which will be written in the csv fil
	fileName: Name of the file

Writes all the beans annotated fields in the csv file. 
If path is null it will use the project root as the default path.
If fileName is null it will use bean Class name as the file name
</pre>
<pre>
createEmptyCSVFromClass(Class clazz, String path, String fileName) : void

params:
	clazz: Class type of the bean
	path: Directory path location where the file will be created and written.
	fileName: Name of the file

Creates an empty csv file with all the headings mapped with the given java class annotated fields
If path is null it will use the project root as the default path.
If fileName is null it will use bean Class name as the file name
</pre>

<h4>Configuring Java class instance variables with CSVUtil FieldType annotations</h4>
<span><b>**Any field with missing @FieldType annotation will be ignored by CSVUtil</b></span></br>
<span><b>**For any field with missing optional attribute 'csvColumnName' of @FieldType annotation, original field name will be used in CSV column name</b></span>
<ul>
<li><span>Vehicle.java</span>
<pre>
import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

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
import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

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

	@FieldType(dataType = Type.INTEGER)
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
import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Tyre {

	@FieldType(dataType = Type.STRING, csvColumnName = "tyre_brandName")
	private String tyreBrand;

	private int tyreWidth;

	private int tyreBreadth;

	@FieldType(dataType = Type.INTEGER, csvColumnName = "tyre_selfLife")
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
import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Engine {

	@FieldType(dataType = Type.FLOAT)
	private float cc;

	@FieldType(dataType = Type.FLOAT, csvColumnName = "engine_horsepower")
	private float bhp;

	@FieldType(dataType = Type.INTEGER, csvColumnName = "engine_cylinders")
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
<li><span>Get an instance of CSVFileWriter</span>
<pre>
CSVFileWriter csvFileWriter = CSVFileWriterUtil.getCSVFileWriter();
</pre>
</li>
<li><span>Write Java beans data to a CSV file</span>
<pre>
List<Vehicle> vehicles = new ArrayList<>() {
	{
		add(new Vehicle("KTM MotorCycle", false, new Byke("KTM", "Duke 250", "Ebony Black", new Tyre("MRF", 150, 70, 5), new Engine(249.9f, 28, 1), 170, 292000f)));
		add(new Vehicle("Kawasaki MotorCycle", false, new Byke("Kawasaki", "z900", "Ninja green", new Tyre("Bridgestone", 180, 90, 2), new Engine(899.9f, 128, 4), 190, 1092000f)));	
		add(new Vehicle("Yamaha MotorCycle", false, new Byke("Yamaha", "mt09", "White", new Tyre("Pirelli", 180, 90, 2), new Engine(900.0f, 120, 4), 180, 1192000f)));	
	}
};
csvFileWriter.writeBeansToCSV(Vehicle.class, vehicles, System.getProperty("user.dir"), "Vehicles");
</pre>
<h4>CSV output file data:</h4>
<pre>
vahicle_name,isElectric,byke_brandName,byke_modelName,byke_color,tyre_brandName,tyre_selfLife,cc,engine_horsepower,engine_cylinders,kerbWeight
KTM MotorCycle,false,KTM,Duke 250,Ebony Black,MRF,5,249.9,28.0,1,170
Kawasaki MotorCycle,false,Kawasaki,z900,Ninja green,Bridgestone,2,899.9,128.0,4,190
Yamaha MotorCycle,false,Yamaha,mt09,White,Pirelli,2,900.0,120.0,4,180
</pre>
</li>
<li><span>Creating an empty CSV file with Java bean definition</span>
<pre>
csvFileWriter.createEmptyCSVFromClass(Vehicle.class, System.getProperty("user.dir"), "Vehicle_sample");
</pre>
<h4>CSV output file data:</h4>
<pre>
vahicle_name,isElectric,byke_brandName,byke_modelName,byke_color,tyre_brandName,tyre_selfLife,cc,engine_horsepower,engine_cylinders,kerbWeight
</pre>
</li>
<li><span>Get an instance of CSVFileReader</span>
<pre>
CSVFileReader csvFileReader = CSVFileReaderUtil.getCSVFileReader();
</pre>
<li><span>Create list of Java beans from CSV file data</span>
<pre>
csvFileReader.createBeansFromCSV(Vehicle.class, System.getProperty("user.dir"), "Vehicles").forEach(vehicle -> {
	System.out.println(vehicle.toString());
});
</pre>
<h4>Output data:</h4>
<pre>
Vehicle [name=KTM MotorCycle, isElectric=false, byke=Byke [brandName=KTM, modelName=Duke 250, color=Ebony Black, tyre=Tyre [tyreBrand=MRF, tyreWidth=0, tyreBreadth=0, selfLife=5], engine=Engine [cc=249.9, bhp=28.0, cylinders=1], kerbWeight=170, price=0.0]]
Vehicle [name=Kawasaki MotorCycle, isElectric=false, byke=Byke [brandName=Kawasaki, modelName=z900, color=Ninja green, tyre=Tyre [tyreBrand=Bridgestone, tyreWidth=0, tyreBreadth=0, selfLife=2], engine=Engine [cc=899.9, bhp=128.0, cylinders=4], kerbWeight=190, price=0.0]]
Vehicle [name=Yamaha MotorCycle, isElectric=false, byke=Byke [brandName=Yamaha, modelName=mt09, color=White, tyre=Tyre [tyreBrand=Pirelli, tyreWidth=0, tyreBreadth=0, selfLife=2], engine=Engine [cc=900.0, bhp=120.0, cylinders=4], kerbWeight=180, price=0.0]]
</pre>
</li>
</ol>


<h4>Contribution</h4>
<span>You are welcome to contribute to this project by writing unit test
cases, improving performance, or adding new features. Feel free to fork
the repository, create pull requests, or open issues.</span>

<h4>Feedback</h4>
<span>I would love to hear your feedback and suggestions on how to improve
this utility. Please share it with your network and let me know what you
think. Thank you for your support and interest.</span>

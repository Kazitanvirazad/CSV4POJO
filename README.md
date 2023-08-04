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
</ul>

<h4>Methods</h4>
<pre>
static getInstance(): CSVUtil

returns:
	CSVUtil

Creates and returns an instance of CSVUtil


writeBeansToCSV(Class clazz, List beanList, String path, String fileName) : void

params:
	clazz: Class type of the beans
	path: Directory path location where the file will be created and written.
	beanList: List of beans of clazz type which will be written in the csv fil
	fileName: Name of the file

Writes all the beans annotated fields in the csv file. 
If path is null it will use the project root as the default path.
If fileName is null it will use bean Class name as the file name


createEmptyCSVFromClass(Class clazz, String path, String fileName) : void

params:
	clazz: Class type of the bean
	path: Directory path location where the file will be created and written.
	fileName: Name of the file

Creates an empty csv file with all the headings mapped with the given java class annotated fields
If path is null it will use the project root as the default path.
If fileName is null it will use bean Class name as the file name


createBeansFromCSV(Class clazz, String path, String fileName) : List

params:
	clazz: Class type of the bean
	path: Directory path location of the file.
	fileName: Name of the file

return:
	CSVUtil

Reads the given file and returns a list of beans with csv data.

</pre>

<h4>Configuring Java class instance variables with CSVUtil FieldType annotations</h4>
<span><b>*Any field with missing @FieldType annotation will be ignored by CSVUtil</b></span>
<ul>
<li><span>Vehicle.java</span>
<pre>
import com.csvutil.annotation.FieldType;
import com.csvutil.annotation.Type;

public class Vehicle {

	@FieldType(Type.STRING)
	private String name;

	@FieldType(Type.BOOLEAN)
	private boolean isElectric;

	@FieldType(Type.CLASSTYPE)
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

	@FieldType(Type.STRING)
	private String brandName;

	@FieldType(Type.STRING)
	private String modelName;

	@FieldType(Type.STRING)
	private String color;

	@FieldType(Type.CLASSTYPE)
	private Tyre tyre;

	@FieldType(Type.CLASSTYPE)
	private Engine engine;

	@FieldType(Type.INTEGER)
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

	@FieldType(Type.STRING)
	private String tyreBrand;

	private int tyreWidth;

	private int tyreBreadth;

	@FieldType(Type.INTEGER)
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

	@FieldType(Type.FLOAT)
	private float cc;

	@FieldType(Type.FLOAT)
	private float bhp;

	@FieldType(Type.INTEGER)
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
<li><span>Get an instance of CSVUtil</span>
<pre>
CSVUtil csvUtil = CSVUtil.getInstance();
</pre>
</li>
<li><span>Write Java beans data to a CSV file</span>
<pre>
List<Vehicle> vehicles = new ArrayList<>() {
	{
		add(new Vehicle("Motor Cycle", false, new Byke("KTM", "Duke 250", "Ebony Black", new Tyre("MRF", 150, 60, 5), new Engine(250, 28, 1), 170, 292000)));
		add(new Vehicle("Motor Cycle", false, new Byke("Kawasaki", "z900", "Green", new Tyre("Pirelli", 170, 70, 3), new Engine(900, 125, 4), 190, 1092000)));
		add(new Vehicle("Motor Cycle", false, new Byke("KTM", "RC 390", "White", new Tyre("Metzeller", 150, 60, 4), new Engine(372.9f, 43.5f, 1), 170, 348500.0f)));
		add(new Vehicle("Motor Cycle", false, new Byke("Yamaha", "mt09", "White", new Tyre("Bridgestone", 170, 70, 3), new Engine(900, 120, 4), 185, 1192000)));
	}
};
csvUtil.writeBeansToCSV(Vehicle.class, vehicles, System.getProperty("user.dir"), "Vehicles");
</pre>
<h4>CSV output file data:</h4>
<pre>
name,isElectric,brandName,modelName,color,tyreBrand,selfLife,cc,bhp,cylinders,kerbWeight
Motor Cycle,false,KTM,Duke 250,Ebony Black,MRF,5,250.0,28.0,1,170
Motor Cycle,false,Kawasaki,z900,Green,Pirelli,3,900.0,125.0,4,190
Motor Cycle,false,KTM,RC 390,White,Metzeller,4,372.9,43.5,1,170
Motor Cycle,false,Yamaha,mt09,White,Bridgestone,3,900.0,120.0,4,185
</pre>
</li>
<li><span>Creating an empty CSV file with Java bean definition</span>
<pre>
csvUtil.createEmptyCSVFromClass(Vehicle.class, System.getProperty("user.dir"), "Vehicle_sample");
</pre>
<h4>CSV output file data:</h4>
<pre>
name,isElectric,brandName,modelName,color,tyreBrand,selfLife,cc,bhp,cylinders,kerbWeight
</pre>
</li>
<li><span>Create list of Java beans from CSV file data</span>
<pre>
csvUtil.createBeansFromCSV(Vehicle.class, System.getProperty("user.dir"), "Vehicles").forEach(vehicle -> {
	System.out.println(vehicle.toString());
});
</pre>
<h4>Output data:</h4>
<pre>
Vehicle [name=Motor Cycle, isElectric=false, byke=Byke [brandName=KTM, modelName=Duke 250, color=Ebony Black, tyre=Tyre [tyreBrand=MRF, tyreWidth=0, tyreBreadth=0, selfLife=5], engine=Engine [cc=250.0, bhp=28.0, cylinders=1], kerbWeight=170, price=0.0]]
Vehicle [name=Motor Cycle, isElectric=false, byke=Byke [brandName=Kawasaki, modelName=z900, color=Green, tyre=Tyre [tyreBrand=Pirelli, tyreWidth=0, tyreBreadth=0, selfLife=3], engine=Engine [cc=900.0, bhp=125.0, cylinders=4], kerbWeight=190, price=0.0]]
Vehicle [name=Motor Cycle, isElectric=false, byke=Byke [brandName=KTM, modelName=RC 390, color=White, tyre=Tyre [tyreBrand=Metzeller, tyreWidth=0, tyreBreadth=0, selfLife=4], engine=Engine [cc=372.9, bhp=43.5, cylinders=1], kerbWeight=170, price=0.0]]
Vehicle [name=Motor Cycle, isElectric=false, byke=Byke [brandName=Yamaha, modelName=mt09, color=White, tyre=Tyre [tyreBrand=Bridgestone, tyreWidth=0, tyreBreadth=0, selfLife=3], engine=Engine [cc=900.0, bhp=120.0, cylinders=4], kerbWeight=185, price=0.0]]
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

package tendiwa.modules;

import tendiwa.core.LoadStaticDataFromXML;
import tendiwa.core.Module;


public class MainModule extends Module {
	public MainModule() {
		setDependencies(null);
	}
	@Override
	public void buildStaticData() {
		LoadStaticDataFromXML.loadGameDataFromXml("data/mainmodule.xml");
	}
}

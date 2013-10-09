package tendiwa.modules;

import tendiwa.core.Module;
import tendiwa.core.ResourcesRegistry;
import tendiwa.core.World;

public class MainModule extends Module {
public MainModule() {
	addStaticDataResource("/data/mainmodule.xml");
	ResourcesRegistry.registerDrawer(new Forest());
	World world = World.create(new SuseikaWorld(), 800, 800);
}

public static void main(String[] args) {
	new MainModule();
}

}

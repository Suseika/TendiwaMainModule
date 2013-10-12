package tendiwa.modules;

import tendiwa.core.Module;
import tendiwa.core.ResourcesRegistry;
import tendiwa.core.World;

import static tendiwa.geometry.DSL.canvas;

public class MainModule extends Module {
public MainModule() {
//	ResourcesRegistry.registerDrawer(new TestLocationDrawer());
//	ResourcesRegistry.registerDrawer(new Forest());
//	World world = World.create(new SuseikaWorld(), 800, 600);
//	canvas().draw(world);
}

public static void main(String[] args) {
	new MainModule();
}

}

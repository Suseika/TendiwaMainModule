package org.tendiwa.entities;

import tendiwa.core.ObjectType;
import tendiwa.core.Passability;
import tendiwa.core.WallType;

import java.util.HashMap;
import java.util.NoSuchElementException;

public enum WallTypes implements WallType {
	GREY_STONE {
		@Override
		public String getResourceName() {
			return "wall_grey_stone";
		}
	},
	RED_STONE {
		@Override
		public String getResourceName() {
			return "wall_red_stone";
		}
	},
	WOODEN {
		@Override
		public String getResourceName() {
			return "wall_wooden";
		}
	},
	LATTICE {
		@Override
		public String getResourceName() {
			return "lattice";
		}
	},
	CAVE {
		@Override
		public String getResourceName() {
			return "wall_cave";
		}
	};
public static final WallType VOID = WallType.VOID;
private static final HashMap<String, WallType> name2type = new HashMap<>();

static {
	for (WallType type : WallTypes.values()) {
		name2type.put(type.getResourceName(), type);
	}
}

private static final ObjectType wallObjectType = new ObjectType() {
	@Override
	public Passability getPassability() {
		return Passability.NO;
	}

	@Override
	public String getResourceName() {
		return "wall_type";
	}
};

public static WallType getWallTypeByResourceName(String resourceName) {
	WallType wallType = name2type.get(resourceName);
	if (wallType == null) {
		throw new NoSuchElementException("No wall type with resource name \"" + resourceName + "\" defined");
	}
	return wallType;
}

@Override
public ObjectType getType() {
	return wallObjectType;
}
}

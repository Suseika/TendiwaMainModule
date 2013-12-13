package org.tendiwa.entities;

import tendiwa.core.WallType;

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
	}

}

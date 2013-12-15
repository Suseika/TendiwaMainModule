package org.tendiwa.entities;

import tendiwa.core.FloorType;

public enum FloorTypes implements FloorType {
	WATER {
		@Override
		public boolean isLiquid() {
			return true;
		}

		@Override
		public String getResourceName() {
			return "water";
		}
	},
	GRASS {
		@Override
		public boolean isLiquid() {
			return false;
		}

		@Override
		public String getResourceName() {
			return "grass";
		}
	},
	STONE {
		@Override
		public boolean isLiquid() {
			return false;
		}

		@Override
		public String getResourceName() {
			return "stone";
		}
	},
	DRY_GRASS {
		@Override
		public boolean isLiquid() {
			return false;
		}

		@Override
		public String getResourceName() {
			return "dry_grass";
		}
	},
	GROUND {
		@Override
		public boolean isLiquid() {
			return false;
		}

		@Override
		public String getResourceName() {
			return "ground";
		}
	},
	SNOW {
		@Override
		public boolean isLiquid() {
			return false;
		}

		@Override
		public String getResourceName() {
			return "snow";
		}
	},
	WOOD {
		@Override
		public boolean isLiquid() {
			return false;
		}

		@Override
		public String getResourceName() {
			return "wood";
		}
	},
	SWAMP {
		@Override
		public boolean isLiquid() {
			return false;
		}

		@Override
		public String getResourceName() {
			return "swamp";
		}
	},
EMPTINESS {
	@Override
	public boolean isLiquid() {
		return false;
	}

	@Override
	public String getResourceName() {
		return "emptiness";
	}
}

}
